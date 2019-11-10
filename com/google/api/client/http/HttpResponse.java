package com.google.api.client.http;

import java.util.logging.*;
import java.util.zip.*;
import java.lang.reflect.*;
import java.io.*;
import java.nio.charset.*;
import com.google.api.client.util.*;

public final class HttpResponse
{
    private InputStream content;
    private final String contentEncoding;
    private final String contentType;
    private final HttpMediaType mediaType;
    LowLevelHttpResponse response;
    private final int statusCode;
    private final String statusMessage;
    private final HttpRequest request;
    private int contentLoggingLimit;
    private boolean loggingEnabled;
    private boolean contentRead;
    
    HttpResponse(final HttpRequest request, final LowLevelHttpResponse response) throws IOException {
        super();
        this.request = request;
        this.contentLoggingLimit = request.getContentLoggingLimit();
        this.loggingEnabled = request.isLoggingEnabled();
        this.response = response;
        this.contentEncoding = response.getContentEncoding();
        final int code = response.getStatusCode();
        this.statusCode = ((code < 0) ? 0 : code);
        final String message = response.getReasonPhrase();
        this.statusMessage = message;
        final Logger logger = HttpTransport.LOGGER;
        final boolean loggable = this.loggingEnabled && logger.isLoggable(Level.CONFIG);
        StringBuilder logbuf = null;
        if (loggable) {
            logbuf = new StringBuilder();
            logbuf.append("-------------- RESPONSE --------------").append(StringUtils.LINE_SEPARATOR);
            final String statusLine = response.getStatusLine();
            if (statusLine != null) {
                logbuf.append(statusLine);
            }
            else {
                logbuf.append(this.statusCode);
                if (message != null) {
                    logbuf.append(' ').append(message);
                }
            }
            logbuf.append(StringUtils.LINE_SEPARATOR);
        }
        request.getResponseHeaders().fromHttpResponse(response, loggable ? logbuf : null);
        String contentType = response.getContentType();
        if (contentType == null) {
            contentType = request.getResponseHeaders().getContentType();
        }
        this.mediaType = (((this.contentType = contentType) == null) ? null : new HttpMediaType(contentType));
        if (loggable) {
            logger.config(logbuf.toString());
        }
    }
    
    public int getContentLoggingLimit() {
        return this.contentLoggingLimit;
    }
    
    public HttpResponse setContentLoggingLimit(final int contentLoggingLimit) {
        Preconditions.checkArgument(contentLoggingLimit >= 0, (Object)"The content logging limit must be non-negative.");
        this.contentLoggingLimit = contentLoggingLimit;
        return this;
    }
    
    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }
    
    public HttpResponse setLoggingEnabled(final boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
        return this;
    }
    
    public String getContentEncoding() {
        return this.contentEncoding;
    }
    
    public String getContentType() {
        return this.contentType;
    }
    
    public HttpMediaType getMediaType() {
        return this.mediaType;
    }
    
    public HttpHeaders getHeaders() {
        return this.request.getResponseHeaders();
    }
    
    public boolean isSuccessStatusCode() {
        return HttpStatusCodes.isSuccess(this.statusCode);
    }
    
    public int getStatusCode() {
        return this.statusCode;
    }
    
    public String getStatusMessage() {
        return this.statusMessage;
    }
    
    public HttpTransport getTransport() {
        return this.request.getTransport();
    }
    
    public HttpRequest getRequest() {
        return this.request;
    }
    
    public InputStream getContent() throws IOException {
        if (!this.contentRead) {
            InputStream lowLevelResponseContent = this.response.getContent();
            if (lowLevelResponseContent != null) {
                boolean contentProcessed = false;
                try {
                    final String contentEncoding = this.contentEncoding;
                    if (contentEncoding != null && contentEncoding.contains("gzip")) {
                        lowLevelResponseContent = new GZIPInputStream(lowLevelResponseContent);
                    }
                    final Logger logger = HttpTransport.LOGGER;
                    if (this.loggingEnabled && logger.isLoggable(Level.CONFIG)) {
                        lowLevelResponseContent = new LoggingInputStream(lowLevelResponseContent, logger, Level.CONFIG, this.contentLoggingLimit);
                    }
                    this.content = lowLevelResponseContent;
                    contentProcessed = true;
                }
                catch (EOFException ex) {}
                finally {
                    if (!contentProcessed) {
                        lowLevelResponseContent.close();
                    }
                }
            }
            this.contentRead = true;
        }
        return this.content;
    }
    
    public void download(final OutputStream outputStream) throws IOException {
        final InputStream inputStream = this.getContent();
        IOUtils.copy(inputStream, outputStream);
    }
    
    public void ignore() throws IOException {
        final InputStream content = this.getContent();
        if (content != null) {
            content.close();
        }
    }
    
    public void disconnect() throws IOException {
        this.ignore();
        this.response.disconnect();
    }
    
    public <T> T parseAs(final Class<T> dataClass) throws IOException {
        if (!this.hasMessageBody()) {
            return null;
        }
        return this.request.getParser().parseAndClose(this.getContent(), this.getContentCharset(), dataClass);
    }
    
    private boolean hasMessageBody() throws IOException {
        final int statusCode = this.getStatusCode();
        if (this.getRequest().getRequestMethod().equals("HEAD") || statusCode / 100 == 1 || statusCode == 204 || statusCode == 304) {
            this.ignore();
            return false;
        }
        return true;
    }
    
    public Object parseAs(final Type dataType) throws IOException {
        if (!this.hasMessageBody()) {
            return null;
        }
        return this.request.getParser().parseAndClose(this.getContent(), this.getContentCharset(), dataType);
    }
    
    public String parseAsString() throws IOException {
        final InputStream content = this.getContent();
        if (content == null) {
            return "";
        }
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(content, out);
        return out.toString(this.getContentCharset().name());
    }
    
    public Charset getContentCharset() {
        return (this.mediaType == null || this.mediaType.getCharsetParameter() == null) ? Charsets.ISO_8859_1 : this.mediaType.getCharsetParameter();
    }
}
