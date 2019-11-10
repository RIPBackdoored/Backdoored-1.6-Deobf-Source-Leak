package com.google.api.client.googleapis.testing.services;

import com.google.api.client.googleapis.services.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

@Beta
public class MockGoogleClientRequest<T> extends AbstractGoogleClientRequest<T>
{
    public MockGoogleClientRequest(final AbstractGoogleClient client, final String method, final String uriTemplate, final HttpContent content, final Class<T> responseClass) {
        super(client, method, uriTemplate, content, responseClass);
    }
    
    @Override
    public MockGoogleClientRequest<T> setDisableGZipContent(final boolean disableGZipContent) {
        return (MockGoogleClientRequest)super.setDisableGZipContent(disableGZipContent);
    }
    
    @Override
    public MockGoogleClientRequest<T> setRequestHeaders(final HttpHeaders headers) {
        return (MockGoogleClientRequest)super.setRequestHeaders(headers);
    }
    
    @Override
    public MockGoogleClientRequest<T> set(final String fieldName, final Object value) {
        return (MockGoogleClientRequest)super.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ AbstractGoogleClientRequest set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ AbstractGoogleClientRequest setRequestHeaders(final HttpHeaders requestHeaders) {
        return this.setRequestHeaders(requestHeaders);
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
