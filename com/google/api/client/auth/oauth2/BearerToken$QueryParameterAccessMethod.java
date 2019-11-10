package com.google.api.client.auth.oauth2;

import com.google.api.client.http.*;
import java.io.*;

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
