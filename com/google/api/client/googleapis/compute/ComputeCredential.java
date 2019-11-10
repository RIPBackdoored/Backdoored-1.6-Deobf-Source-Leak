package com.google.api.client.googleapis.compute;

import com.google.api.client.json.*;
import java.io.*;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.util.*;
import com.google.api.client.http.*;
import com.google.api.client.auth.oauth2.*;
import java.util.*;

@Beta
public class ComputeCredential extends Credential
{
    public static final String TOKEN_SERVER_ENCODED_URL;
    
    public ComputeCredential(final HttpTransport transport, final JsonFactory jsonFactory) {
        this(new Builder(transport, jsonFactory));
    }
    
    protected ComputeCredential(final Builder builder) {
        super(builder);
    }
    
    @Override
    protected TokenResponse executeRefreshToken() throws IOException {
        final GenericUrl tokenUrl = new GenericUrl(this.getTokenServerEncodedUrl());
        final HttpRequest request = this.getTransport().createRequestFactory().buildGetRequest(tokenUrl);
        request.setParser(new JsonObjectParser(this.getJsonFactory()));
        request.getHeaders().set("Metadata-Flavor", "Google");
        return request.execute().parseAs(TokenResponse.class);
    }
    
    static {
        TOKEN_SERVER_ENCODED_URL = String.valueOf(OAuth2Utils.getMetadataServerUrl()).concat("/computeMetadata/v1/instance/service-accounts/default/token");
    }
    
    @Beta
    public static class Builder extends Credential.Builder
    {
        public Builder(final HttpTransport transport, final JsonFactory jsonFactory) {
            super(BearerToken.authorizationHeaderAccessMethod());
            this.setTransport(transport);
            this.setJsonFactory(jsonFactory);
            this.setTokenServerEncodedUrl(ComputeCredential.TOKEN_SERVER_ENCODED_URL);
        }
        
        @Override
        public ComputeCredential build() {
            return new ComputeCredential(this);
        }
        
        @Override
        public Builder setTransport(final HttpTransport transport) {
            return (Builder)super.setTransport(Preconditions.checkNotNull(transport));
        }
        
        @Override
        public Builder setClock(final Clock clock) {
            return (Builder)super.setClock(clock);
        }
        
        @Override
        public Builder setJsonFactory(final JsonFactory jsonFactory) {
            return (Builder)super.setJsonFactory(Preconditions.checkNotNull(jsonFactory));
        }
        
        @Override
        public Builder setTokenServerUrl(final GenericUrl tokenServerUrl) {
            return (Builder)super.setTokenServerUrl(Preconditions.checkNotNull(tokenServerUrl));
        }
        
        @Override
        public Builder setTokenServerEncodedUrl(final String tokenServerEncodedUrl) {
            return (Builder)super.setTokenServerEncodedUrl(Preconditions.checkNotNull(tokenServerEncodedUrl));
        }
        
        @Override
        public Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
            Preconditions.checkArgument(clientAuthentication == null);
            return this;
        }
        
        @Override
        public Builder setRequestInitializer(final HttpRequestInitializer requestInitializer) {
            return (Builder)super.setRequestInitializer(requestInitializer);
        }
        
        @Override
        public Builder addRefreshListener(final CredentialRefreshListener refreshListener) {
            return (Builder)super.addRefreshListener(refreshListener);
        }
        
        @Override
        public Builder setRefreshListeners(final Collection<CredentialRefreshListener> refreshListeners) {
            return (Builder)super.setRefreshListeners(refreshListeners);
        }
        
        @Override
        public /* bridge */ Credential.Builder setRefreshListeners(final Collection refreshListeners) {
            return this.setRefreshListeners(refreshListeners);
        }
        
        @Override
        public /* bridge */ Credential.Builder addRefreshListener(final CredentialRefreshListener refreshListener) {
            return this.addRefreshListener(refreshListener);
        }
        
        @Override
        public /* bridge */ Credential.Builder setRequestInitializer(final HttpRequestInitializer requestInitializer) {
            return this.setRequestInitializer(requestInitializer);
        }
        
        @Override
        public /* bridge */ Credential.Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
            return this.setClientAuthentication(clientAuthentication);
        }
        
        @Override
        public /* bridge */ Credential.Builder setTokenServerEncodedUrl(final String tokenServerEncodedUrl) {
            return this.setTokenServerEncodedUrl(tokenServerEncodedUrl);
        }
        
        @Override
        public /* bridge */ Credential.Builder setTokenServerUrl(final GenericUrl tokenServerUrl) {
            return this.setTokenServerUrl(tokenServerUrl);
        }
        
        @Override
        public /* bridge */ Credential.Builder setJsonFactory(final JsonFactory jsonFactory) {
            return this.setJsonFactory(jsonFactory);
        }
        
        @Override
        public /* bridge */ Credential.Builder setClock(final Clock clock) {
            return this.setClock(clock);
        }
        
        @Override
        public /* bridge */ Credential.Builder setTransport(final HttpTransport transport) {
            return this.setTransport(transport);
        }
        
        @Override
        public /* bridge */ Credential build() {
            return this.build();
        }
    }
}
