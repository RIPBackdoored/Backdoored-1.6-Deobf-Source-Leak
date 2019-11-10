package com.google.cloud.storage;

import com.google.common.collect.*;

public static final class Builder
{
    private Integer maxAgeSeconds;
    private ImmutableList<HttpMethod> methods;
    private ImmutableList<Origin> origins;
    private ImmutableList<String> responseHeaders;
    
    private Builder() {
        super();
    }
    
    public Builder setMaxAgeSeconds(final Integer maxAgeSeconds) {
        this.maxAgeSeconds = maxAgeSeconds;
        return this;
    }
    
    public Builder setMethods(final Iterable<HttpMethod> methods) {
        this.methods = ((methods != null) ? ImmutableList.copyOf((Iterable<? extends HttpMethod>)methods) : null);
        return this;
    }
    
    public Builder setOrigins(final Iterable<Origin> origins) {
        this.origins = ((origins != null) ? ImmutableList.copyOf((Iterable<? extends Origin>)origins) : null);
        return this;
    }
    
    public Builder setResponseHeaders(final Iterable<String> headers) {
        this.responseHeaders = ((headers != null) ? ImmutableList.copyOf((Iterable<? extends String>)headers) : null);
        return this;
    }
    
    public Cors build() {
        return new Cors(this, null);
    }
    
    static /* synthetic */ Integer access$100(final Builder x0) {
        return x0.maxAgeSeconds;
    }
    
    static /* synthetic */ ImmutableList access$200(final Builder x0) {
        return x0.methods;
    }
    
    static /* synthetic */ ImmutableList access$300(final Builder x0) {
        return x0.origins;
    }
    
    static /* synthetic */ ImmutableList access$400(final Builder x0) {
        return x0.responseHeaders;
    }
    
    Builder(final Cors$1 x0) {
        this();
    }
}
