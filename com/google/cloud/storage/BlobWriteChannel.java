package com.google.cloud.storage;

import java.util.*;
import com.google.cloud.storage.spi.v1.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.*;
import com.google.api.gax.retrying.*;
import com.google.cloud.*;

class BlobWriteChannel extends BaseWriteChannel<StorageOptions, BlobInfo>
{
    BlobWriteChannel(final StorageOptions options, final BlobInfo blob, final Map<StorageRpc.Option, ?> optionsMap) {
        this(options, blob, open(options, blob, optionsMap));
    }
    
    BlobWriteChannel(final StorageOptions options, final URL signedURL) {
        this(options, open(signedURL, options));
    }
    
    BlobWriteChannel(final StorageOptions options, final BlobInfo blobInfo, final String uploadId) {
        super((ServiceOptions)options, (Serializable)blobInfo, uploadId);
    }
    
    BlobWriteChannel(final StorageOptions options, final String uploadId) {
        super((ServiceOptions)options, (Serializable)null, uploadId);
    }
    
    protected void flushBuffer(final int length, final boolean last) {
        try {
            RetryHelper.runWithRetries((Callable)Executors.callable(new Runnable() {
                final /* synthetic */ int val$length;
                final /* synthetic */ boolean val$last;
                final /* synthetic */ BlobWriteChannel this$0;
                
                BlobWriteChannel$1() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public void run() {
                    ((StorageOptions)BlobWriteChannel.access$300(this.this$0)).getStorageRpcV1().write(BlobWriteChannel.access$000(this.this$0), BlobWriteChannel.access$100(this.this$0), 0, BlobWriteChannel.access$200(this.this$0), length, last);
                }
            }), ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    protected StateImpl.Builder stateBuilder() {
        return StateImpl.builder((StorageOptions)this.getOptions(), (BlobInfo)this.getEntity(), this.getUploadId());
    }
    
    private static String open(final StorageOptions options, final BlobInfo blob, final Map<StorageRpc.Option, ?> optionsMap) {
        try {
            return (String)RetryHelper.runWithRetries((Callable)new Callable<String>() {
                final /* synthetic */ StorageOptions val$options;
                final /* synthetic */ BlobInfo val$blob;
                final /* synthetic */ Map val$optionsMap;
                
                BlobWriteChannel$2() {
                    super();
                }
                
                @Override
                public String call() {
                    return options.getStorageRpcV1().open(blob.toPb(), optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, options.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, options.getClock());
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    private static String open(final URL signedURL, final StorageOptions options) {
        try {
            return (String)RetryHelper.runWithRetries((Callable)new Callable<String>() {
                final /* synthetic */ URL val$signedURL;
                final /* synthetic */ StorageOptions val$options;
                
                BlobWriteChannel$3() {
                    super();
                }
                
                @Override
                public String call() {
                    if (!isValidSignedURL(signedURL.getQuery())) {
                        throw new StorageException(2, "invalid signedURL");
                    }
                    return options.getStorageRpcV1().open(signedURL.toString());
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, options.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, options.getClock());
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    private static boolean isValidSignedURL(final String signedURLQuery) {
        boolean isValid = true;
        if (signedURLQuery.startsWith("X-Goog-Algorithm=")) {
            if (!signedURLQuery.contains("&X-Goog-Credential=") || !signedURLQuery.contains("&X-Goog-Date=") || !signedURLQuery.contains("&X-Goog-Expires=") || !signedURLQuery.contains("&X-Goog-SignedHeaders=") || !signedURLQuery.contains("&X-Goog-Signature=")) {
                isValid = false;
            }
        }
        else if (signedURLQuery.startsWith("GoogleAccessId=")) {
            if (!signedURLQuery.contains("&Expires=") || !signedURLQuery.contains("&Signature=")) {
                isValid = false;
            }
        }
        else {
            isValid = false;
        }
        return isValid;
    }
    
    protected /* bridge */ BaseState.Builder stateBuilder() {
        return this.stateBuilder();
    }
    
    static /* synthetic */ String access$000(final BlobWriteChannel x0) {
        return x0.getUploadId();
    }
    
    static /* synthetic */ byte[] access$100(final BlobWriteChannel x0) {
        return x0.getBuffer();
    }
    
    static /* synthetic */ long access$200(final BlobWriteChannel x0) {
        return x0.getPosition();
    }
    
    static /* synthetic */ ServiceOptions access$300(final BlobWriteChannel x0) {
        return x0.getOptions();
    }
    
    static /* synthetic */ boolean access$400(final String x0) {
        return isValidSignedURL(x0);
    }
    
    static /* synthetic */ void access$600(final BlobWriteChannel x0, final BaseState x1) {
        x0.restore(x1);
    }
    
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
}
