package com.google.api.client.googleapis.batch;

import java.util.logging.*;
import com.google.api.client.http.*;
import java.io.*;

class HttpRequestContent extends AbstractHttpContent
{
    static final String NEWLINE = "\r\n";
    private final HttpRequest request;
    private static final String HTTP_VERSION = "HTTP/1.1";
    
    HttpRequestContent(final HttpRequest request) {
        super("application/http");
        this.request = request;
    }
    
    public void writeTo(final OutputStream out) throws IOException {
        final Writer writer = new OutputStreamWriter(out, this.getCharset());
        writer.write(this.request.getRequestMethod());
        writer.write(" ");
        writer.write(this.request.getUrl().build());
        writer.write(" ");
        writer.write("HTTP/1.1");
        writer.write("\r\n");
        final HttpHeaders headers = new HttpHeaders();
        headers.fromHttpHeaders(this.request.getHeaders());
        headers.setAcceptEncoding(null).setUserAgent(null).setContentEncoding(null).setContentType(null).setContentLength(null);
        final HttpContent content = this.request.getContent();
        if (content != null) {
            headers.setContentType(content.getType());
            final long contentLength = content.getLength();
            if (contentLength != -1L) {
                headers.setContentLength(contentLength);
            }
        }
        HttpHeaders.serializeHeadersForMultipartRequests(headers, null, null, writer);
        writer.write("\r\n");
        writer.flush();
        if (content != null) {
            content.writeTo(out);
        }
    }
}
