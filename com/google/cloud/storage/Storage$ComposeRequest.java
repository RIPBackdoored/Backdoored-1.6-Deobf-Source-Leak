package com.google.cloud.storage;

import java.io.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;

public static class ComposeRequest implements Serializable
{
    private static final long serialVersionUID = -7385681353748590911L;
    private final List<SourceBlob> sourceBlobs;
    private final BlobInfo target;
    private final List<BlobTargetOption> targetOptions;
    
    private ComposeRequest(final Builder builder) {
        super();
        this.sourceBlobs = (List<SourceBlob>)ImmutableList.copyOf((Collection<?>)builder.sourceBlobs);
        this.target = builder.target;
        this.targetOptions = (List<BlobTargetOption>)ImmutableList.copyOf((Collection<?>)builder.targetOptions);
    }
    
    public List<SourceBlob> getSourceBlobs() {
        return this.sourceBlobs;
    }
    
    public BlobInfo getTarget() {
        return this.target;
    }
    
    public List<BlobTargetOption> getTargetOptions() {
        return this.targetOptions;
    }
    
    public static ComposeRequest of(final Iterable<String> sources, final BlobInfo target) {
        return newBuilder().setTarget(target).addSource(sources).build();
    }
    
    public static ComposeRequest of(final String bucket, final Iterable<String> sources, final String target) {
        return of(sources, BlobInfo.newBuilder(BlobId.of(bucket, target)).build());
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    ComposeRequest(final Builder x0, final Storage$1 x1) {
        this(x0);
    }
    
    public static class SourceBlob implements Serializable
    {
        private static final long serialVersionUID = 4094962795951990439L;
        final String name;
        final Long generation;
        
        SourceBlob(final String name) {
            this(name, null);
        }
        
        SourceBlob(final String name, final Long generation) {
            super();
            this.name = name;
            this.generation = generation;
        }
        
        public String getName() {
            return this.name;
        }
        
        public Long getGeneration() {
            return this.generation;
        }
    }
    
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
}
