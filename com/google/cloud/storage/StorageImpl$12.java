package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

class StorageImpl$12 implements Callable<Boolean> {
    final /* synthetic */ Bucket val$bucketPb;
    final /* synthetic */ Map val$optionsMap;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$12(final StorageImpl this$0, final Bucket val$bucketPb, final Map val$optionsMap) {
        this.this$0 = this$0;
        this.val$bucketPb = val$bucketPb;
        this.val$optionsMap = val$optionsMap;
        super();
    }
    
    @Override
    public Boolean call() {
        return StorageImpl.access$000(this.this$0).delete(this.val$bucketPb, this.val$optionsMap);
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}