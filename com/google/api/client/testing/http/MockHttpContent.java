package com.google.api.client.testing.http;

import com.google.api.client.http.*;
import java.io.*;
import com.google.api.client.util.*;

@Beta
public class MockHttpContent implements HttpContent
{
    private long length;
    private String type;
    private byte[] content;
    
    public MockHttpContent() {
        super();
        this.length = -1L;
        this.content = new byte[0];
    }
    
    @Override
    public long getLength() throws IOException {
        return this.length;
    }
    
    @Override
    public String getType() {
        return this.type;
    }
    
    @Override
    public void writeTo(final OutputStream out) throws IOException {
        out.write(this.content);
        out.flush();
    }
    
    @Override
    public boolean retrySupported() {
        return true;
    }
    
    public final byte[] getContent() {
        return this.content;
    }
    
    public MockHttpContent setContent(final byte[] content) {
        this.content = Preconditions.checkNotNull(content);
        return this;
    }
    
    public MockHttpContent setLength(final long length) {
        Preconditions.checkArgument(length >= -1L);
        this.length = length;
        return this;
    }
    
    public MockHttpContent setType(final String type) {
        this.type = type;
        return this;
    }
}
