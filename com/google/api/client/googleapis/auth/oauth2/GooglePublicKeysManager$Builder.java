package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.http.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;

@Beta
public static class Builder
{
    Clock clock;
    final HttpTransport transport;
    final JsonFactory jsonFactory;
    String publicCertsEncodedUrl;
    
    public Builder(final HttpTransport transport, final JsonFactory jsonFactory) {
        super();
        this.clock = Clock.SYSTEM;
        this.publicCertsEncodedUrl = "https://www.googleapis.com/oauth2/v1/certs";
        this.transport = Preconditions.checkNotNull(transport);
        this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
    }
    
    public GooglePublicKeysManager build() {
        return new GooglePublicKeysManager(this);
    }
    
    public final HttpTransport getTransport() {
        return this.transport;
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public final String getPublicCertsEncodedUrl() {
        return this.publicCertsEncodedUrl;
    }
    
    public Builder setPublicCertsEncodedUrl(final String publicCertsEncodedUrl) {
        this.publicCertsEncodedUrl = Preconditions.checkNotNull(publicCertsEncodedUrl);
        return this;
    }
    
    public final Clock getClock() {
        return this.clock;
    }
    
    public Builder setClock(final Clock clock) {
        this.clock = Preconditions.checkNotNull(clock);
        return this;
    }
}
