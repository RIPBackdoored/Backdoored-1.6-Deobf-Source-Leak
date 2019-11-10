package com.google.api.client.googleapis.services.json;

import com.google.api.client.http.json.*;
import com.google.api.client.googleapis.services.*;
import com.google.api.client.googleapis.batch.json.*;
import com.google.api.client.googleapis.batch.*;
import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.googleapis.json.*;
import com.google.api.client.util.*;

public abstract class AbstractGoogleJsonClientRequest<T> extends AbstractGoogleClientRequest<T>
{
    private final Object jsonContent;
    
    protected AbstractGoogleJsonClientRequest(final AbstractGoogleJsonClient abstractGoogleJsonClient, final String requestMethod, final String uriTemplate, final Object jsonContent, final Class<T> responseClass) {
        super(abstractGoogleJsonClient, requestMethod, uriTemplate, (jsonContent == null) ? null : new JsonHttpContent(abstractGoogleJsonClient.getJsonFactory(), jsonContent).setWrapperKey(abstractGoogleJsonClient.getObjectParser().getWrapperKeys().isEmpty() ? null : "data"), responseClass);
        this.jsonContent = jsonContent;
    }
    
    @Override
    public AbstractGoogleJsonClient getAbstractGoogleClient() {
        return (AbstractGoogleJsonClient)super.getAbstractGoogleClient();
    }
    
    @Override
    public AbstractGoogleJsonClientRequest<T> setDisableGZipContent(final boolean disableGZipContent) {
        return (AbstractGoogleJsonClientRequest)super.setDisableGZipContent(disableGZipContent);
    }
    
    @Override
    public AbstractGoogleJsonClientRequest<T> setRequestHeaders(final HttpHeaders headers) {
        return (AbstractGoogleJsonClientRequest)super.setRequestHeaders(headers);
    }
    
    public final void queue(final BatchRequest batchRequest, final JsonBatchCallback<T> callback) throws IOException {
        super.queue(batchRequest, GoogleJsonErrorContainer.class, callback);
    }
    
    @Override
    protected GoogleJsonResponseException newExceptionOnError(final HttpResponse response) {
        return GoogleJsonResponseException.from(this.getAbstractGoogleClient().getJsonFactory(), response);
    }
    
    public Object getJsonContent() {
        return this.jsonContent;
    }
    
    @Override
    public AbstractGoogleJsonClientRequest<T> set(final String fieldName, final Object value) {
        return (AbstractGoogleJsonClientRequest)super.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ AbstractGoogleClientRequest set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    @Override
    protected /* bridge */ IOException newExceptionOnError(final HttpResponse response) {
        return this.newExceptionOnError(response);
    }
    
    @Override
    public /* bridge */ AbstractGoogleClientRequest setRequestHeaders(final HttpHeaders requestHeaders) {
        return this.setRequestHeaders(requestHeaders);
    }
    
    @Override
    public /* bridge */ AbstractGoogleClient getAbstractGoogleClient() {
        return this.getAbstractGoogleClient();
    }
    
    @Override
    public /* bridge */ AbstractGoogleClientRequest setDisableGZipContent(final boolean disableGZipContent) {
        return this.setDisableGZipContent(disableGZipContent);
    }
    
    @Override
    public /* bridge */ GenericData set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
}
