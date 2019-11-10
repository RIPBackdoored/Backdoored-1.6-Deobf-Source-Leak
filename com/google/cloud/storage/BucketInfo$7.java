package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class BucketInfo$7 implements Function<BucketAccessControl, Acl> {
    BucketInfo$7() {
        super();
    }
    
    @Override
    public Acl apply(final BucketAccessControl bucketAccessControl) {
        return Acl.fromPb(bucketAccessControl);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((BucketAccessControl)o);
    }
}