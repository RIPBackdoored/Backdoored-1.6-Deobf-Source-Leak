package com.google.api.client.auth.oauth2;

import java.util.*;
import java.io.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;
import com.google.api.client.http.*;

public class TokenRequest extends GenericData
{
    HttpRequestInitializer requestInitializer;
    HttpExecuteInterceptor clientAuthentication;
    private final HttpTransport transport;
    private final JsonFactory jsonFactory;
    private GenericUrl tokenServerUrl;
    @Key("scope")
    private String scopes;
    @Key("grant_type")
    private String grantType;
    
    public TokenRequest(final HttpTransport transport, final JsonFactory jsonFactory, final GenericUrl tokenServerUrl, final String grantType) {
        super();
        this.transport = Preconditions.checkNotNull(transport);
        this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
        this.setTokenServerUrl(tokenServerUrl);
        this.setGrantType(grantType);
    }
    
    public final HttpTransport getTransport() {
        return this.transport;
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }
    
    public TokenRequest setRequestInitializer(final HttpRequestInitializer requestInitializer) {
        this.requestInitializer = requestInitializer;
        return this;
    }
    
    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }
    
    public TokenRequest setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        this.clientAuthentication = clientAuthentication;
        return this;
    }
    
    public final GenericUrl getTokenServerUrl() {
        return this.tokenServerUrl;
    }
    
    public TokenRequest setTokenServerUrl(final GenericUrl tokenServerUrl) {
        this.tokenServerUrl = tokenServerUrl;
        Preconditions.checkArgument(tokenServerUrl.getFragment() == null);
        return this;
    }
    
    public final String getScopes() {
        return this.scopes;
    }
    
    public TokenRequest setScopes(final Collection<String> scopes) {
        this.scopes = ((scopes == null) ? null : Joiner.on(' ').join(scopes));
        return this;
    }
    
    public final String getGrantType() {
        return this.grantType;
    }
    
    public TokenRequest setGrantType(final String grantType) {
        this.grantType = Preconditions.checkNotNull(grantType);
        return this;
    }
    
    public final HttpResponse executeUnparsed() throws IOException {
        final HttpRequestFactory requestFactory = this.transport.createRequestFactory(new HttpRequestInitializer() {
            final /* synthetic */ TokenRequest this$0;
            
            TokenRequest$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public void initialize(final HttpRequest request) throws IOException {
                if (this.this$0.requestInitializer != null) {
                    this.this$0.requestInitializer.initialize(request);
                }
                final HttpExecuteInterceptor interceptor = request.getInterceptor();
                request.setInterceptor(new HttpExecuteInterceptor() {
                    final /* synthetic */ HttpExecuteInterceptor val$interceptor;
                    final /* synthetic */ TokenRequest$1 this$1;
                    
                    TokenRequest$1$1() {
                        this.this$1 = this$1;
                        super();
                    }
                    
                    @Override
                    public void intercept(final HttpRequest request) throws IOException {
                        if (interceptor != null) {
                            interceptor.intercept(request);
                        }
                        if (this.this$1.this$0.clientAuthentication != null) {
                            this.this$1.this$0.clientAuthentication.intercept(request);
                        }
                    }
                });
            }
        });
        final HttpRequest request = requestFactory.buildPostRequest(this.tokenServerUrl, new UrlEncodedContent(this));
        request.setParser(new JsonObjectParser(this.jsonFactory));
        request.setThrowExceptionOnExecuteError(false);
        final HttpResponse response = request.execute();
        if (response.isSuccessStatusCode()) {
            return response;
        }
        throw TokenResponseException.from(this.jsonFactory, response);
    }
    
    public TokenResponse execute() throws IOException {
        return this.executeUnparsed().parseAs(TokenResponse.class);
    }
    
    @Override
    public TokenRequest set(final String fieldName, final Object value) {
        return (TokenRequest)super.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ GenericData set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
}
