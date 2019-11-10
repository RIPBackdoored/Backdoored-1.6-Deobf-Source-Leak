package com.google.api.client.googleapis.testing.auth.oauth2;

import com.google.api.client.util.*;
import com.google.api.client.http.*;
import java.io.*;

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
