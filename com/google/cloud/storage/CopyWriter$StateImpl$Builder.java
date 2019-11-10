package com.google.cloud.storage;

import java.util.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;

static class Builder
{
    private final StorageOptions serviceOptions;
    private final BlobId source;
    private final Map<StorageRpc.Option, ?> sourceOptions;
    private final boolean overrideInfo;
    private final BlobInfo target;
    private final Map<StorageRpc.Option, ?> targetOptions;
    private BlobInfo result;
    private long blobSize;
    private boolean isDone;
    private String rewriteToken;
    private long totalBytesCopied;
    private Long megabytesCopiedPerChunk;
    
    private Builder(final StorageOptions options, final BlobId source, final Map<StorageRpc.Option, ?> sourceOptions, final boolean overrideInfo, final BlobInfo target, final Map<StorageRpc.Option, ?> targetOptions) {
        super();
        this.serviceOptions = options;
        this.source = source;
        this.sourceOptions = sourceOptions;
        this.overrideInfo = overrideInfo;
        this.target = target;
        this.targetOptions = targetOptions;
    }
    
    Builder setResult(final BlobInfo result) {
        this.result = result;
        return this;
    }
    
    Builder setBlobSize(final long blobSize) {
        this.blobSize = blobSize;
        return this;
    }
    
    Builder setIsDone(final boolean isDone) {
        this.isDone = isDone;
        return this;
    }
    
    Builder setRewriteToken(final String rewriteToken) {
        this.rewriteToken = rewriteToken;
        return this;
    }
    
    Builder setTotalBytesRewritten(final long totalBytesRewritten) {
        this.totalBytesCopied = totalBytesRewritten;
        return this;
    }
    
    Builder setMegabytesCopiedPerChunk(final Long megabytesCopiedPerChunk) {
        this.megabytesCopiedPerChunk = megabytesCopiedPerChunk;
        return this;
    }
    
    RestorableState<CopyWriter> build() {
        return (RestorableState<CopyWriter>)new StateImpl(this);
    }
    
    static /* synthetic */ StorageOptions access$200(final Builder x0) {
        return x0.serviceOptions;
    }
    
    static /* synthetic */ BlobId access$300(final Builder x0) {
        return x0.source;
    }
    
    static /* synthetic */ Map access$400(final Builder x0) {
        return x0.sourceOptions;
    }
    
    static /* synthetic */ boolean access$500(final Builder x0) {
        return x0.overrideInfo;
    }
    
    static /* synthetic */ BlobInfo access$600(final Builder x0) {
        return x0.target;
    }
    
    static /* synthetic */ Map access$700(final Builder x0) {
        return x0.targetOptions;
    }
    
    static /* synthetic */ BlobInfo access$800(final Builder x0) {
        return x0.result;
    }
    
    static /* synthetic */ long access$900(final Builder x0) {
        return x0.blobSize;
    }
    
    static /* synthetic */ boolean access$1000(final Builder x0) {
        return x0.isDone;
    }
    
    static /* synthetic */ String access$1100(final Builder x0) {
        return x0.rewriteToken;
    }
    
    static /* synthetic */ long access$1200(final Builder x0) {
        return x0.totalBytesCopied;
    }
    
    static /* synthetic */ Long access$1300(final Builder x0) {
        return x0.megabytesCopiedPerChunk;
    }
    
    Builder(final StorageOptions x0, final BlobId x1, final Map x2, final boolean x3, final BlobInfo x4, final Map x5, final CopyWriter$1 x6) {
        this(x0, x1, x2, x3, x4, x5);
    }
}
