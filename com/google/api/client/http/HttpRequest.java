package com.google.api.client.http;

import java.util.logging.*;
import com.google.api.client.util.*;
import java.io.*;
import java.util.concurrent.*;

public final class HttpRequest
{
    public static final String VERSION = "1.25.0";
    public static final String USER_AGENT_SUFFIX = "Google-HTTP-Java-Client/1.25.0 (gzip)";
    public static final int DEFAULT_NUMBER_OF_RETRIES = 10;
    private HttpExecuteInterceptor executeInterceptor;
    private HttpHeaders headers;
    private HttpHeaders responseHeaders;
    private int numRetries;
    private int contentLoggingLimit;
    private boolean loggingEnabled;
    private boolean curlLoggingEnabled;
    private HttpContent content;
    private final HttpTransport transport;
    private String requestMethod;
    private GenericUrl url;
    private int connectTimeout;
    private int readTimeout;
    private HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler;
    @Beta
    private HttpIOExceptionHandler ioExceptionHandler;
    private HttpResponseInterceptor responseInterceptor;
    private ObjectParser objectParser;
    private HttpEncoding encoding;
    @Deprecated
    @Beta
    private BackOffPolicy backOffPolicy;
    private boolean followRedirects;
    private boolean throwExceptionOnExecuteError;
    @Deprecated
    @Beta
    private boolean retryOnExecuteIOException;
    private boolean suppressUserAgentSuffix;
    private Sleeper sleeper;
    
    HttpRequest(final HttpTransport transport, final String requestMethod) {
        super();
        this.headers = new HttpHeaders();
        this.responseHeaders = new HttpHeaders();
        this.numRetries = 10;
        this.contentLoggingLimit = 16384;
        this.loggingEnabled = true;
        this.curlLoggingEnabled = true;
        this.connectTimeout = 20000;
        this.readTimeout = 20000;
        this.followRedirects = true;
        this.throwExceptionOnExecuteError = true;
        this.retryOnExecuteIOException = false;
        this.sleeper = Sleeper.DEFAULT;
        this.transport = transport;
        this.setRequestMethod(requestMethod);
    }
    
    public HttpTransport getTransport() {
        return this.transport;
    }
    
    public String getRequestMethod() {
        return this.requestMethod;
    }
    
    public HttpRequest setRequestMethod(final String requestMethod) {
        Preconditions.checkArgument(requestMethod == null || HttpMediaType.matchesToken(requestMethod));
        this.requestMethod = requestMethod;
        return this;
    }
    
    public GenericUrl getUrl() {
        return this.url;
    }
    
    public HttpRequest setUrl(final GenericUrl url) {
        this.url = Preconditions.checkNotNull(url);
        return this;
    }
    
    public HttpContent getContent() {
        return this.content;
    }
    
    public HttpRequest setContent(final HttpContent content) {
        this.content = content;
        return this;
    }
    
    public HttpEncoding getEncoding() {
        return this.encoding;
    }
    
    public HttpRequest setEncoding(final HttpEncoding encoding) {
        this.encoding = encoding;
        return this;
    }
    
    @Deprecated
    @Beta
    public BackOffPolicy getBackOffPolicy() {
        return this.backOffPolicy;
    }
    
    @Deprecated
    @Beta
    public HttpRequest setBackOffPolicy(final BackOffPolicy backOffPolicy) {
        this.backOffPolicy = backOffPolicy;
        return this;
    }
    
    public int getContentLoggingLimit() {
        return this.contentLoggingLimit;
    }
    
