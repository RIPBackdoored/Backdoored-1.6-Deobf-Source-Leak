package com.google.cloud.storage;

import java.io.*;
import com.google.cloud.storage.spi.v1.*;
import java.util.*;
import com.google.common.base.*;
import com.google.cloud.*;

static class StateImpl implements RestorableState<CopyWriter>, Serializable
{
    private static final long serialVersionUID = 1693964441435822700L;
    private final StorageOptions serviceOptions;
    private final BlobId source;
    private final Map<StorageRpc.Option, ?> sourceOptions;
    private final boolean overrideInfo;
    private final BlobInfo target;
    private final Map<StorageRpc.Option, ?> targetOptions;
    private final BlobInfo result;
    private final long blobSize;
    private final boolean isDone;
    private final String rewriteToken;
    private final long totalBytesCopied;
    private final Long megabytesCopiedPerChunk;
    
    StateImpl(final Builder builder) {
        super();
        this.serviceOptions = builder.serviceOptions;
        this.source = builder.source;
        this.sourceOptions = builder.sourceOptions;
        this.overrideInfo = builder.overrideInfo;
        this.target = builder.target;
        this.targetOptions = builder.targetOptions;
        this.result = builder.result;
        this.blobSize = builder.blobSize;
        this.isDone = builder.isDone;
        this.rewriteToken = builder.rewriteToken;
        this.totalBytesCopied = builder.totalBytesCopied;
        this.megabytesCopiedPerChunk = builder.megabytesCopiedPerChunk;
    }
    
    static Builder newBuilder(final StorageOptions options, final BlobId source, final Map<StorageRpc.Option, ?> sourceOptions, final boolean overrideInfo, final BlobInfo target, final Map<StorageRpc.Option, ?> targetOptions) {
        return new Builder(options, source, (Map)sourceOptions, overrideInfo, target, (Map)targetOptions);
    }
    
    public CopyWriter restore() {
        final StorageRpc.RewriteRequest rewriteRequest = new StorageRpc.RewriteRequest(this.source.toPb(), this.sourceOptions, this.overrideInfo, this.target.toPb(), this.targetOptions, this.megabytesCopiedPerChunk);
        final StorageRpc.RewriteResponse rewriteResponse = new StorageRpc.RewriteResponse(rewriteRequest, (this.result != null) ? this.result.toPb() : null, this.blobSize, this.isDone, this.rewriteToken, this.totalBytesCopied);
        return new CopyWriter(this.serviceOptions, rewriteResponse);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.serviceOptions, this.source, this.sourceOptions, this.overrideInfo, this.target, this.targetOptions, this.result, this.blobSize, this.isDone, this.megabytesCopiedPerChunk, this.rewriteToken, this.totalBytesCopied);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof StateImpl)) {
            return false;
        }
        final StateImpl other = (StateImpl)obj;
        return Objects.equals(this.serviceOptions, other.serviceOptions) && Objects.equals(this.source, other.source) && Objects.equals(this.sourceOptions, other.sourceOptions) && Objects.equals(this.overrideInfo, other.overrideInfo) && Objects.equals(this.target, other.target) && Objects.equals(this.targetOptions, other.targetOptions) && Objects.equals(this.result, other.result) && Objects.equals(this.rewriteToken, other.rewriteToken) && Objects.equals(this.megabytesCopiedPerChunk, other.megabytesCopiedPerChunk) && this.blobSize == other.blobSize && this.isDone == other.isDone && this.totalBytesCopied == other.totalBytesCopied;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("source", this.source).add("overrideInfo", this.overrideInfo).add("target", this.target).add("result", this.result).add("blobSize", this.blobSize).add("isDone", this.isDone).add("rewriteToken", this.rewriteToken).add("totalBytesCopied", this.totalBytesCopied).add("megabytesCopiedPerChunk", this.megabytesCopiedPerChunk).toString();
    }
    
    public /* bridge */ Restorable restore() {
        return (Restorable)this.restore();
    }
    
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
}
