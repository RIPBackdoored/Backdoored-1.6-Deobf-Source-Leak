package com.google.api.client.testing.http.javanet;

import java.net.*;
import java.io.*;
import com.google.api.client.util.*;
import java.util.*;

@Beta
public class MockHttpURLConnection extends HttpURLConnection
{
    private boolean doOutputCalled;
    private OutputStream outputStream;
    @Deprecated
    public static final byte[] INPUT_BUF;
    @Deprecated
    public static final byte[] ERROR_BUF;
    private InputStream inputStream;
    private InputStream errorStream;
    private Map<String, List<String>> headers;
    
    public MockHttpURLConnection(final URL u) {
        super(u);
        this.outputStream = new ByteArrayOutputStream(0);
        this.inputStream = null;
        this.errorStream = null;
        this.headers = new LinkedHashMap<String, List<String>>();
    }
    
    @Override
    public void disconnect() {
    }
    
    @Override
    public boolean usingProxy() {
        return false;
    }
    
    @Override
    public void connect() throws IOException {
    }
    
    @Override
    public int getResponseCode() throws IOException {
        return this.responseCode;
    }
    
    @Override
    public void setDoOutput(final boolean dooutput) {
        this.doOutputCalled = true;
    }
    
    @Override
    public OutputStream getOutputStream() throws IOException {
        if (this.outputStream != null) {
            return this.outputStream;
        }
        return super.getOutputStream();
    }
    
    public final boolean doOutputCalled() {
        return this.doOutputCalled;
    }
    
    public MockHttpURLConnection setOutputStream(final OutputStream outputStream) {
        this.outputStream = outputStream;
        return this;
    }
    
    public MockHttpURLConnection setResponseCode(final int responseCode) {
        Preconditions.checkArgument(responseCode >= -1);
        this.responseCode = responseCode;
        return this;
    }
    
    public MockHttpURLConnection addHeader(final String name, final String value) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(value);
        if (this.headers.containsKey(name)) {
            this.headers.get(name).add(value);
        }
        else {
            final List<String> values = new ArrayList<String>();
            values.add(value);
            this.headers.put(name, values);
        }
        return this;
    }
    
    public MockHttpURLConnection setInputStream(final InputStream is) {
        Preconditions.checkNotNull(is);
        if (this.inputStream == null) {
            this.inputStream = is;
        }
        return this;
    }
    
    public MockHttpURLConnection setErrorStream(final InputStream is) {
        Preconditions.checkNotNull(is);
        if (this.errorStream == null) {
            this.errorStream = is;
        }
        return this;
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        if (this.responseCode < 400) {
            return this.inputStream;
        }
        throw new IOException();
    }
    
    @Override
    public InputStream getErrorStream() {
        return this.errorStream;
    }
    
    @Override
    public Map<String, List<String>> getHeaderFields() {
        return this.headers;
    }
    
    @Override
    public String getHeaderField(final String name) {
        final List<String> values = this.headers.get(name);
        return (values == null) ? null : values.get(0);
    }
    
    static {
        INPUT_BUF = new byte[1];
        ERROR_BUF = new byte[5];
    }
}
