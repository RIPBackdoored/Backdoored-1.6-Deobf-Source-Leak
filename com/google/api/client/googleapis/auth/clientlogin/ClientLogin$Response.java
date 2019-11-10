package com.google.api.client.googleapis.auth.clientlogin;

import com.google.api.client.util.*;
import com.google.api.client.http.*;

public static final class Response implements HttpExecuteInterceptor, HttpRequestInitializer
{
    @Key("Auth")
    public String auth;
    
    public Response() {
        super();
    }
    
    public String getAuthorizationHeaderValue() {
        return ClientLogin.getAuthorizationHeaderValue(this.auth);
    }
    
    public void initialize(final HttpRequest request) {
        request.setInterceptor(this);
    }
    
    public void intercept(final HttpRequest request) {
        request.getHeaders().setAuthorization(this.getAuthorizationHeaderValue());
    }
}
