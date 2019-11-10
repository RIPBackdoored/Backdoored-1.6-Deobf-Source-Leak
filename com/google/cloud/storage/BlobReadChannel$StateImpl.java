package com.google.cloud.storage;

import java.io.*;
import com.google.cloud.storage.spi.v1.*;
import java.util.*;
import com.google.common.base.*;
import com.google.cloud.*;

static class StateImpl implements RestorableState<ReadChannel>, Serializable
{
    private static final long serialVersionUID = 3889420316004453706L;
    private final StorageOptions serviceOptions;
    private final BlobId blob;
    private final Map<StorageRpc.Option, ?> requestOptions;
    private final String lastEtag;
    private final long position;
    private final boolean isOpen;
    private final boolean endOfStream;
    private final int chunkSize;
    
    StateImpl(final Builder builder) {
        super();
        this.serviceOptions = builder.serviceOptions;
        this.blob = builder.blob;
        this.requestOptions = builder.requestOptions;
        this.lastEtag = builder.lastEtag;
        this.position = builder.position;
        this.isOpen = builder.isOpen;
        this.endOfStream = builder.endOfStream;
        this.chunkSize = builder.chunkSize;
    }
    
    static Builder builder(final StorageOptions options, final BlobId blob, final Map<StorageRpc.Option, ?> reqOptions) {
        return new Builder(options, blob, (Map)reqOptions);
    }
    
    public ReadChannel restore() {
        final BlobReadChannel channel = new BlobReadChannel(this.serviceOptions, this.blob, this.requestOptions);
        BlobReadChannel.access$1302(channel, this.lastEtag);
        BlobReadChannel.access$202(channel, this.position);
        BlobReadChannel.access$1402(channel, this.isOpen);
        BlobReadChannel.access$1502(channel, this.endOfStream);
        BlobReadChannel.access$1602(channel, this.chunkSize);
        return (ReadChannel)channel;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.serviceOptions, this.blob, this.requestOptions, this.lastEtag, this.position, this.isOpen, this.endOfStream, this.chunkSize);
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
        return Objects.equals(this.serviceOptions, other.serviceOptions) && Objects.equals(this.blob, other.blob) && Objects.equals(this.requestOptions, other.requestOptions) && Objects.equals(this.lastEtag, other.lastEtag) && this.position == other.position && this.isOpen == other.isOpen && this.endOfStream == other.endOfStream && this.chunkSize == other.chunkSize;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("blob", this.blob).add("position", this.position).add("isOpen", this.isOpen).add("endOfStream", this.endOfStream).toString();
    }
    
    public /* bridge */ Restorable restore() {
        return (Restorable)this.restore();
    }
    
    static class Builder
    {
        private final StorageOptions serviceOptions;
        private final BlobId blob;
        private final Map<StorageRpc.Option, ?> requestOptions;
        private String lastEtag;
        private long position;
        private boolean isOpen;
        private boolean endOfStream;
        private int chunkSize;
        
        private Builder(final StorageOptions options, final BlobId blob, final Map<StorageRpc.Option, ?> reqOptions) {
            super();
            this.serviceOptions = options;
            this.blob = blob;
            this.requestOptions = reqOptions;
        }
        
        Builder setLastEtag(final String lastEtag) {
            this.lastEtag = lastEtag;
            return this;
        }
        
        Builder setPosition(final long position) {
            this.position = position;
            return this;
        }
        
        Builder setIsOpen(final boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }
        
        Builder setEndOfStream(final boolean endOfStream) {
            this.endOfStream = endOfStream;
            return this;
        }
        
        Builder setChunkSize(final int chunkSize) {
            this.chunkSize = chunkSize;
            return this;
        }
        
        RestorableState<ReadChannel> build() {
            return (RestorableState<ReadChannel>)new StateImpl(this);
        }
        
        static /* synthetic */ StorageOptions access$400(final Builder x0) {
            return x0.serviceOptions;
        }
        
        static /* synthetic */ BlobId access$500(final Builder x0) {
            return x0.blob;
        }
        
        static /* synthetic */ Map access$600(final Builder x0) {
            return x0.requestOptions;
        }
        
        static /* synthetic */ String access$700(final Builder x0) {
            return x0.lastEtag;
        }
        
        static /* synthetic */ long access$800(final Builder x0) {
            return x0.position;
        }
        
        static /* synthetic */ boolean access$900(final Builder x0) {
            return x0.isOpen;
        }
        
        static /* synthetic */ boolean access$1000(final Builder x0) {
            return x0.endOfStream;
        }
        
        static /* synthetic */ int access$1100(final Builder x0) {
            return x0.chunkSize;
        }
        
        Builder(final StorageOptions x0, final BlobId x1, final Map x2, final BlobReadChannel$1 x3) {
            this(x0, x1, x2);
        }
    }
}
