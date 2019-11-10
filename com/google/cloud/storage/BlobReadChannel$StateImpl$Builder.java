package com.google.cloud.storage;

import java.util.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;

static class Builder
{
    private final StorageOptions serviceOptions;
    private final BlobId blob;
    private final Map<StorageRpc.Option, ?> requestOptions;
    private String lastEtag;
    private long position;
    private boolean isOpen;
    private boolean endOfStream;
    private int chunkSize;
    
    private Builder(final StorageOptions options, final BlobId blob, final Map<StorageRpc.Option, ?> reqOptions) {
        super();
        this.serviceOptions = options;
        this.blob = blob;
        this.requestOptions = reqOptions;
    }
    
    Builder setLastEtag(final String lastEtag) {
        this.lastEtag = lastEtag;
        return this;
    }
    
    Builder setPosition(final long position) {
        this.position = position;
        return this;
    }
    
    Builder setIsOpen(final boolean isOpen) {
        this.isOpen = isOpen;
        return this;
    }
    
    Builder setEndOfStream(final boolean endOfStream) {
        this.endOfStream = endOfStream;
        return this;
    }
    
    Builder setChunkSize(final int chunkSize) {
        this.chunkSize = chunkSize;
        return this;
    }
    
    RestorableState<ReadChannel> build() {
        return (RestorableState<ReadChannel>)new StateImpl(this);
    }
    
    static /* synthetic */ StorageOptions access$400(final Builder x0) {
        return x0.serviceOptions;
    }
    
    static /* synthetic */ BlobId access$500(final Builder x0) {
        return x0.blob;
    }
    
    static /* synthetic */ Map access$600(final Builder x0) {
        return x0.requestOptions;
    }
    
    static /* synthetic */ String access$700(final Builder x0) {
        return x0.lastEtag;
    }
    
    static /* synthetic */ long access$800(final Builder x0) {
        return x0.position;
    }
    
    static /* synthetic */ boolean access$900(final Builder x0) {
        return x0.isOpen;
    }
    
    static /* synthetic */ boolean access$1000(final Builder x0) {
        return x0.endOfStream;
    }
    
    static /* synthetic */ int access$1100(final Builder x0) {
        return x0.chunkSize;
    }
    
    Builder(final StorageOptions x0, final BlobId x1, final Map x2, final BlobReadChannel$1 x3) {
        this(x0, x1, x2);
    }
}
