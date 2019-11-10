package com.google.cloud.storage;

import com.google.cloud.*;
import com.google.cloud.http.*;

public static class Builder extends ServiceOptions.Builder<Storage, StorageOptions, Builder>
{
    private Builder() {
        super();
    }
    
    private Builder(final StorageOptions options) {
        super((ServiceOptions)options);
    }
    
    public Builder setTransportOptions(final TransportOptions transportOptions) {
        if (!(transportOptions instanceof HttpTransportOptions)) {
            throw new IllegalArgumentException("Only http transport is allowed for Storage.");
        }
        return (Builder)super.setTransportOptions(transportOptions);
    }
    
    public StorageOptions build() {
        return new StorageOptions(this, null);
    }
    
    public /* bridge */ ServiceOptions.Builder setTransportOptions(final TransportOptions transportOptions) {
        return this.setTransportOptions(transportOptions);
    }
    
    public /* bridge */ ServiceOptions build() {
        return this.build();
    }
    
    Builder(final StorageOptions x0, final StorageOptions$1 x1) {
        this(x0);
    }
    
    Builder(final StorageOptions$1 x0) {
        this();
    }
}
