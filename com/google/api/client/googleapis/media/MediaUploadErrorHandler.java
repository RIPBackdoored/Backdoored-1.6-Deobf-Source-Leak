package com.google.api.client.googleapis.media;

import com.google.api.client.util.*;
import java.util.logging.*;
import java.io.*;
import com.google.api.client.http.*;

@Beta
class MediaUploadErrorHandler implements HttpUnsuccessfulResponseHandler, HttpIOExceptionHandler
{
    static final Logger LOGGER;
    private final MediaHttpUploader uploader;
    private final HttpIOExceptionHandler originalIOExceptionHandler;
    private final HttpUnsuccessfulResponseHandler originalUnsuccessfulHandler;
    
    public MediaUploadErrorHandler(final MediaHttpUploader uploader, final HttpRequest request) {
        super();
        this.uploader = Preconditions.checkNotNull(uploader);
        this.originalIOExceptionHandler = request.getIOExceptionHandler();
        this.originalUnsuccessfulHandler = request.getUnsuccessfulResponseHandler();
        request.setIOExceptionHandler(this);
        request.setUnsuccessfulResponseHandler(this);
    }
    
    public boolean handleIOException(final HttpRequest request, final boolean supportsRetry) throws IOException {
        final boolean handled = this.originalIOExceptionHandler != null && this.originalIOExceptionHandler.handleIOException(request, supportsRetry);
        if (handled) {
            try {
                this.uploader.serverErrorCallback();
            }
            catch (IOException e) {
                MediaUploadErrorHandler.LOGGER.log(Level.WARNING, "exception thrown while calling server callback", e);
            }
        }
        return handled;
    }
    
    public boolean handleResponse(final HttpRequest request, final HttpResponse response, final boolean supportsRetry) throws IOException {
        final boolean handled = this.originalUnsuccessfulHandler != null && this.originalUnsuccessfulHandler.handleResponse(request, response, supportsRetry);
        if (handled && supportsRetry && response.getStatusCode() / 100 == 5) {
            try {
                this.uploader.serverErrorCallback();
            }
            catch (IOException e) {
                MediaUploadErrorHandler.LOGGER.log(Level.WARNING, "exception thrown while calling server callback", e);
            }
        }
        return handled;
    }
    
    static {
        LOGGER = Logger.getLogger(MediaUploadErrorHandler.class.getName());
    }
}
