package com.google.cloud.storage;

import java.util.concurrent.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;

class StorageImpl$24 implements Callable<List<BucketAccessControl>> {
    final /* synthetic */ String val$bucket;
    final /* synthetic */ Map val$optionsMap;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$24(final StorageImpl this$0, final String val$bucket, final Map val$optionsMap) {
        this.this$0 = this$0;
        this.val$bucket = val$bucket;
        this.val$optionsMap = val$optionsMap;
        super();
    }
    
    @Override
    public List<BucketAccessControl> call() {
        return StorageImpl.access$000(this.this$0).listAcls(this.val$bucket, this.val$optionsMap);
    }
    
    @Override
    public /* bridge */ Object call() throws Exception {
        return this.call();
    }
}