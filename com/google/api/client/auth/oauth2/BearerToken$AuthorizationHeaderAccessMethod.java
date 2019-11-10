package com.google.api.client.auth.oauth2;

import com.google.api.client.http.*;
import java.io.*;
import java.util.*;

static final class AuthorizationHeaderAccessMethod implements Credential.AccessMethod
{
    static final String HEADER_PREFIX = "Bearer ";
    
    AuthorizationHeaderAccessMethod() {
        super();
    }
    
    @Override
    public void intercept(final HttpRequest request, final String accessToken) throws IOException {
        request.getHeaders().setAuthorization("Bearer " + accessToken);
    }
    
    @Override
    public String getAccessTokenFromRequest(final HttpRequest request) {
        final List<String> authorizationAsList = request.getHeaders().getAuthorizationAsList();
        if (authorizationAsList != null) {
            for (final String header : authorizationAsList) {
                if (header.startsWith("Bearer ")) {
                    return header.substring("Bearer ".length());
                }
            }
        }
        return null;
    }
}
