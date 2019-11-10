package com.google.api.client.http.javanet;

import java.net.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;
import java.io.*;

final class NetHttpRequest extends LowLevelHttpRequest
{
    private final HttpURLConnection connection;
    
    NetHttpRequest(final HttpURLConnection connection) {
        super();
        (this.connection = connection).setInstanceFollowRedirects(false);
    }
    
    @Override
    public void addHeader(final String name, final String value) {
        this.connection.addRequestProperty(name, value);
    }
    
    @Override
    public void setTimeout(final int connectTimeout, final int readTimeout) {
        this.connection.setReadTimeout(readTimeout);
        this.connection.setConnectTimeout(connectTimeout);
    }
    
    @Override
    public LowLevelHttpResponse execute() throws IOException {
        final HttpURLConnection connection = this.connection;
        if (this.getStreamingContent() != null) {
            final String contentType = this.getContentType();
            if (contentType != null) {
                this.addHeader("Content-Type", contentType);
            }
            final String contentEncoding = this.getContentEncoding();
            if (contentEncoding != null) {
                this.addHeader("Content-Encoding", contentEncoding);
            }
            final long contentLength = this.getContentLength();
            if (contentLength >= 0L) {
                connection.setRequestProperty("Content-Length", Long.toString(contentLength));
            }
            final String requestMethod = connection.getRequestMethod();
            if ("POST".equals(requestMethod) || "PUT".equals(requestMethod)) {
                connection.setDoOutput(true);
                if (contentLength >= 0L && contentLength <= 2147483647L) {
                    connection.setFixedLengthStreamingMode((int)contentLength);
                }
                else {
                    connection.setChunkedStreamingMode(0);
                }
                final OutputStream out = connection.getOutputStream();
                boolean threw = true;
                try {
                    this.getStreamingContent().writeTo(out);
                    threw = false;
                }
                finally {
                    try {
                        out.close();
                    }
                    catch (IOException exception) {
                        if (!threw) {
                            throw exception;
                        }
                    }
                }
            }
            else {
                Preconditions.checkArgument(contentLength == 0L, "%s with non-zero content length is not supported", requestMethod);
            }
        }
        boolean successfulConnection = false;
        try {
            connection.connect();
            final NetHttpResponse response = new NetHttpResponse(connection);
            successfulConnection = true;
            return response;
        }
        finally {
            if (!successfulConnection) {
                connection.disconnect();
            }
        }
    }
}
