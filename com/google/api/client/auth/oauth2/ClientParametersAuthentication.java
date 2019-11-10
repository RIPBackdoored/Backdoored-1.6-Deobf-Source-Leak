package com.google.api.client.auth.oauth2;

import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;
import java.util.*;

public class ClientParametersAuthentication implements HttpRequestInitializer, HttpExecuteInterceptor
{
    private final String clientId;
    private final String clientSecret;
    
    public ClientParametersAuthentication(final String clientId, final String clientSecret) {
        super();
        this.clientId = Preconditions.checkNotNull(clientId);
        this.clientSecret = clientSecret;
    }
    
    @Override
    public void initialize(final HttpRequest request) throws IOException {
        request.setInterceptor(this);
    }
    
    @Override
    public void intercept(final HttpRequest request) throws IOException {
        final Map<String, Object> data = Data.mapOf(UrlEncodedContent.getContent(request).getData());
        data.put("client_id", this.clientId);
        if (this.clientSecret != null) {
            data.put("client_secret", this.clientSecret);
        }
    }
    
    public final String getClientId() {
        return this.clientId;
    }
    
    public final String getClientSecret() {
        return this.clientSecret;
    }
}
