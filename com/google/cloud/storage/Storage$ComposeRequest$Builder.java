package com.google.cloud.storage;

import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;

public static class Builder
{
    private final List<SourceBlob> sourceBlobs;
    private final Set<BlobTargetOption> targetOptions;
    private BlobInfo target;
    
    public Builder() {
        super();
        this.sourceBlobs = new LinkedList<SourceBlob>();
        this.targetOptions = new LinkedHashSet<BlobTargetOption>();
    }
    
    public Builder addSource(final Iterable<String> blobs) {
        for (final String blob : blobs) {
            this.sourceBlobs.add(new SourceBlob(blob));
        }
        return this;
    }
    
    public Builder addSource(final String... blobs) {
        return this.addSource(Arrays.asList(blobs));
    }
    
    public Builder addSource(final String blob, final long generation) {
        this.sourceBlobs.add(new SourceBlob(blob, generation));
        return this;
    }
    
    public Builder setTarget(final BlobInfo target) {
        this.target = target;
        return this;
    }
    
    public Builder setTargetOptions(final BlobTargetOption... options) {
        Collections.addAll(this.targetOptions, options);
        return this;
    }
    
    public Builder setTargetOptions(final Iterable<BlobTargetOption> options) {
        Iterables.addAll(this.targetOptions, options);
        return this;
    }
    
    public ComposeRequest build() {
        Preconditions.checkArgument(!this.sourceBlobs.isEmpty());
        Preconditions.checkNotNull(this.target);
        return new ComposeRequest(this);
    }
    
    static /* synthetic */ List access$300(final Builder x0) {
        return x0.sourceBlobs;
    }
    
    static /* synthetic */ BlobInfo access$400(final Builder x0) {
        return x0.target;
    }
    
    static /* synthetic */ Set access$500(final Builder x0) {
        return x0.targetOptions;
    }
}
