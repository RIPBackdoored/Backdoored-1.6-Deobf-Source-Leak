package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import java.util.concurrent.locks.*;
import java.io.*;
import java.util.logging.*;
import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class Credential implements HttpExecuteInterceptor, HttpRequestInitializer, HttpUnsuccessfulResponseHandler
{
    static final Logger LOGGER;
    private final Lock lock;
    private final AccessMethod method;
    private final Clock clock;
    private String accessToken;
    private Long expirationTimeMilliseconds;
    private String refreshToken;
    private final HttpTransport transport;
    private final HttpExecuteInterceptor clientAuthentication;
    private final JsonFactory jsonFactory;
    private final String tokenServerEncodedUrl;
    private final Collection<CredentialRefreshListener> refreshListeners;
    private final HttpRequestInitializer requestInitializer;
    
    public Credential(final AccessMethod method) {
        this(new Builder(method));
    }
    
    protected Credential(final Builder builder) {
        super();
        this.lock = new ReentrantLock();
        this.method = Preconditions.checkNotNull(builder.method);
        this.transport = builder.transport;
        this.jsonFactory = builder.jsonFactory;
        this.tokenServerEncodedUrl = ((builder.tokenServerUrl == null) ? null : builder.tokenServerUrl.build());
        this.clientAuthentication = builder.clientAuthentication;
        this.requestInitializer = builder.requestInitializer;
        this.refreshListeners = Collections.unmodifiableCollection((Collection<? extends CredentialRefreshListener>)builder.refreshListeners);
        this.clock = Preconditions.checkNotNull(builder.clock);
    }
    
    @Override
    public void intercept(final HttpRequest request) throws IOException {
        this.lock.lock();
        try {
            final Long expiresIn = this.getExpiresInSeconds();
            if (this.accessToken == null || (expiresIn != null && expiresIn <= 60L)) {
                this.refreshToken();
                if (this.accessToken == null) {
                    return;
                }
            }
            this.method.intercept(request, this.accessToken);
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public boolean handleResponse(final HttpRequest request, final HttpResponse response, final boolean supportsRetry) {
        boolean refreshToken = false;
        boolean bearer = false;
        final List<String> authenticateList = response.getHeaders().getAuthenticateAsList();
        if (authenticateList != null) {
            for (final String authenticate : authenticateList) {
                if (authenticate.startsWith("Bearer ")) {
                    bearer = true;
                    refreshToken = BearerToken.INVALID_TOKEN_ERROR.matcher(authenticate).find();
                    break;
                }
            }
        }
        if (!bearer) {
            refreshToken = (response.getStatusCode() == 401);
        }
        if (refreshToken) {
            try {
                this.lock.lock();
                try {
                    return !Objects.equal(this.accessToken, this.method.getAccessTokenFromRequest(request)) || this.refreshToken();
                }
                finally {
                    this.lock.unlock();
                }
            }
            catch (IOException exception) {
                Credential.LOGGER.log(Level.SEVERE, "unable to refresh token", exception);
            }
        }
        return false;
    }
    
    @Override
    public void initialize(final HttpRequest request) throws IOException {
        request.setInterceptor(this);
        request.setUnsuccessfulResponseHandler(this);
    }
    
    public final String getAccessToken() {
        this.lock.lock();
        try {
            return this.accessToken;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public Credential setAccessToken(final String accessToken) {
        this.lock.lock();
        try {
            this.accessToken = accessToken;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    public final AccessMethod getMethod() {
        return this.method;
    }
    
    public final Clock getClock() {
        return this.clock;
    }
    
    public final HttpTransport getTransport() {
        return this.transport;
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public final String getTokenServerEncodedUrl() {
        return this.tokenServerEncodedUrl;
    }
    
    public final String getRefreshToken() {
        this.lock.lock();
        try {
            return this.refreshToken;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public Credential setRefreshToken(final String refreshToken) {
        this.lock.lock();
        try {
            if (refreshToken != null) {
                Preconditions.checkArgument(this.jsonFactory != null && this.transport != null && this.clientAuthentication != null && this.tokenServerEncodedUrl != null, (Object)"Please use the Builder and call setJsonFactory, setTransport, setClientAuthentication and setTokenServerUrl/setTokenServerEncodedUrl");
            }
            this.refreshToken = refreshToken;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    public final Long getExpirationTimeMilliseconds() {
        this.lock.lock();
        try {
            return this.expirationTimeMilliseconds;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public Credential setExpirationTimeMilliseconds(final Long expirationTimeMilliseconds) {
        this.lock.lock();
        try {
            this.expirationTimeMilliseconds = expirationTimeMilliseconds;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    public final Long getExpiresInSeconds() {
        this.lock.lock();
        try {
            if (this.expirationTimeMilliseconds == null) {
                return null;
            }
            return (this.expirationTimeMilliseconds - this.clock.currentTimeMillis()) / 1000L;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public Credential setExpiresInSeconds(final Long expiresIn) {
        return this.setExpirationTimeMilliseconds((expiresIn == null) ? null : Long.valueOf(this.clock.currentTimeMillis() + expiresIn * 1000L));
    }
    
    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }
    
    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }
    
    public final boolean refreshToken() throws IOException {
        this.lock.lock();
        try {
            try {
                final TokenResponse tokenResponse = this.executeRefreshToken();
                if (tokenResponse != null) {
                    this.setFromTokenResponse(tokenResponse);
                    for (final CredentialRefreshListener refreshListener : this.refreshListeners) {
                        refreshListener.onTokenResponse(this, tokenResponse);
                    }
                    return true;
                }
            }
            catch (TokenResponseException e) {
                final boolean statusCode4xx = 400 <= e.getStatusCode() && e.getStatusCode() < 500;
                if (e.getDetails() != null && statusCode4xx) {
                    this.setAccessToken(null);
                    this.setExpiresInSeconds(null);
                }
                for (final CredentialRefreshListener refreshListener2 : this.refreshListeners) {
                    refreshListener2.onTokenErrorResponse(this, e.getDetails());
                }
                if (statusCode4xx) {
                    throw e;
                }
            }
            return false;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public Credential setFromTokenResponse(final TokenResponse tokenResponse) {
        this.setAccessToken(tokenResponse.getAccessToken());
        if (tokenResponse.getRefreshToken() != null) {
            this.setRefreshToken(tokenResponse.getRefreshToken());
        }
        this.setExpiresInSeconds(tokenResponse.getExpiresInSeconds());
        return this;
    }
    
    protected TokenResponse executeRefreshToken() throws IOException {
        if (this.refreshToken == null) {
            return null;
        }
        return new RefreshTokenRequest(this.transport, this.jsonFactory, new GenericUrl(this.tokenServerEncodedUrl), this.refreshToken).setClientAuthentication(this.clientAuthentication).setRequestInitializer(this.requestInitializer).execute();
    }
    
    public final Collection<CredentialRefreshListener> getRefreshListeners() {
        return this.refreshListeners;
    }
    
    static {
        LOGGER = Logger.getLogger(Credential.class.getName());
    }
    
    public static class Builder
    {
        final AccessMethod method;
        HttpTransport transport;
        JsonFactory jsonFactory;
        GenericUrl tokenServerUrl;
        Clock clock;
        HttpExecuteInterceptor clientAuthentication;
        HttpRequestInitializer requestInitializer;
        Collection<CredentialRefreshListener> refreshListeners;
        
        public Builder(final AccessMethod method) {
            super();
            this.clock = Clock.SYSTEM;
            this.refreshListeners = (Collection<CredentialRefreshListener>)Lists.newArrayList();
            this.method = Preconditions.checkNotNull(method);
        }
        
        public Credential build() {
            return new Credential(this);
        }
        
        public final AccessMethod getMethod() {
            return this.method;
        }
        
        public final HttpTransport getTransport() {
            return this.transport;
        }
        
        public Builder setTransport(final HttpTransport transport) {
            this.transport = transport;
            return this;
        }
        
        public final Clock getClock() {
            return this.clock;
        }
        
        public Builder setClock(final Clock clock) {
            this.clock = Preconditions.checkNotNull(clock);
            return this;
        }
        
        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }
        
        public Builder setJsonFactory(final JsonFactory jsonFactory) {
            this.jsonFactory = jsonFactory;
            return this;
        }
        
        public final GenericUrl getTokenServerUrl() {
            return this.tokenServerUrl;
        }
        
        public Builder setTokenServerUrl(final GenericUrl tokenServerUrl) {
            this.tokenServerUrl = tokenServerUrl;
            return this;
        }
        
        public Builder setTokenServerEncodedUrl(final String tokenServerEncodedUrl) {
            this.tokenServerUrl = ((tokenServerEncodedUrl == null) ? null : new GenericUrl(tokenServerEncodedUrl));
            return this;
        }
        
        public final HttpExecuteInterceptor getClientAuthentication() {
            return this.clientAuthentication;
        }
        
        public Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
            this.clientAuthentication = clientAuthentication;
            return this;
        }
        
        public final HttpRequestInitializer getRequestInitializer() {
            return this.requestInitializer;
        }
        
        public Builder setRequestInitializer(final HttpRequestInitializer requestInitializer) {
            this.requestInitializer = requestInitializer;
            return this;
        }
        
        public Builder addRefreshListener(final CredentialRefreshListener refreshListener) {
            this.refreshListeners.add(Preconditions.checkNotNull(refreshListener));
            return this;
        }
        
        public final Collection<CredentialRefreshListener> getRefreshListeners() {
            return this.refreshListeners;
        }
        
        public Builder setRefreshListeners(final Collection<CredentialRefreshListener> refreshListeners) {
            this.refreshListeners = Preconditions.checkNotNull(refreshListeners);
            return this;
        }
    }
    
    public interface AccessMethod
    {
        void intercept(final HttpRequest p0, final String p1) throws IOException;
        
        String getAccessTokenFromRequest(final HttpRequest p0);
    }
}
