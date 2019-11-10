package com.google.api.client.auth.oauth2;

import java.util.regex.*;
import java.io.*;
import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class BearerToken
{
    static final String PARAM_NAME = "access_token";
    static final Pattern INVALID_TOKEN_ERROR;
    
    public BearerToken() {
        super();
    }
    
    public static Credential.AccessMethod authorizationHeaderAccessMethod() {
        return new AuthorizationHeaderAccessMethod();
    }
    
    public static Credential.AccessMethod formEncodedBodyAccessMethod() {
        return new FormEncodedBodyAccessMethod();
    }
    
    public static Credential.AccessMethod queryParameterAccessMethod() {
        return new QueryParameterAccessMethod();
    }
    
    static {
        INVALID_TOKEN_ERROR = Pattern.compile("\\s*error\\s*=\\s*\"?invalid_token\"?");
    }
    
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
    
    static final class FormEncodedBodyAccessMethod implements Credential.AccessMethod
    {
        FormEncodedBodyAccessMethod() {
            super();
        }
        
        @Override
        public void intercept(final HttpRequest request, final String accessToken) throws IOException {
            Preconditions.checkArgument(!"GET".equals(request.getRequestMethod()), (Object)"HTTP GET method is not supported");
            getData(request).put("access_token", accessToken);
        }
        
        @Override
        public String getAccessTokenFromRequest(final HttpRequest request) {
            final Object bodyParam = getData(request).get("access_token");
            return (bodyParam == null) ? null : bodyParam.toString();
        }
        
        private static Map<String, Object> getData(final HttpRequest request) {
            return Data.mapOf(UrlEncodedContent.getContent(request).getData());
        }
    }
    
    static final class QueryParameterAccessMethod implements Credential.AccessMethod
    {
        QueryParameterAccessMethod() {
            super();
        }
        
        @Override
        public void intercept(final HttpRequest request, final String accessToken) throws IOException {
            request.getUrl().set("access_token", accessToken);
        }
        
        @Override
        public String getAccessTokenFromRequest(final HttpRequest request) {
            final Object param = request.getUrl().get("access_token");
            return (param == null) ? null : param.toString();
        }
    }
}
