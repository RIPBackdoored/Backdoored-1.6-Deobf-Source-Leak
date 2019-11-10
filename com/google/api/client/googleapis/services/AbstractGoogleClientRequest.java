package com.google.api.client.googleapis.services;

import com.google.api.client.googleapis.media.*;
import com.google.api.client.util.*;
import com.google.api.client.googleapis.*;
import java.util.*;
import com.google.api.client.http.*;
import java.io.*;
import com.google.api.client.googleapis.batch.*;

public abstract class AbstractGoogleClientRequest<T> extends GenericData
{
    public static final String USER_AGENT_SUFFIX = "Google-API-Java-Client";
    private final AbstractGoogleClient abstractGoogleClient;
    private final String requestMethod;
    private final String uriTemplate;
    private final HttpContent httpContent;
    private HttpHeaders requestHeaders;
    private HttpHeaders lastResponseHeaders;
    private int lastStatusCode;
    private String lastStatusMessage;
    private boolean disableGZipContent;
    private Class<T> responseClass;
    private MediaHttpUploader uploader;
    private MediaHttpDownloader downloader;
    
    protected AbstractGoogleClientRequest(final AbstractGoogleClient abstractGoogleClient, final String requestMethod, final String uriTemplate, final HttpContent httpContent, final Class<T> responseClass) {
        super();
        this.requestHeaders = new HttpHeaders();
        this.lastStatusCode = -1;
        this.responseClass = Preconditions.checkNotNull(responseClass);
        this.abstractGoogleClient = Preconditions.checkNotNull(abstractGoogleClient);
        this.requestMethod = Preconditions.checkNotNull(requestMethod);
        this.uriTemplate = Preconditions.checkNotNull(uriTemplate);
        this.httpContent = httpContent;
        final String applicationName = abstractGoogleClient.getApplicationName();
        if (applicationName != null) {
            final HttpHeaders requestHeaders = this.requestHeaders;
            final String value = String.valueOf(String.valueOf(applicationName));
            final String value2 = String.valueOf(String.valueOf("Google-API-Java-Client"));
            requestHeaders.setUserAgent(new StringBuilder(1 + value.length() + value2.length()).append(value).append(" ").append(value2).toString());
        }
        else {
            this.requestHeaders.setUserAgent("Google-API-Java-Client");
        }
    }
    
    public final boolean getDisableGZipContent() {
        return this.disableGZipContent;
    }
    
    public AbstractGoogleClientRequest<T> setDisableGZipContent(final boolean disableGZipContent) {
        this.disableGZipContent = disableGZipContent;
        return this;
    }
    
    public final String getRequestMethod() {
        return this.requestMethod;
    }
    
    public final String getUriTemplate() {
        return this.uriTemplate;
    }
    
    public final HttpContent getHttpContent() {
        return this.httpContent;
    }
    
    public AbstractGoogleClient getAbstractGoogleClient() {
        return this.abstractGoogleClient;
    }
    
    public final HttpHeaders getRequestHeaders() {
        return this.requestHeaders;
    }
    
    public AbstractGoogleClientRequest<T> setRequestHeaders(final HttpHeaders headers) {
        this.requestHeaders = headers;
        return this;
    }
    
    public final HttpHeaders getLastResponseHeaders() {
        return this.lastResponseHeaders;
    }
    
    public final int getLastStatusCode() {
        return this.lastStatusCode;
    }
    
    public final String getLastStatusMessage() {
        return this.lastStatusMessage;
    }
    
    public final Class<T> getResponseClass() {
        return this.responseClass;
    }
    
    public final MediaHttpUploader getMediaHttpUploader() {
        return this.uploader;
    }
    
    protected final void initializeMediaUpload(final AbstractInputStreamContent mediaContent) {
        final HttpRequestFactory requestFactory = this.abstractGoogleClient.getRequestFactory();
        (this.uploader = new MediaHttpUploader(mediaContent, requestFactory.getTransport(), requestFactory.getInitializer())).setInitiationRequestMethod(this.requestMethod);
        if (this.httpContent != null) {
            this.uploader.setMetadata(this.httpContent);
        }
    }
    
    public final MediaHttpDownloader getMediaHttpDownloader() {
        return this.downloader;
    }
    
    protected final void initializeMediaDownload() {
        final HttpRequestFactory requestFactory = this.abstractGoogleClient.getRequestFactory();
        this.downloader = new MediaHttpDownloader(requestFactory.getTransport(), requestFactory.getInitializer());
    }
    
    public GenericUrl buildHttpRequestUrl() {
        return new GenericUrl(UriTemplate.expand(this.abstractGoogleClient.getBaseUrl(), this.uriTemplate, this, true));
    }
    
    public HttpRequest buildHttpRequest() throws IOException {
        return this.buildHttpRequest(false);
    }
    
    protected HttpRequest buildHttpRequestUsingHead() throws IOException {
        return this.buildHttpRequest(true);
    }
    
