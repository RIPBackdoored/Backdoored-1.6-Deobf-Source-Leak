package com.google.cloud.storage;

import java.io.*;
import com.google.cloud.*;

static class StateImpl extends BaseState<StorageOptions, BlobInfo>
{
    private static final long serialVersionUID = -9028324143780151286L;
    
    StateImpl(final Builder builder) {
        super((BaseState.Builder)builder);
    }
    
    static Builder builder(final StorageOptions options, final BlobInfo blobInfo, final String uploadId) {
        return new Builder(options, blobInfo, uploadId);
    }
    
    public WriteChannel restore() {
        final BlobWriteChannel channel = new BlobWriteChannel((StorageOptions)this.serviceOptions, (BlobInfo)this.entity, this.uploadId);
        BlobWriteChannel.access$600(channel, this);
        return (WriteChannel)channel;
    }
    
    public /* bridge */ Restorable restore() {
        return (Restorable)this.restore();
    }
    
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
}
