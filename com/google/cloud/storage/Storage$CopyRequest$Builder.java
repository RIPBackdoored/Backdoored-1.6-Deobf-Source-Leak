package com.google.cloud.storage;

import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;

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
