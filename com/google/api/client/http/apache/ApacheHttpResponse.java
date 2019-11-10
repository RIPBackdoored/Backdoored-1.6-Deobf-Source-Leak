package com.google.api.client.http.apache;

import com.google.api.client.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.*;
import java.io.*;

final class ApacheHttpResponse extends LowLevelHttpResponse
{
    private final HttpRequestBase request;
    private final HttpResponse response;
    private final Header[] allHeaders;
    
    ApacheHttpResponse(final HttpRequestBase request, final HttpResponse response) {
        super();
        this.request = request;
        this.response = response;
        this.allHeaders = response.getAllHeaders();
    }
    
    @Override
    public int getStatusCode() {
        final StatusLine statusLine = this.response.getStatusLine();
        return (statusLine == null) ? 0 : statusLine.getStatusCode();
    }
    
    @Override
    public InputStream getContent() throws IOException {
        final HttpEntity entity = this.response.getEntity();
        return (entity == null) ? null : entity.getContent();
    }
    
    @Override
    public String getContentEncoding() {
        final HttpEntity entity = this.response.getEntity();
        if (entity != null) {
            final Header contentEncodingHeader = entity.getContentEncoding();
            if (contentEncodingHeader != null) {
                return contentEncodingHeader.getValue();
            }
        }
        return null;
    }
    
    @Override
    public long getContentLength() {
        final HttpEntity entity = this.response.getEntity();
        return (entity == null) ? -1L : entity.getContentLength();
    }
    
    @Override
    public String getContentType() {
        final HttpEntity entity = this.response.getEntity();
        if (entity != null) {
            final Header contentTypeHeader = entity.getContentType();
            if (contentTypeHeader != null) {
                return contentTypeHeader.getValue();
            }
        }
        return null;
    }
    
    @Override
    public String getReasonPhrase() {
        final StatusLine statusLine = this.response.getStatusLine();
        return (statusLine == null) ? null : statusLine.getReasonPhrase();
    }
    
    @Override
    public String getStatusLine() {
        final StatusLine statusLine = this.response.getStatusLine();
        return (statusLine == null) ? null : statusLine.toString();
    }
    
    public String getHeaderValue(final String name) {
        return this.response.getLastHeader(name).getValue();
    }
    
    @Override
    public int getHeaderCount() {
        return this.allHeaders.length;
    }
    
    @Override
    public String getHeaderName(final int index) {
        return this.allHeaders[index].getName();
    }
    
    @Override
    public String getHeaderValue(final int index) {
        return this.allHeaders[index].getValue();
    }
    
    @Override
    public void disconnect() {
        this.request.abort();
    }
}