    private HttpRequest buildHttpRequest(final boolean usingHead) throws IOException {
        Preconditions.checkArgument(this.uploader == null);
        Preconditions.checkArgument(!usingHead || this.requestMethod.equals("GET"));
        final String requestMethodToUse = usingHead ? "HEAD" : this.requestMethod;
        final HttpRequest httpRequest = this.getAbstractGoogleClient().getRequestFactory().buildRequest(requestMethodToUse, this.buildHttpRequestUrl(), this.httpContent);
        new MethodOverride().intercept(httpRequest);
        httpRequest.setParser(this.getAbstractGoogleClient().getObjectParser());
        if (this.httpContent == null && (this.requestMethod.equals("POST") || this.requestMethod.equals("PUT") || this.requestMethod.equals("PATCH"))) {
            httpRequest.setContent(new EmptyContent());
        }
        httpRequest.getHeaders().putAll(this.requestHeaders);
        if (!this.disableGZipContent) {
            httpRequest.setEncoding(new GZipEncoding());
        }
        final HttpResponseInterceptor responseInterceptor = httpRequest.getResponseInterceptor();
        httpRequest.setResponseInterceptor(new HttpResponseInterceptor() {
            final /* synthetic */ HttpResponseInterceptor val$responseInterceptor;
            final /* synthetic */ HttpRequest val$httpRequest;
            final /* synthetic */ AbstractGoogleClientRequest this$0;
            
            AbstractGoogleClientRequest$1() {
                this.this$0 = this$0;
                super();
            }
            
            public void interceptResponse(final HttpResponse response) throws IOException {
                if (responseInterceptor != null) {
                    responseInterceptor.interceptResponse(response);
                }
                if (!response.isSuccessStatusCode() && httpRequest.getThrowExceptionOnExecuteError()) {
                    throw this.this$0.newExceptionOnError(response);
                }
            }
        });
        return httpRequest;
    }
    
    public HttpResponse executeUnparsed() throws IOException {
        return this.executeUnparsed(false);
    }
    
    protected HttpResponse executeMedia() throws IOException {
        this.set("alt", "media");
        return this.executeUnparsed();
    }
    
    protected HttpResponse executeUsingHead() throws IOException {
        Preconditions.checkArgument(this.uploader == null);
        final HttpResponse response = this.executeUnparsed(true);
        response.ignore();
        return response;
    }
    
    private HttpResponse executeUnparsed(final boolean usingHead) throws IOException {
        HttpResponse response;
        if (this.uploader == null) {
            response = this.buildHttpRequest(usingHead).execute();
        }
        else {
            final GenericUrl httpRequestUrl = this.buildHttpRequestUrl();
            final HttpRequest httpRequest = this.getAbstractGoogleClient().getRequestFactory().buildRequest(this.requestMethod, httpRequestUrl, this.httpContent);
            final boolean throwExceptionOnExecuteError = httpRequest.getThrowExceptionOnExecuteError();
            response = this.uploader.setInitiationHeaders(this.requestHeaders).setDisableGZipContent(this.disableGZipContent).upload(httpRequestUrl);
            response.getRequest().setParser(this.getAbstractGoogleClient().getObjectParser());
            if (throwExceptionOnExecuteError && !response.isSuccessStatusCode()) {
                throw this.newExceptionOnError(response);
            }
        }
        this.lastResponseHeaders = response.getHeaders();
        this.lastStatusCode = response.getStatusCode();
        this.lastStatusMessage = response.getStatusMessage();
        return response;
    }
    
    protected IOException newExceptionOnError(final HttpResponse response) {
        return new HttpResponseException(response);
    }
    
    public T execute() throws IOException {
        return this.executeUnparsed().parseAs(this.responseClass);
    }
    
    public InputStream executeAsInputStream() throws IOException {
        return this.executeUnparsed().getContent();
    }
    
    protected InputStream executeMediaAsInputStream() throws IOException {
        return this.executeMedia().getContent();
    }
    
    public void executeAndDownloadTo(final OutputStream outputStream) throws IOException {
        this.executeUnparsed().download(outputStream);
    }
    
    protected void executeMediaAndDownloadTo(final OutputStream outputStream) throws IOException {
        if (this.downloader == null) {
            this.executeMedia().download(outputStream);
        }
        else {
            this.downloader.download(this.buildHttpRequestUrl(), this.requestHeaders, outputStream);
        }
    }
    
    public final <E> void queue(final BatchRequest batchRequest, final Class<E> errorClass, final BatchCallback<T, E> callback) throws IOException {
        Preconditions.checkArgument(this.uploader == null, (Object)"Batching media requests is not supported");
        batchRequest.queue(this.buildHttpRequest(), this.getResponseClass(), errorClass, callback);
    }
    
    @Override
    public AbstractGoogleClientRequest<T> set(final String fieldName, final Object value) {
        return (AbstractGoogleClientRequest)super.set(fieldName, value);
    }
    
    protected final void checkRequiredParameter(final Object value, final String name) {
        Preconditions.checkArgument(this.abstractGoogleClient.getSuppressRequiredParameterChecks() || value != null, "Required parameter %s must be specified", name);
    }
    
    @Override
    public /* bridge */ GenericData set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
}
