package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class BucketInfo$2 implements Function<BucketInfo, Bucket> {
    BucketInfo$2() {
        super();
    }
    
    @Override
    public Bucket apply(final BucketInfo bucketInfo) {
        return bucketInfo.toPb();
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((BucketInfo)o);
    }
}