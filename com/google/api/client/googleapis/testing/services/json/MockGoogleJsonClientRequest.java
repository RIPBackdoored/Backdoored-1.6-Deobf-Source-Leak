package com.google.api.client.googleapis.testing.services.json;

import com.google.api.client.util.*;
import com.google.api.client.googleapis.services.json.*;
import com.google.api.client.http.*;
import com.google.api.client.googleapis.services.*;

@Beta
public class MockGoogleJsonClientRequest<T> extends AbstractGoogleJsonClientRequest<T>
{
    public MockGoogleJsonClientRequest(final AbstractGoogleJsonClient client, final String method, final String uriTemplate, final Object content, final Class<T> responseClass) {
        super(client, method, uriTemplate, content, responseClass);
    }
    
    @Override
    public MockGoogleJsonClient getAbstractGoogleClient() {
        return (MockGoogleJsonClient)super.getAbstractGoogleClient();
    }
    
    @Override
    public MockGoogleJsonClientRequest<T> setDisableGZipContent(final boolean disableGZipContent) {
        return (MockGoogleJsonClientRequest)super.setDisableGZipContent(disableGZipContent);
    }
    
    @Override
    public MockGoogleJsonClientRequest<T> setRequestHeaders(final HttpHeaders headers) {
        return (MockGoogleJsonClientRequest)super.setRequestHeaders(headers);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClientRequest setRequestHeaders(final HttpHeaders requestHeaders) {
        return this.setRequestHeaders(requestHeaders);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClientRequest setDisableGZipContent(final boolean disableGZipContent) {
        return this.setDisableGZipContent(disableGZipContent);
    }
    
    @Override
    public /* bridge */ AbstractGoogleJsonClient getAbstractGoogleClient() {
        return this.getAbstractGoogleClient();
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
}
