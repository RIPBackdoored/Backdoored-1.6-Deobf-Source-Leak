package com.google.api.client.testing.http;

import com.google.api.client.util.*;
import com.google.api.client.http.*;
import java.io.*;

@Beta
public class MockHttpUnsuccessfulResponseHandler implements HttpUnsuccessfulResponseHandler
{
    private boolean isCalled;
    private boolean successfullyHandleResponse;
    
    public MockHttpUnsuccessfulResponseHandler(final boolean successfullyHandleResponse) {
        super();
        this.successfullyHandleResponse = successfullyHandleResponse;
    }
    
    public boolean isCalled() {
        return this.isCalled;
    }
    
    @Override
    public boolean handleResponse(final HttpRequest request, final HttpResponse response, final boolean supportsRetry) throws IOException {
        this.isCalled = true;
        return this.successfullyHandleResponse;
    }
}
