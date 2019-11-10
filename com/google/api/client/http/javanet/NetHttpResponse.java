package com.google.api.client.http.javanet;

import com.google.api.client.http.*;
import java.net.*;
import java.util.*;
import java.io.*;

final class NetHttpResponse extends LowLevelHttpResponse
{
    private final HttpURLConnection connection;
    private final int responseCode;
    private final String responseMessage;
    private final ArrayList<String> headerNames;
    private final ArrayList<String> headerValues;
    
    NetHttpResponse(final HttpURLConnection connection) throws IOException {
        super();
        this.headerNames = new ArrayList<String>();
        this.headerValues = new ArrayList<String>();
        this.connection = connection;
        final int responseCode = connection.getResponseCode();
        this.responseCode = ((responseCode == -1) ? 0 : responseCode);
        this.responseMessage = connection.getResponseMessage();
        final List<String> headerNames = this.headerNames;
        final List<String> headerValues = this.headerValues;
        for (final Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
            final String key = entry.getKey();
            if (key != null) {
                for (final String value : entry.getValue()) {
                    if (value != null) {
                        headerNames.add(key);
                        headerValues.add(value);
                    }
                }
            }
        }
    }
    
    @Override
    public int getStatusCode() {
        return this.responseCode;
    }
    
    @Override
    public InputStream getContent() throws IOException {
        InputStream in = null;
        try {
            in = this.connection.getInputStream();
        }
        catch (IOException ioe) {
            in = this.connection.getErrorStream();
        }
        return (in == null) ? null : new SizeValidatingInputStream(in);
    }
    
    @Override
    public String getContentEncoding() {
        return this.connection.getContentEncoding();
    }
    
    @Override
    public long getContentLength() {
        final String string = this.connection.getHeaderField("Content-Length");
        return (string == null) ? -1L : Long.parseLong(string);
    }
    
    @Override
    public String getContentType() {
        return this.connection.getHeaderField("Content-Type");
    }
    
    @Override
    public String getReasonPhrase() {
        return this.responseMessage;
    }
    
    @Override
    public String getStatusLine() {
        final String result = this.connection.getHeaderField(0);
        return (result != null && result.startsWith("HTTP/1.")) ? result : null;
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
    public void disconnect() {
        this.connection.disconnect();
    }
    
    private final class SizeValidatingInputStream extends FilterInputStream
    {
        private long bytesRead;
        final /* synthetic */ NetHttpResponse this$0;
        
        public SizeValidatingInputStream(final NetHttpResponse this$0, final InputStream in) {
            this.this$0 = this$0;
            super(in);
            this.bytesRead = 0L;
        }
        
        @Override
        public int read(final byte[] b, final int off, final int len) throws IOException {
            final int n = this.in.read(b, off, len);
            if (n == -1) {
                this.throwIfFalseEOF();
            }
            else {
                this.bytesRead += n;
            }
            return n;
        }
        
        @Override
        public int read() throws IOException {
            final int n = this.in.read();
            if (n == -1) {
                this.throwIfFalseEOF();
            }
            else {
                ++this.bytesRead;
            }
            return n;
        }
        
        private void throwIfFalseEOF() throws IOException {
            final long contentLength = this.this$0.getContentLength();
            if (contentLength == -1L) {
                return;
            }
            if (this.bytesRead != 0L && this.bytesRead < contentLength) {
                throw new IOException("Connection closed prematurely: bytesRead = " + this.bytesRead + ", Content-Length = " + contentLength);
            }
        }
    }
}
