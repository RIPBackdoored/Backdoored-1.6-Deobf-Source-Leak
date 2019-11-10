package com.google.cloud.storage;

import java.io.*;
import java.util.*;
import com.google.api.services.storage.model.*;
import com.google.common.base.*;

public final class BlobId implements Serializable
{
    private static final long serialVersionUID = -6156002883225601925L;
    private final String bucket;
    private final String name;
    private final Long generation;
    
    private BlobId(final String bucket, final String name, final Long generation) {
        super();
        this.bucket = bucket;
        this.name = name;
        this.generation = generation;
    }
    
    public String getBucket() {
        return this.bucket;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Long getGeneration() {
        return this.generation;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("bucket", this.getBucket()).add("name", this.getName()).add("generation", this.getGeneration()).toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.bucket, this.name, this.generation);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(BlobId.class)) {
            return false;
        }
        final BlobId other = (BlobId)obj;
        return Objects.equals(this.bucket, other.bucket) && Objects.equals(this.name, other.name) && Objects.equals(this.generation, other.generation);
    }
    
    StorageObject toPb() {
        final StorageObject storageObject = new StorageObject();
        storageObject.setBucket(this.bucket);
        storageObject.setName(this.name);
        storageObject.setGeneration(this.generation);
        return storageObject;
    }
    
    public static BlobId of(final String bucket, final String name) {
        return new BlobId(Preconditions.checkNotNull(bucket), Preconditions.checkNotNull(name), null);
    }
    
    public static BlobId of(final String bucket, final String name, final Long generation) {
        return new BlobId(Preconditions.checkNotNull(bucket), Preconditions.checkNotNull(name), generation);
    }
    
    static BlobId fromPb(final StorageObject storageObject) {
        return of(storageObject.getBucket(), storageObject.getName(), storageObject.getGeneration());
    }
}
