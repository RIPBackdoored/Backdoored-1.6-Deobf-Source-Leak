package com.google.api.client.googleapis.batch;

import com.google.api.client.util.*;
import java.util.*;
import com.google.api.client.http.*;
import java.io.*;

public final class BatchRequest
{
    private GenericUrl batchUrl;
    private final HttpRequestFactory requestFactory;
    List<RequestInfo<?, ?>> requestInfos;
    private Sleeper sleeper;
    
    public BatchRequest(final HttpTransport transport, final HttpRequestInitializer httpRequestInitializer) {
        super();
        this.batchUrl = new GenericUrl("https://www.googleapis.com/batch");
        this.requestInfos = new ArrayList<RequestInfo<?, ?>>();
        this.sleeper = Sleeper.DEFAULT;
        this.requestFactory = ((httpRequestInitializer == null) ? transport.createRequestFactory() : transport.createRequestFactory(httpRequestInitializer));
    }
    
    public BatchRequest setBatchUrl(final GenericUrl batchUrl) {
        this.batchUrl = batchUrl;
        return this;
    }
    
    public GenericUrl getBatchUrl() {
        return this.batchUrl;
    }
    
    public Sleeper getSleeper() {
        return this.sleeper;
    }
    
    public BatchRequest setSleeper(final Sleeper sleeper) {
        this.sleeper = Preconditions.checkNotNull(sleeper);
        return this;
    }
    
    public <T, E> BatchRequest queue(final HttpRequest httpRequest, final Class<T> dataClass, final Class<E> errorClass, final BatchCallback<T, E> callback) throws IOException {
        Preconditions.checkNotNull(httpRequest);
        Preconditions.checkNotNull(callback);
        Preconditions.checkNotNull(dataClass);
        Preconditions.checkNotNull(errorClass);
        this.requestInfos.add(new RequestInfo<Object, Object>((BatchCallback<Object, Object>)callback, (Class<Object>)dataClass, (Class<Object>)errorClass, httpRequest));
        return this;
    }
    
    public int size() {
        return this.requestInfos.size();
    }
    
    public void execute() throws IOException {
        Preconditions.checkState(!this.requestInfos.isEmpty());
        final HttpRequest batchRequest = this.requestFactory.buildPostRequest(this.batchUrl, null);
        final HttpExecuteInterceptor originalInterceptor = batchRequest.getInterceptor();
        batchRequest.setInterceptor(new BatchInterceptor(originalInterceptor));
        int retriesRemaining = batchRequest.getNumberOfRetries();
        final BackOffPolicy backOffPolicy = batchRequest.getBackOffPolicy();
        if (backOffPolicy != null) {
            backOffPolicy.reset();
        }
        boolean retryAllowed;
        do {
            retryAllowed = (retriesRemaining > 0);
            final MultipartContent batchContent = new MultipartContent();
            batchContent.getMediaType().setSubType("mixed");
            int contentId = 1;
            for (final RequestInfo<?, ?> requestInfo : this.requestInfos) {
                batchContent.addPart(new MultipartContent.Part(new HttpHeaders().setAcceptEncoding(null).set("Content-ID", contentId++), new HttpRequestContent(requestInfo.request)));
            }
            batchRequest.setContent(batchContent);
            final HttpResponse response = batchRequest.execute();
            BatchUnparsedResponse batchResponse;
            try {
                final String s = "--";
                final String value = String.valueOf(response.getMediaType().getParameter("boundary"));
                final String boundary = (value.length() != 0) ? s.concat(value) : new String(s);
                final InputStream contentStream = response.getContent();
                batchResponse = new BatchUnparsedResponse(contentStream, boundary, this.requestInfos, retryAllowed);
                while (batchResponse.hasNext) {
                    batchResponse.parseNextResponse();
                }
            }
            finally {
                response.disconnect();
            }
            final List<RequestInfo<?, ?>> unsuccessfulRequestInfos = batchResponse.unsuccessfulRequestInfos;
            if (unsuccessfulRequestInfos.isEmpty()) {
                break;
            }
            this.requestInfos = unsuccessfulRequestInfos;
            if (batchResponse.backOffRequired && backOffPolicy != null) {
                final long backOffTime = backOffPolicy.getNextBackOffMillis();
                if (backOffTime != -1L) {
                    try {
                        this.sleeper.sleep(backOffTime);
                    }
                    catch (InterruptedException ex) {}
                }
            }
            --retriesRemaining;
        } while (retryAllowed);
        this.requestInfos.clear();
    }
    
    static class RequestInfo<T, E>
    {
        final BatchCallback<T, E> callback;
        final Class<T> dataClass;
        final Class<E> errorClass;
        final HttpRequest request;
        
        RequestInfo(final BatchCallback<T, E> callback, final Class<T> dataClass, final Class<E> errorClass, final HttpRequest request) {
            super();
            this.callback = callback;
            this.dataClass = dataClass;
            this.errorClass = errorClass;
            this.request = request;
        }
    }
    
    class BatchInterceptor implements HttpExecuteInterceptor
    {
        private HttpExecuteInterceptor originalInterceptor;
        final /* synthetic */ BatchRequest this$0;
        
        BatchInterceptor(final BatchRequest this$0, final HttpExecuteInterceptor originalInterceptor) {
            this.this$0 = this$0;
            super();
            this.originalInterceptor = originalInterceptor;
        }
        
        public void intercept(final HttpRequest batchRequest) throws IOException {
            if (this.originalInterceptor != null) {
                this.originalInterceptor.intercept(batchRequest);
            }
            for (final RequestInfo<?, ?> requestInfo : this.this$0.requestInfos) {
                final HttpExecuteInterceptor interceptor = requestInfo.request.getInterceptor();
                if (interceptor != null) {
                    interceptor.intercept(requestInfo.request);
                }
            }
        }
    }
}
