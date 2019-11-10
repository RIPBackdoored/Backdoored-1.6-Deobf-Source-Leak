package com.google.api.client.auth.oauth2;

import java.io.*;
import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

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
