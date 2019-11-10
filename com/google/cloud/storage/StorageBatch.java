package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.common.annotations.*;
import java.util.*;
import com.google.api.services.storage.model.*;
import com.google.api.client.googleapis.json.*;

public class StorageBatch
{
    private final RpcBatch batch;
    private final StorageRpc storageRpc;
    private final StorageOptions options;
    
    StorageBatch(final StorageOptions options) {
        super();
        this.options = options;
        this.storageRpc = options.getStorageRpcV1();
        this.batch = this.storageRpc.createBatch();
    }
    
    @VisibleForTesting
    Object getBatch() {
        return this.batch;
    }
    
    @VisibleForTesting
    StorageRpc getStorageRpc() {
        return this.storageRpc;
    }
    
    @VisibleForTesting
    StorageOptions getOptions() {
        return this.options;
    }
    
    public StorageBatchResult<Boolean> delete(final String bucket, final String blob, final Storage.BlobSourceOption... options) {
        return this.delete(BlobId.of(bucket, blob), options);
    }
    
    public StorageBatchResult<Boolean> delete(final BlobId blob, final Storage.BlobSourceOption... options) {
        final StorageBatchResult<Boolean> result = new StorageBatchResult<Boolean>();
        final RpcBatch.Callback<Void> callback = this.createDeleteCallback(result);
        final Map<StorageRpc.Option, ?> optionMap = StorageImpl.optionMap(blob, (Option[])options);
        this.batch.addDelete(blob.toPb(), callback, optionMap);
        return result;
    }
    
    public StorageBatchResult<Blob> update(final BlobInfo blobInfo, final Storage.BlobTargetOption... options) {
        final StorageBatchResult<Blob> result = new StorageBatchResult<Blob>();
        final RpcBatch.Callback<StorageObject> callback = this.createUpdateCallback(this.options, result);
        final Map<StorageRpc.Option, ?> optionMap = StorageImpl.optionMap(blobInfo, (Option[])options);
        this.batch.addPatch(blobInfo.toPb(), callback, optionMap);
        return result;
    }
    
    public StorageBatchResult<Blob> get(final String bucket, final String blob, final Storage.BlobGetOption... options) {
        return this.get(BlobId.of(bucket, blob), options);
    }
    
    public StorageBatchResult<Blob> get(final BlobId blob, final Storage.BlobGetOption... options) {
        final StorageBatchResult<Blob> result = new StorageBatchResult<Blob>();
        final RpcBatch.Callback<StorageObject> callback = this.createGetCallback(this.options, result);
        final Map<StorageRpc.Option, ?> optionMap = StorageImpl.optionMap(blob, (Option[])options);
        this.batch.addGet(blob.toPb(), callback, optionMap);
        return result;
    }
    
    public void submit() {
        this.batch.submit();
    }
    
    private RpcBatch.Callback<Void> createDeleteCallback(final StorageBatchResult<Boolean> result) {
        return new RpcBatch.Callback<Void>() {
            final /* synthetic */ StorageBatchResult val$result;
            final /* synthetic */ StorageBatch this$0;
            
            StorageBatch$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public void onSuccess(final Void response) {
                result.success(true);
            }
            
            @Override
            public void onFailure(final GoogleJsonError googleJsonError) {
                final StorageException serviceException = new StorageException(googleJsonError);
                if (serviceException.getCode() == 404) {
                    result.success(false);
                }
                else {
                    result.error(serviceException);
                }
            }
            
            @Override
            public /* bridge */ void onSuccess(final Object o) {
                this.onSuccess((Void)o);
            }
        };
    }
    
    private RpcBatch.Callback<StorageObject> createGetCallback(final StorageOptions serviceOptions, final StorageBatchResult<Blob> result) {
        return new RpcBatch.Callback<StorageObject>() {
            final /* synthetic */ StorageBatchResult val$result;
            final /* synthetic */ StorageOptions val$serviceOptions;
            final /* synthetic */ StorageBatch this$0;
            
            StorageBatch$2() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public void onSuccess(final StorageObject response) {
                result.success((response == null) ? null : Blob.fromPb((Storage)serviceOptions.getService(), response));
            }
            
            @Override
            public void onFailure(final GoogleJsonError googleJsonError) {
                final StorageException serviceException = new StorageException(googleJsonError);
                if (serviceException.getCode() == 404) {
                    result.success(null);
                }
                else {
                    result.error(serviceException);
                }
            }
            
            @Override
            public /* bridge */ void onSuccess(final Object o) {
                this.onSuccess((StorageObject)o);
            }
        };
    }
    
    private RpcBatch.Callback<StorageObject> createUpdateCallback(final StorageOptions serviceOptions, final StorageBatchResult<Blob> result) {
        return new RpcBatch.Callback<StorageObject>() {
            final /* synthetic */ StorageBatchResult val$result;
            final /* synthetic */ StorageOptions val$serviceOptions;
            final /* synthetic */ StorageBatch this$0;
            
            StorageBatch$3() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public void onSuccess(final StorageObject response) {
                result.success((response == null) ? null : Blob.fromPb((Storage)serviceOptions.getService(), response));
            }
            
            @Override
            public void onFailure(final GoogleJsonError googleJsonError) {
                result.error(new StorageException(googleJsonError));
            }
            
            @Override
            public /* bridge */ void onSuccess(final Object o) {
                this.onSuccess((StorageObject)o);
            }
        };
    }
}
