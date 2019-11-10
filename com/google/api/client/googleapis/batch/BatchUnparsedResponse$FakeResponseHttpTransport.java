package com.google.api.client.googleapis.batch;

import java.io.*;
import java.util.*;
import com.google.api.client.http.*;

private static class FakeResponseHttpTransport extends HttpTransport
{
    private int statusCode;
    private InputStream partContent;
    private List<String> headerNames;
    private List<String> headerValues;
    
    FakeResponseHttpTransport(final int statusCode, final InputStream partContent, final List<String> headerNames, final List<String> headerValues) {
        super();
        this.statusCode = statusCode;
        this.partContent = partContent;
        this.headerNames = headerNames;
        this.headerValues = headerValues;
    }
    
    @Override
    protected LowLevelHttpRequest buildRequest(final String method, final String url) {
        return new FakeLowLevelHttpRequest(this.partContent, this.statusCode, this.headerNames, this.headerValues);
    }
}
