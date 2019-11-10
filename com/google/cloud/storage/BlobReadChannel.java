package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.api.services.storage.model.*;
import java.nio.channels.*;
import java.nio.*;
import java.util.concurrent.*;
import com.google.api.gax.retrying.*;
import java.util.*;
import java.io.*;
import com.google.common.base.*;
import com.google.cloud.*;

class BlobReadChannel implements ReadChannel
{
    private static final int DEFAULT_CHUNK_SIZE = 2097152;
    private final StorageOptions serviceOptions;
    private final BlobId blob;
    private final Map<StorageRpc.Option, ?> requestOptions;
    private String lastEtag;
    private long position;
    private boolean isOpen;
    private boolean endOfStream;
    private int chunkSize;
    private final StorageRpc storageRpc;
    private final StorageObject storageObject;
    private int bufferPos;
    private byte[] buffer;
    
    BlobReadChannel(final StorageOptions serviceOptions, final BlobId blob, final Map<StorageRpc.Option, ?> requestOptions) {
        super();
        this.chunkSize = 2097152;
        this.serviceOptions = serviceOptions;
        this.blob = blob;
        this.requestOptions = requestOptions;
        this.isOpen = true;
        this.storageRpc = serviceOptions.getStorageRpcV1();
        this.storageObject = blob.toPb();
    }
    
    public RestorableState<ReadChannel> capture() {
        final StateImpl.Builder builder = StateImpl.builder(this.serviceOptions, this.blob, this.requestOptions).setPosition(this.position).setIsOpen(this.isOpen).setEndOfStream(this.endOfStream).setChunkSize(this.chunkSize);
        if (this.buffer != null) {
            builder.setPosition(this.position + this.bufferPos);
            builder.setEndOfStream(false);
        }
        return builder.build();
    }
    
    public boolean isOpen() {
        return this.isOpen;
    }
    
    public void close() {
        if (this.isOpen) {
            this.buffer = null;
            this.isOpen = false;
        }
    }
    
    private void validateOpen() throws ClosedChannelException {
        if (!this.isOpen) {
            throw new ClosedChannelException();
        }
    }
    
    public void seek(final long position) throws IOException {
        this.validateOpen();
        this.position = position;
        this.buffer = null;
        this.bufferPos = 0;
        this.endOfStream = false;
    }
    
    public void setChunkSize(final int chunkSize) {
        this.chunkSize = ((chunkSize <= 0) ? 2097152 : chunkSize);
    }
    
    public int read(final ByteBuffer byteBuffer) throws IOException {
        this.validateOpen();
        if (this.buffer == null) {
            if (this.endOfStream) {
                return -1;
            }
            final int toRead = Math.max(byteBuffer.remaining(), this.chunkSize);
            try {
                final Tuple<String, byte[]> result = (Tuple<String, byte[]>)RetryHelper.runWithRetries((Callable)new Callable<Tuple<String, byte[]>>() {
                    final /* synthetic */ int val$toRead;
                    final /* synthetic */ BlobReadChannel this$0;
                    
                    BlobReadChannel$1() {
                        this.this$0 = this$0;
                        super();
                    }
                    
                    @Override
                    public Tuple<String, byte[]> call() {
                        return this.this$0.storageRpc.read(this.this$0.storageObject, this.this$0.requestOptions, this.this$0.position, toRead);
                    }
                    
                    @Override
                    public /* bridge */ Object call() throws Exception {
                        return this.call();
                    }
                }, this.serviceOptions.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, this.serviceOptions.getClock());
                if (((byte[])result.y()).length > 0 && this.lastEtag != null && !Objects.equals(result.x(), this.lastEtag)) {
                    final StringBuilder messageBuilder = new StringBuilder();
                    messageBuilder.append("Blob ").append(this.blob).append(" was updated while reading");
                    throw new StorageException(0, messageBuilder.toString());
                }
                this.lastEtag = (String)result.x();
                this.buffer = (byte[])result.y();
            }
            catch (RetryHelper.RetryHelperException e) {
                throw StorageException.translateAndThrow(e);
            }
            if (toRead > this.buffer.length) {
                this.endOfStream = true;
                if (this.buffer.length == 0) {
                    this.buffer = null;
                    return -1;
                }
            }
        }
        final int toWrite = Math.min(this.buffer.length - this.bufferPos, byteBuffer.remaining());
        byteBuffer.put(this.buffer, this.bufferPos, toWrite);
        this.bufferPos += toWrite;
        if (this.bufferPos >= this.buffer.length) {
            this.position += this.buffer.length;
            this.buffer = null;
            this.bufferPos = 0;
        }
        return toWrite;
    }
    
    static /* synthetic */ StorageObject access$000(final BlobReadChannel x0) {
        return x0.storageObject;
    }
    
    static /* synthetic */ Map access$100(final BlobReadChannel x0) {
        return x0.requestOptions;
    }
    
    static /* synthetic */ long access$200(final BlobReadChannel x0) {
        return x0.position;
    }
    
    static /* synthetic */ StorageRpc access$300(final BlobReadChannel x0) {
        return x0.storageRpc;
    }
    
    static /* synthetic */ String access$1302(final BlobReadChannel x0, final String x1) {
        return x0.lastEtag = x1;
    }
    
    static /* synthetic */ long access$202(final BlobReadChannel x0, final long x1) {
        return x0.position = x1;
    }
    
    static /* synthetic */ boolean access$1402(final BlobReadChannel x0, final boolean x1) {
        return x0.isOpen = x1;
    }
    
    static /* synthetic */ boolean access$1502(final BlobReadChannel x0, final boolean x1) {
        return x0.endOfStream = x1;
    }
    
    static /* synthetic */ int access$1602(final BlobReadChannel x0, final int x1) {
        return x0.chunkSize = x1;
    }
    
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
            channel.lastEtag = this.lastEtag;
            channel.position = this.position;
            channel.isOpen = this.isOpen;
            channel.endOfStream = this.endOfStream;
            channel.chunkSize = this.chunkSize;
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
}
