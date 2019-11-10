package com.google.api.client.googleapis.batch;

import java.io.*;
import java.util.*;
import com.google.api.client.http.*;

private static class FakeLowLevelHttpRequest extends LowLevelHttpRequest
{
    private InputStream partContent;
    private int statusCode;
    private List<String> headerNames;
    private List<String> headerValues;
    
    FakeLowLevelHttpRequest(final InputStream partContent, final int statusCode, final List<String> headerNames, final List<String> headerValues) {
        super();
        this.partContent = partContent;
        this.statusCode = statusCode;
        this.headerNames = headerNames;
        this.headerValues = headerValues;
    }
    
    @Override
    public void addHeader(final String name, final String value) {
    }
    
    @Override
    public LowLevelHttpResponse execute() {
        final FakeLowLevelHttpResponse response = new FakeLowLevelHttpResponse(this.partContent, this.statusCode, this.headerNames, this.headerValues);
        return response;
    }
}
