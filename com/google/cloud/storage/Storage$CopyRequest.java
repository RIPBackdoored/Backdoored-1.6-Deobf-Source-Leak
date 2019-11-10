package com.google.cloud.storage;

import java.io.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;

public static class CopyRequest implements Serializable
{
    private static final long serialVersionUID = -4498650529476219937L;
    private final BlobId source;
    private final List<BlobSourceOption> sourceOptions;
    private final boolean overrideInfo;
    private final BlobInfo target;
    private final List<BlobTargetOption> targetOptions;
    private final Long megabytesCopiedPerChunk;
    
    private CopyRequest(final Builder builder) {
        super();
        this.source = Preconditions.checkNotNull(builder.source);
        this.sourceOptions = (List<BlobSourceOption>)ImmutableList.copyOf((Collection<?>)builder.sourceOptions);
        this.overrideInfo = builder.overrideInfo;
        this.target = Preconditions.checkNotNull(builder.target);
        this.targetOptions = (List<BlobTargetOption>)ImmutableList.copyOf((Collection<?>)builder.targetOptions);
        this.megabytesCopiedPerChunk = builder.megabytesCopiedPerChunk;
    }
    
    public BlobId getSource() {
        return this.source;
    }
    
    public List<BlobSourceOption> getSourceOptions() {
        return this.sourceOptions;
    }
    
    public BlobInfo getTarget() {
        return this.target;
    }
    
    public boolean overrideInfo() {
        return this.overrideInfo;
    }
    
    public List<BlobTargetOption> getTargetOptions() {
        return this.targetOptions;
    }
    
    public Long getMegabytesCopiedPerChunk() {
        return this.megabytesCopiedPerChunk;
    }
    
    public static CopyRequest of(final String sourceBucket, final String sourceBlob, final BlobInfo target) {
        return newBuilder().setSource(sourceBucket, sourceBlob).setTarget(target, new BlobTargetOption[0]).build();
    }
    
    public static CopyRequest of(final BlobId sourceBlobId, final BlobInfo target) {
        return newBuilder().setSource(sourceBlobId).setTarget(target, new BlobTargetOption[0]).build();
    }
    
    public static CopyRequest of(final String sourceBucket, final String sourceBlob, final String targetBlob) {
        return newBuilder().setSource(sourceBucket, sourceBlob).setTarget(BlobId.of(sourceBucket, targetBlob)).build();
    }
    
    public static CopyRequest of(final String sourceBucket, final String sourceBlob, final BlobId target) {
        return newBuilder().setSource(sourceBucket, sourceBlob).setTarget(target).build();
    }
    
    public static CopyRequest of(final BlobId sourceBlobId, final String targetBlob) {
        return newBuilder().setSource(sourceBlobId).setTarget(BlobId.of(sourceBlobId.getBucket(), targetBlob)).build();
    }
    
    public static CopyRequest of(final BlobId sourceBlobId, final BlobId targetBlobId) {
        return newBuilder().setSource(sourceBlobId).setTarget(targetBlobId).build();
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    CopyRequest(final Builder x0, final Storage$1 x1) {
        this(x0);
    }
    
    public static class Builder
    {
        private final Set<BlobSourceOption> sourceOptions;
        private final Set<BlobTargetOption> targetOptions;
        private BlobId source;
        private boolean overrideInfo;
        private BlobInfo target;
        private Long megabytesCopiedPerChunk;
        
        public Builder() {
            super();
            this.sourceOptions = new LinkedHashSet<BlobSourceOption>();
            this.targetOptions = new LinkedHashSet<BlobTargetOption>();
        }
        
        public Builder setSource(final String bucket, final String blob) {
            this.source = BlobId.of(bucket, blob);
            return this;
        }
        
        public Builder setSource(final BlobId source) {
            this.source = source;
            return this;
        }
        
        public Builder setSourceOptions(final BlobSourceOption... options) {
            Collections.addAll(this.sourceOptions, options);
            return this;
        }
        
        public Builder setSourceOptions(final Iterable<BlobSourceOption> options) {
            Iterables.addAll(this.sourceOptions, options);
            return this;
        }
        
        public Builder setTarget(final BlobId targetId) {
            this.overrideInfo = false;
            this.target = BlobInfo.newBuilder(targetId).build();
            return this;
        }
        
        public Builder setTarget(final BlobId targetId, final BlobTargetOption... options) {
            this.overrideInfo = false;
            this.target = BlobInfo.newBuilder(targetId).build();
            Collections.addAll(this.targetOptions, options);
            return this;
        }
        
        public Builder setTarget(final BlobInfo target, final BlobTargetOption... options) {
            this.overrideInfo = true;
            this.target = Preconditions.checkNotNull(target);
            Collections.addAll(this.targetOptions, options);
            return this;
        }
        
        public Builder setTarget(final BlobInfo target, final Iterable<BlobTargetOption> options) {
            this.overrideInfo = true;
            this.target = Preconditions.checkNotNull(target);
            Iterables.addAll(this.targetOptions, options);
            return this;
        }
        
        public Builder setTarget(final BlobId targetId, final Iterable<BlobTargetOption> options) {
            this.overrideInfo = false;
            this.target = BlobInfo.newBuilder(targetId).build();
            Iterables.addAll(this.targetOptions, options);
            return this;
        }
        
        public Builder setMegabytesCopiedPerChunk(final Long megabytesCopiedPerChunk) {
            this.megabytesCopiedPerChunk = megabytesCopiedPerChunk;
            return this;
        }
        
        public CopyRequest build() {
            return new CopyRequest(this);
        }
        
        static /* synthetic */ BlobId access$700(final Builder x0) {
            return x0.source;
        }
        
        static /* synthetic */ Set access$800(final Builder x0) {
            return x0.sourceOptions;
        }
        
        static /* synthetic */ boolean access$900(final Builder x0) {
            return x0.overrideInfo;
        }
        
        static /* synthetic */ BlobInfo access$1000(final Builder x0) {
            return x0.target;
        }
        
        static /* synthetic */ Set access$1100(final Builder x0) {
            return x0.targetOptions;
        }
        
        static /* synthetic */ Long access$1200(final Builder x0) {
            return x0.megabytesCopiedPerChunk;
        }
    }
}
