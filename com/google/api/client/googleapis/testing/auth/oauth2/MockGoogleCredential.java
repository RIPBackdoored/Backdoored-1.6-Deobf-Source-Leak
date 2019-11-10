package com.google.api.client.googleapis.testing.auth.oauth2;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.testing.http.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;
import com.google.api.client.json.jackson2.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.*;
import java.io.*;

@Beta
public class MockGoogleCredential extends GoogleCredential
{
    public static final String ACCESS_TOKEN = "access_xyz";
    public static final String REFRESH_TOKEN = "refresh123";
    private static final String EXPIRES_IN_SECONDS = "3600";
    private static final String TOKEN_TYPE = "Bearer";
    private static final String TOKEN_RESPONSE = "{\"access_token\": \"%s\", \"expires_in\":  %s, \"refresh_token\": \"%s\", \"token_type\": \"%s\"}";
    private static final String DEFAULT_TOKEN_RESPONSE_JSON;
    
    public MockGoogleCredential(final Builder builder) {
        super(builder);
    }
    
    public static MockHttpTransport newMockHttpTransportWithSampleTokenResponse() {
        final MockLowLevelHttpResponse mockLowLevelHttpResponse = new MockLowLevelHttpResponse().setContentType("application/json; charset=UTF-8").setContent(MockGoogleCredential.DEFAULT_TOKEN_RESPONSE_JSON);
        final MockLowLevelHttpRequest request = new MockLowLevelHttpRequest().setResponse(mockLowLevelHttpResponse);
        return new MockHttpTransport.Builder().setLowLevelHttpRequest(request).build();
    }
    
    static {
        DEFAULT_TOKEN_RESPONSE_JSON = String.format("{\"access_token\": \"%s\", \"expires_in\":  %s, \"refresh_token\": \"%s\", \"token_type\": \"%s\"}", "access_xyz", "3600", "refresh123", "Bearer");
    }
    
    @Beta
    public static class Builder extends GoogleCredential.Builder
    {
        public Builder() {
            super();
        }
        
        @Override
        public Builder setTransport(final HttpTransport transport) {
            return (Builder)super.setTransport(transport);
        }
        
        @Override
        public Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
            return (Builder)super.setClientAuthentication(clientAuthentication);
        }
        
        @Override
        public Builder setJsonFactory(final JsonFactory jsonFactory) {
            return (Builder)super.setJsonFactory(jsonFactory);
        }
        
        @Override
        public Builder setClock(final Clock clock) {
            return (Builder)super.setClock(clock);
        }
        
        @Override
        public MockGoogleCredential build() {
            if (this.getTransport() == null) {
                this.setTransport(new MockHttpTransport.Builder().build());
            }
            if (this.getClientAuthentication() == null) {
                this.setClientAuthentication(new MockClientAuthentication());
            }
            if (this.getJsonFactory() == null) {
                this.setJsonFactory(new JacksonFactory());
            }
            return new MockGoogleCredential(this);
        }
        
        @Override
        public /* bridge */ GoogleCredential.Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
            return this.setClientAuthentication(clientAuthentication);
        }
        
        @Override
        public /* bridge */ GoogleCredential.Builder setClock(final Clock clock) {
            return this.setClock(clock);
        }
        
        @Override
        public /* bridge */ GoogleCredential.Builder setJsonFactory(final JsonFactory jsonFactory) {
            return this.setJsonFactory(jsonFactory);
        }
        
        @Override
        public /* bridge */ GoogleCredential.Builder setTransport(final HttpTransport transport) {
            return this.setTransport(transport);
        }
        
        @Override
        public /* bridge */ GoogleCredential build() {
            return this.build();
        }
        
        @Override
        public /* bridge */ Credential.Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
            return this.setClientAuthentication(clientAuthentication);
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
    
    @Beta
    private static class MockClientAuthentication implements HttpExecuteInterceptor
    {
        private MockClientAuthentication() {
            super();
        }
        
        public void intercept(final HttpRequest request) throws IOException {
        }
        
        MockClientAuthentication(final MockGoogleCredential$1 x0) {
            this();
        }
    }
}