    public HttpRequest setContentLoggingLimit(final int contentLoggingLimit) {
        Preconditions.checkArgument(contentLoggingLimit >= 0, (Object)"The content logging limit must be non-negative.");
        this.contentLoggingLimit = contentLoggingLimit;
        return this;
    }
    
    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }
    
    public HttpRequest setLoggingEnabled(final boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
        return this;
    }
    
    public boolean isCurlLoggingEnabled() {
        return this.curlLoggingEnabled;
    }
    
    public HttpRequest setCurlLoggingEnabled(final boolean curlLoggingEnabled) {
        this.curlLoggingEnabled = curlLoggingEnabled;
        return this;
    }
    
    public int getConnectTimeout() {
        return this.connectTimeout;
    }
    
    public HttpRequest setConnectTimeout(final int connectTimeout) {
        Preconditions.checkArgument(connectTimeout >= 0);
        this.connectTimeout = connectTimeout;
        return this;
    }
    
    public int getReadTimeout() {
        return this.readTimeout;
    }
    
    public HttpRequest setReadTimeout(final int readTimeout) {
        Preconditions.checkArgument(readTimeout >= 0);
        this.readTimeout = readTimeout;
        return this;
    }
    
    public HttpHeaders getHeaders() {
        return this.headers;
    }
    
    public HttpRequest setHeaders(final HttpHeaders headers) {
        this.headers = Preconditions.checkNotNull(headers);
        return this;
    }
    
    public HttpHeaders getResponseHeaders() {
        return this.responseHeaders;
    }
    
    public HttpRequest setResponseHeaders(final HttpHeaders responseHeaders) {
        this.responseHeaders = Preconditions.checkNotNull(responseHeaders);
        return this;
    }
    
    public HttpExecuteInterceptor getInterceptor() {
        return this.executeInterceptor;
    }
    
    public HttpRequest setInterceptor(final HttpExecuteInterceptor interceptor) {
        this.executeInterceptor = interceptor;
        return this;
    }
    
    public HttpUnsuccessfulResponseHandler getUnsuccessfulResponseHandler() {
        return this.unsuccessfulResponseHandler;
    }
    
    public HttpRequest setUnsuccessfulResponseHandler(final HttpUnsuccessfulResponseHandler unsuccessfulResponseHandler) {
        this.unsuccessfulResponseHandler = unsuccessfulResponseHandler;
        return this;
    }
    
    @Beta
    public HttpIOExceptionHandler getIOExceptionHandler() {
        return this.ioExceptionHandler;
    }
    
    @Beta
    public HttpRequest setIOExceptionHandler(final HttpIOExceptionHandler ioExceptionHandler) {
        this.ioExceptionHandler = ioExceptionHandler;
        return this;
    }
    
    public HttpResponseInterceptor getResponseInterceptor() {
        return this.responseInterceptor;
    }
    
    public HttpRequest setResponseInterceptor(final HttpResponseInterceptor responseInterceptor) {
        this.responseInterceptor = responseInterceptor;
        return this;
    }
    
    public int getNumberOfRetries() {
        return this.numRetries;
    }
    
    public HttpRequest setNumberOfRetries(final int numRetries) {
        Preconditions.checkArgument(numRetries >= 0);
        this.numRetries = numRetries;
        return this;
    }
    
    public HttpRequest setParser(final ObjectParser parser) {
        this.objectParser = parser;
        return this;
    }
    
    public final ObjectParser getParser() {
        return this.objectParser;
    }
    
    public boolean getFollowRedirects() {
        return this.followRedirects;
    }
    
    public HttpRequest setFollowRedirects(final boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }
    
    public boolean getThrowExceptionOnExecuteError() {
        return this.throwExceptionOnExecuteError;
    }
    
    public HttpRequest setThrowExceptionOnExecuteError(final boolean throwExceptionOnExecuteError) {
        this.throwExceptionOnExecuteError = throwExceptionOnExecuteError;
        return this;
    }
    
    @Deprecated
    @Beta
    public boolean getRetryOnExecuteIOException() {
        return this.retryOnExecuteIOException;
    }
    
    @Deprecated
    @Beta
    public HttpRequest setRetryOnExecuteIOException(final boolean retryOnExecuteIOException) {
        this.retryOnExecuteIOException = retryOnExecuteIOException;
        return this;
    }
    
    public boolean getSuppressUserAgentSuffix() {
        return this.suppressUserAgentSuffix;
    }
    
    public HttpRequest setSuppressUserAgentSuffix(final boolean suppressUserAgentSuffix) {
        this.suppressUserAgentSuffix = suppressUserAgentSuffix;
        return this;
    }
    
    public HttpResponse execute() throws IOException {
        boolean retryRequest = false;
        Preconditions.checkArgument(this.numRetries >= 0);
        int retriesRemaining = this.numRetries;
        if (this.backOffPolicy != null) {
            this.backOffPolicy.reset();
        }
        HttpResponse response = null;
        Preconditions.checkNotNull(this.requestMethod);
        Preconditions.checkNotNull(this.url);
        IOException executeException;
        do {
            if (response != null) {
                response.ignore();
            }
            response = null;
            executeException = null;
            if (this.executeInterceptor != null) {
                this.executeInterceptor.intercept(this);
            }
            final String urlString = this.url.build();
            final LowLevelHttpRequest lowLevelHttpRequest = this.transport.buildRequest(this.requestMethod, urlString);
            final Logger logger = HttpTransport.LOGGER;
            final boolean loggable = this.loggingEnabled && logger.isLoggable(Level.CONFIG);
            StringBuilder logbuf = null;
            StringBuilder curlbuf = null;
            if (loggable) {
                logbuf = new StringBuilder();
                logbuf.append("-------------- REQUEST  --------------").append(StringUtils.LINE_SEPARATOR);
                logbuf.append(this.requestMethod).append(' ').append(urlString).append(StringUtils.LINE_SEPARATOR);
                if (this.curlLoggingEnabled) {
                    curlbuf = new StringBuilder("curl -v --compressed");
                    if (!this.requestMethod.equals("GET")) {
                        curlbuf.append(" -X ").append(this.requestMethod);
                    }
                }
            }
            final String originalUserAgent = this.headers.getUserAgent();
            if (!this.suppressUserAgentSuffix) {
                if (originalUserAgent == null) {
                    this.headers.setUserAgent("Google-HTTP-Java-Client/1.25.0 (gzip)");
                }
                else {
                    this.headers.setUserAgent(originalUserAgent + " " + "Google-HTTP-Java-Client/1.25.0 (gzip)");
                }
            }
            HttpHeaders.serializeHeaders(this.headers, logbuf, curlbuf, logger, lowLevelHttpRequest);
            if (!this.suppressUserAgentSuffix) {
                this.headers.setUserAgent(originalUserAgent);
            }
            StreamingContent streamingContent = this.content;
            final boolean contentRetrySupported = streamingContent == null || this.content.retrySupported();
            if (streamingContent != null) {
                final String contentType = this.content.getType();
                if (loggable) {
                    streamingContent = new LoggingStreamingContent(streamingContent, HttpTransport.LOGGER, Level.CONFIG, this.contentLoggingLimit);
                }
                String contentEncoding;
                long contentLength;
                if (this.encoding == null) {
                    contentEncoding = null;
                    contentLength = this.content.getLength();
                }
                else {
                    contentEncoding = this.encoding.getName();
                    streamingContent = new HttpEncodingStreamingContent(streamingContent, this.encoding);
                    contentLength = (contentRetrySupported ? IOUtils.computeLength(streamingContent) : -1L);
                }
                if (loggable) {
                    if (contentType != null) {
                        final String header = "Content-Type: " + contentType;
                        logbuf.append(header).append(StringUtils.LINE_SEPARATOR);
                        if (curlbuf != null) {
                            curlbuf.append(" -H '" + header + "'");
                        }
                    }
                    if (contentEncoding != null) {
                        final String header = "Content-Encoding: " + contentEncoding;
                        logbuf.append(header).append(StringUtils.LINE_SEPARATOR);
                        if (curlbuf != null) {
                            curlbuf.append(" -H '" + header + "'");
                        }
                    }
                    if (contentLength >= 0L) {
                        final String header = "Content-Length: " + contentLength;
                        logbuf.append(header).append(StringUtils.LINE_SEPARATOR);
                    }
                }
                if (curlbuf != null) {
                    curlbuf.append(" -d '@-'");
                }
                lowLevelHttpRequest.setContentType(contentType);
                lowLevelHttpRequest.setContentEncoding(contentEncoding);
                lowLevelHttpRequest.setContentLength(contentLength);
                lowLevelHttpRequest.setStreamingContent(streamingContent);
            }
            if (loggable) {
                logger.config(logbuf.toString());
                if (curlbuf != null) {
                    curlbuf.append(" -- '");
                    curlbuf.append(urlString.replaceAll("'", "'\"'\"'"));
                    curlbuf.append("'");
                    if (streamingContent != null) {
                        curlbuf.append(" << $$$");
                    }
                    logger.config(curlbuf.toString());
                }
            }
            retryRequest = (contentRetrySupported && retriesRemaining > 0);
            lowLevelHttpRequest.setTimeout(this.connectTimeout, this.readTimeout);
            try {
                final LowLevelHttpResponse lowLevelHttpResponse = lowLevelHttpRequest.execute();
                boolean responseConstructed = false;
                try {
                    response = new HttpResponse(this, lowLevelHttpResponse);
                    responseConstructed = true;
                }
                finally {
                    if (!responseConstructed) {
                        final InputStream lowLevelContent = lowLevelHttpResponse.getContent();
                        if (lowLevelContent != null) {
                            lowLevelContent.close();
                        }
                    }
                }
            }
            catch (IOException e) {
                if (!this.retryOnExecuteIOException && (this.ioExceptionHandler == null || !this.ioExceptionHandler.handleIOException(this, retryRequest))) {
                    throw e;
                }
                executeException = e;
                if (loggable) {
                    logger.log(Level.WARNING, "exception thrown while executing request", e);
                }
            }
            boolean responseProcessed = false;
            try {
                if (response != null && !response.isSuccessStatusCode()) {
                    boolean errorHandled = false;
                    if (this.unsuccessfulResponseHandler != null) {
                        errorHandled = this.unsuccessfulResponseHandler.handleResponse(this, response, retryRequest);
                    }
                    if (!errorHandled) {
                        if (this.handleRedirect(response.getStatusCode(), response.getHeaders())) {
                            errorHandled = true;
                        }
                        else if (retryRequest && this.backOffPolicy != null && this.backOffPolicy.isBackOffRequired(response.getStatusCode())) {
                            final long backOffTime = this.backOffPolicy.getNextBackOffMillis();
                            if (backOffTime != -1L) {
                                try {
                                    this.sleeper.sleep(backOffTime);
                                }
                                catch (InterruptedException ex) {}
                                errorHandled = true;
                            }
                        }
                    }
                    retryRequest &= errorHandled;
                    if (retryRequest) {
                        response.ignore();
                    }
                }
                else {
                    retryRequest &= (response == null);
                }
                --retriesRemaining;
                responseProcessed = true;
            }
            finally {
                if (response != null && !responseProcessed) {
                    response.disconnect();
                }
            }
        } while (retryRequest);
        if (response == null) {
            throw executeException;
        }
        if (this.responseInterceptor != null) {
            this.responseInterceptor.interceptResponse(response);
        }
        if (this.throwExceptionOnExecuteError && !response.isSuccessStatusCode()) {
            try {
                throw new HttpResponseException(response);
            }
            finally {
                response.disconnect();
            }
        }
        return response;
    }
    
    @Beta
    public Future<HttpResponse> executeAsync(final Executor executor) {
        final FutureTask<HttpResponse> future = new FutureTask<HttpResponse>(new Callable<HttpResponse>() {
            final /* synthetic */ HttpRequest this$0;
            
            HttpRequest$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public HttpResponse call() throws Exception {
                return this.this$0.execute();
            }
            
            @Override
            public /* bridge */ Object call() throws Exception {
                return this.call();
            }
        });
        executor.execute(future);
        return future;
    }
    
    @Beta
    public Future<HttpResponse> executeAsync() {
        return this.executeAsync(Executors.newSingleThreadExecutor());
    }
    
    public boolean handleRedirect(final int statusCode, final HttpHeaders responseHeaders) {
        final String redirectLocation = responseHeaders.getLocation();
        if (this.getFollowRedirects() && HttpStatusCodes.isRedirect(statusCode) && redirectLocation != null) {
            this.setUrl(new GenericUrl(this.url.toURL(redirectLocation)));
            if (statusCode == 303) {
                this.setRequestMethod("GET");
                this.setContent(null);
            }
            this.headers.setAuthorization((String)null);
            this.headers.setIfMatch(null);
            this.headers.setIfNoneMatch(null);
            this.headers.setIfModifiedSince(null);
            this.headers.setIfUnmodifiedSince(null);
            this.headers.setIfRange(null);
            return true;
        }
        return false;
    }
    
    public Sleeper getSleeper() {
        return this.sleeper;
    }
    
    public HttpRequest setSleeper(final Sleeper sleeper) {
        this.sleeper = Preconditions.checkNotNull(sleeper);
        return this;
    }
}
