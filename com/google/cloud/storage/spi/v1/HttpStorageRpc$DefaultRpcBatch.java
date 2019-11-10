package com.google.cloud.storage.spi.v1;

import com.google.api.services.storage.*;
import com.google.api.client.googleapis.batch.*;
import com.google.api.services.storage.model.*;
import java.io.*;
import com.google.api.client.http.*;
import io.opencensus.trace.*;
import io.opencensus.common.*;
import java.util.*;

private class DefaultRpcBatch implements RpcBatch
{
    private static final int MAX_BATCH_SIZE = 100;
    private final Storage storage;
    private final LinkedList<BatchRequest> batches;
    private int currentBatchSize;
    final /* synthetic */ HttpStorageRpc this$0;
    
    private DefaultRpcBatch(final HttpStorageRpc httpStorageRpc, final Storage storage) {
        this.this$0 = httpStorageRpc;
        super();
        this.storage = storage;
        (this.batches = new LinkedList<BatchRequest>()).add(storage.batch(HttpStorageRpc.access$000(httpStorageRpc)));
    }
    
    @Override
    public void addDelete(final StorageObject storageObject, final Callback<Void> callback, final Map<Option, ?> options) {
        try {
            if (this.currentBatchSize == 100) {
                this.batches.add(this.storage.batch());
                this.currentBatchSize = 0;
            }
            HttpStorageRpc.access$200(this.this$0, storageObject, options).queue((BatchRequest)this.batches.getLast(), HttpStorageRpc.access$100(callback));
            ++this.currentBatchSize;
        }
        catch (IOException ex) {
            throw HttpStorageRpc.access$300(ex);
        }
    }
    
    @Override
    public void addPatch(final StorageObject storageObject, final Callback<StorageObject> callback, final Map<Option, ?> options) {
        try {
            if (this.currentBatchSize == 100) {
                this.batches.add(this.storage.batch());
                this.currentBatchSize = 0;
            }
            HttpStorageRpc.access$400(this.this$0, storageObject, options).queue((BatchRequest)this.batches.getLast(), HttpStorageRpc.access$100(callback));
            ++this.currentBatchSize;
        }
        catch (IOException ex) {
            throw HttpStorageRpc.access$300(ex);
        }
    }
    
    @Override
    public void addGet(final StorageObject storageObject, final Callback<StorageObject> callback, final Map<Option, ?> options) {
        try {
            if (this.currentBatchSize == 100) {
                this.batches.add(this.storage.batch());
                this.currentBatchSize = 0;
            }
            HttpStorageRpc.access$500(this.this$0, storageObject, options).queue((BatchRequest)this.batches.getLast(), HttpStorageRpc.access$100(callback));
            ++this.currentBatchSize;
        }
        catch (IOException ex) {
            throw HttpStorageRpc.access$300(ex);
        }
    }
    
    @Override
    public void submit() {
        final Span span = HttpStorageRpc.access$600(this.this$0, HttpStorageRpcSpans.SPAN_NAME_BATCH_SUBMIT);
        final Scope scope = HttpStorageRpc.access$700(this.this$0).withSpan(span);
        try {
            span.putAttribute("batch size", AttributeValue.longAttributeValue((long)this.batches.size()));
            for (final BatchRequest batch : this.batches) {
                span.addAnnotation("Execute batch request");
                batch.setBatchUrl(new GenericUrl(String.format("%s/batch/storage/v1", HttpStorageRpc.access$800(this.this$0).getHost())));
                batch.execute();
            }
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw HttpStorageRpc.access$300(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    DefaultRpcBatch(final HttpStorageRpc x0, final Storage x1, final HttpStorageRpc$1 x2) {
        this(x0, x1);
    }
}
