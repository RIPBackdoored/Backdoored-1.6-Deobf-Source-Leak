package com.google.api.client.http;

import java.io.*;

private static class HeaderParsingFakeLevelHttpRequest extends LowLevelHttpRequest
{
    private final HttpHeaders target;
    private final ParseHeaderState state;
    
    HeaderParsingFakeLevelHttpRequest(final HttpHeaders target, final ParseHeaderState state) {
        super();
        this.target = target;
        this.state = state;
    }
    
    @Override
    public void addHeader(final String name, final String value) {
        this.target.parseHeader(name, value, this.state);
    }
    
    @Override
    public LowLevelHttpResponse execute() throws IOException {
        throw new UnsupportedOperationException();
    }
}
