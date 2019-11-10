package com.google.cloud.storage;

import java.io.*;
import com.google.cloud.*;

static class Builder extends BaseState.Builder<StorageOptions, BlobInfo>
{
    private Builder(final StorageOptions options, final BlobInfo blobInfo, final String uploadId) {
        super((ServiceOptions)options, (Serializable)blobInfo, uploadId);
    }
    
    public RestorableState<WriteChannel> build() {
        return (RestorableState<WriteChannel>)new StateImpl(this);
    }
    
    Builder(final StorageOptions x0, final BlobInfo x1, final String x2, final BlobWriteChannel$1 x3) {
        this(x0, x1, x2);
    }
}
