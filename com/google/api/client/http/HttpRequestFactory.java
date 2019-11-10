package com.google.api.client.http;

import java.io.*;

public final class HttpRequestFactory
{
    private final HttpTransport transport;
    private final HttpRequestInitializer initializer;
    
    HttpRequestFactory(final HttpTransport transport, final HttpRequestInitializer initializer) {
        super();
        this.transport = transport;
        this.initializer = initializer;
    }
    
    public HttpTransport getTransport() {
        return this.transport;
    }
    
    public HttpRequestInitializer getInitializer() {
        return this.initializer;
    }
    
    public HttpRequest buildRequest(final String requestMethod, final GenericUrl url, final HttpContent content) throws IOException {
        final HttpRequest request = this.transport.buildRequest();
        if (this.initializer != null) {
            this.initializer.initialize(request);
        }
        request.setRequestMethod(requestMethod);
        if (url != null) {
            request.setUrl(url);
        }
        if (content != null) {
            request.setContent(content);
        }
        return request;
    }
    
    public HttpRequest buildDeleteRequest(final GenericUrl url) throws IOException {
        return this.buildRequest("DELETE", url, null);
    }
    
    public HttpRequest buildGetRequest(final GenericUrl url) throws IOException {
        return this.buildRequest("GET", url, null);
    }
    
    public HttpRequest buildPostRequest(final GenericUrl url, final HttpContent content) throws IOException {
        return this.buildRequest("POST", url, content);
    }
    
    public HttpRequest buildPutRequest(final GenericUrl url, final HttpContent content) throws IOException {
        return this.buildRequest("PUT", url, content);
    }
    
    public HttpRequest buildPatchRequest(final GenericUrl url, final HttpContent content) throws IOException {
        return this.buildRequest("PATCH", url, content);
    }
    
    public HttpRequest buildHeadRequest(final GenericUrl url) throws IOException {
        return this.buildRequest("HEAD", url, null);
    }
}
