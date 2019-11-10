package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.http.*;
import java.util.*;
import com.google.api.client.util.*;

public static class Builder
{
    final AccessMethod method;
    HttpTransport transport;
    JsonFactory jsonFactory;
    GenericUrl tokenServerUrl;
    Clock clock;
    HttpExecuteInterceptor clientAuthentication;
    HttpRequestInitializer requestInitializer;
    Collection<CredentialRefreshListener> refreshListeners;
    
    public Builder(final AccessMethod method) {
        super();
        this.clock = Clock.SYSTEM;
        this.refreshListeners = (Collection<CredentialRefreshListener>)Lists.newArrayList();
        this.method = Preconditions.checkNotNull(method);
    }
    
    public Credential build() {
        return new Credential(this);
    }
    
    public final AccessMethod getMethod() {
        return this.method;
    }
    
    public final HttpTransport getTransport() {
        return this.transport;
    }
    
    public Builder setTransport(final HttpTransport transport) {
        this.transport = transport;
        return this;
    }
    
    public final Clock getClock() {
        return this.clock;
    }
    
    public Builder setClock(final Clock clock) {
        this.clock = Preconditions.checkNotNull(clock);
        return this;
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public Builder setJsonFactory(final JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
        return this;
    }
    
    public final GenericUrl getTokenServerUrl() {
        return this.tokenServerUrl;
    }
    
    public Builder setTokenServerUrl(final GenericUrl tokenServerUrl) {
        this.tokenServerUrl = tokenServerUrl;
        return this;
    }
    
    public Builder setTokenServerEncodedUrl(final String tokenServerEncodedUrl) {
        this.tokenServerUrl = ((tokenServerEncodedUrl == null) ? null : new GenericUrl(tokenServerEncodedUrl));
        return this;
    }
    
    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }
    
    public Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        this.clientAuthentication = clientAuthentication;
        return this;
    }
    
    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }
    
    public Builder setRequestInitializer(final HttpRequestInitializer requestInitializer) {
        this.requestInitializer = requestInitializer;
        return this;
    }
    
    public Builder addRefreshListener(final CredentialRefreshListener refreshListener) {
        this.refreshListeners.add(Preconditions.checkNotNull(refreshListener));
        return this;
    }
    
    public final Collection<CredentialRefreshListener> getRefreshListeners() {
        return this.refreshListeners;
    }
    
    public Builder setRefreshListeners(final Collection<CredentialRefreshListener> refreshListeners) {
        this.refreshListeners = Preconditions.checkNotNull(refreshListeners);
        return this;
    }
}
