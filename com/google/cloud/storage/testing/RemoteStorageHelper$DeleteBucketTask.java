package com.google.cloud.storage.testing;

import java.util.concurrent.*;
import com.google.common.base.*;
import com.google.api.gax.paging.*;
import com.google.cloud.storage.*;
import java.util.*;

private static class DeleteBucketTask implements Callable<Boolean>
{
    private final Storage storage;
    private final String bucket;
    private final String userProject;
    
    public DeleteBucketTask(final Storage storage, final String bucket) {
        super();
        this.storage = storage;
        this.bucket = bucket;
        this.userProject = "";
    }
    
    public DeleteBucketTask(final Storage storage, final String bucket, final String userProject) {
        super();
        this.storage = storage;
        this.bucket = bucket;
        this.userProject = userProject;
    }
    
    @Override
    public Boolean call() {
        while (true) {
            final ArrayList<BlobId> ids = new ArrayList<BlobId>();
            Page<Blob> listedBlobs;
            if (Strings.isNullOrEmpty(this.userProject)) {
                listedBlobs = this.storage.list(this.bucket, Storage.BlobListOption.versions(true));
            }
            else {
                listedBlobs = this.storage.list(this.bucket, Storage.BlobListOption.versions(true), Storage.BlobListOption.userProject(this.userProject));
            }
            for (final BlobInfo info : listedBlobs.getValues()) {
                ids.add(info.getBlobId());
            }
            if (!ids.isEmpty()) {
                final List<Boolean> results = this.storage.delete(ids);
                if (!Strings.isNullOrEmpty(this.userProject)) {
                    for (int i = 0; i < results.size(); ++i) {
                        if (!results.get(i)) {
                            this.storage.delete(this.bucket, ids.get(i).getName(), Storage.BlobSourceOption.userProject(this.userProject));
                        }
                    }
                }
            }
            try {
                if (Strings.isNullOrEmpty(this.userProject)) {
                    this.storage.delete(this.bucket, new Storage.BucketSourceOption[0]);
                }
                else {
                    this.storage.delete(this.bucket, Storage.BucketSourceOption.userProject(this.userProject));
                }
                return true;
            }
            catch (StorageException e) {
                if (e.getCode() == 409) {
                    try {
                        Thread.sleep(500L);
                        continue;
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        throw e;
                    }
                    throw e;
                    continue;
                }
                throw e;
            }
        }
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}
