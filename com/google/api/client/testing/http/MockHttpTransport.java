package com.google.api.client.testing.http;

import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;
import java.util.*;

@Beta
public class MockHttpTransport extends HttpTransport
{
    private Set<String> supportedMethods;
    private MockLowLevelHttpRequest lowLevelHttpRequest;
    private MockLowLevelHttpResponse lowLevelHttpResponse;
    
    public MockHttpTransport() {
        super();
    }
    
    protected MockHttpTransport(final Builder builder) {
        super();
        this.supportedMethods = builder.supportedMethods;
        this.lowLevelHttpRequest = builder.lowLevelHttpRequest;
        this.lowLevelHttpResponse = builder.lowLevelHttpResponse;
    }
    
    @Override
    public boolean supportsMethod(final String method) throws IOException {
        return this.supportedMethods == null || this.supportedMethods.contains(method);
    }
    
    public LowLevelHttpRequest buildRequest(final String method, final String url) throws IOException {
        Preconditions.checkArgument(this.supportsMethod(method), "HTTP method %s not supported", method);
        if (this.lowLevelHttpRequest != null) {
            return this.lowLevelHttpRequest;
        }
        this.lowLevelHttpRequest = new MockLowLevelHttpRequest(url);
        if (this.lowLevelHttpResponse != null) {
            this.lowLevelHttpRequest.setResponse(this.lowLevelHttpResponse);
        }
        return this.lowLevelHttpRequest;
    }
    
    public final Set<String> getSupportedMethods() {
        return (this.supportedMethods == null) ? null : Collections.unmodifiableSet((Set<? extends String>)this.supportedMethods);
    }
    
    public final MockLowLevelHttpRequest getLowLevelHttpRequest() {
        return this.lowLevelHttpRequest;
    }
    
    @Deprecated
    public static Builder builder() {
        return new Builder();
    }
    
    @Beta
    public static class Builder
    {
        Set<String> supportedMethods;
        MockLowLevelHttpRequest lowLevelHttpRequest;
        MockLowLevelHttpResponse lowLevelHttpResponse;
        
        public Builder() {
            super();
        }
        
        public MockHttpTransport build() {
            return new MockHttpTransport(this);
        }
        
        public final Set<String> getSupportedMethods() {
            return this.supportedMethods;
        }
        
        public final Builder setSupportedMethods(final Set<String> supportedMethods) {
            this.supportedMethods = supportedMethods;
            return this;
        }
        
        public final Builder setLowLevelHttpRequest(final MockLowLevelHttpRequest lowLevelHttpRequest) {
            Preconditions.checkState(this.lowLevelHttpResponse == null, (Object)"Cannnot set a low level HTTP request when a low level HTTP response has been set.");
            this.lowLevelHttpRequest = lowLevelHttpRequest;
            return this;
        }
        
        public final MockLowLevelHttpRequest getLowLevelHttpRequest() {
            return this.lowLevelHttpRequest;
        }
        
        public final Builder setLowLevelHttpResponse(final MockLowLevelHttpResponse lowLevelHttpResponse) {
            Preconditions.checkState(this.lowLevelHttpRequest == null, (Object)"Cannot set a low level HTTP response when a low level HTTP request has been set.");
            this.lowLevelHttpResponse = lowLevelHttpResponse;
            return this;
        }
        
        MockLowLevelHttpResponse getLowLevelHttpResponse() {
            return this.lowLevelHttpResponse;
        }
    }
}
