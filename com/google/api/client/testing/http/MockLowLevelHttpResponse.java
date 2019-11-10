package com.google.api.client.testing.http;

import com.google.api.client.http.*;
import java.util.*;
import com.google.api.client.util.*;
import com.google.api.client.testing.util.*;
import java.io.*;

@Beta
public class MockLowLevelHttpResponse extends LowLevelHttpResponse
{
    private InputStream content;
    private String contentType;
    private int statusCode;
    private String reasonPhrase;
    private List<String> headerNames;
    private List<String> headerValues;
    private String contentEncoding;
    private long contentLength;
    private boolean isDisconnected;
    
    public MockLowLevelHttpResponse() {
        super();
        this.statusCode = 200;
        this.headerNames = new ArrayList<String>();
        this.headerValues = new ArrayList<String>();
        this.contentLength = -1L;
    }
    
    public MockLowLevelHttpResponse addHeader(final String name, final String value) {
        this.headerNames.add(Preconditions.checkNotNull(name));
        this.headerValues.add(Preconditions.checkNotNull(value));
        return this;
    }
    
    public MockLowLevelHttpResponse setContent(final String stringContent) {
        return (stringContent == null) ? this.setZeroContent() : this.setContent(StringUtils.getBytesUtf8(stringContent));
    }
    
    public MockLowLevelHttpResponse setContent(final byte[] byteContent) {
        if (byteContent == null) {
            return this.setZeroContent();
        }
        this.content = new TestableByteArrayInputStream(byteContent);
        this.setContentLength(byteContent.length);
        return this;
    }
    
    public MockLowLevelHttpResponse setZeroContent() {
        this.content = null;
        this.setContentLength(0L);
        return this;
    }
    
    @Override
    public InputStream getContent() throws IOException {
        return this.content;
    }
    
    @Override
    public String getContentEncoding() {
        return this.contentEncoding;
    }
    
    @Override
    public long getContentLength() {
        return this.contentLength;
    }
    
    @Override
    public final String getContentType() {
        return this.contentType;
    }
    
    @Override
    public int getHeaderCount() {
        return this.headerNames.size();
    }
    
    @Override
    public String getHeaderName(final int index) {
        return this.headerNames.get(index);
    }
    
    @Override
    public String getHeaderValue(final int index) {
        return this.headerValues.get(index);
    }
    
    @Override
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
    
    @Override
    public int getStatusCode() {
        return this.statusCode;
    }
    
    @Override
    public String getStatusLine() {
        final StringBuilder buf = new StringBuilder();
        buf.append(this.statusCode);
        if (this.reasonPhrase != null) {
            buf.append(this.reasonPhrase);
        }
        return buf.toString();
    }
    
    public final List<String> getHeaderNames() {
        return this.headerNames;
    }
    
    public MockLowLevelHttpResponse setHeaderNames(final List<String> headerNames) {
        this.headerNames = Preconditions.checkNotNull(headerNames);
        return this;
    }
    
    public final List<String> getHeaderValues() {
        return this.headerValues;
    }
    
    public MockLowLevelHttpResponse setHeaderValues(final List<String> headerValues) {
        this.headerValues = Preconditions.checkNotNull(headerValues);
        return this;
    }
    
    public MockLowLevelHttpResponse setContent(final InputStream content) {
        this.content = content;
        return this;
    }
    
    public MockLowLevelHttpResponse setContentType(final String contentType) {
        this.contentType = contentType;
        return this;
    }
    
    public MockLowLevelHttpResponse setContentEncoding(final String contentEncoding) {
        this.contentEncoding = contentEncoding;
        return this;
    }
    
    public MockLowLevelHttpResponse setContentLength(final long contentLength) {
        this.contentLength = contentLength;
        Preconditions.checkArgument(contentLength >= -1L);
        return this;
    }
    
    public MockLowLevelHttpResponse setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
        return this;
    }
    
    public MockLowLevelHttpResponse setReasonPhrase(final String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
        return this;
    }
    
    @Override
    public void disconnect() throws IOException {
        this.isDisconnected = true;
        super.disconnect();
    }
    
    public boolean isDisconnected() {
        return this.isDisconnected;
    }
}
