package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class Acl$2 implements Function<BucketAccessControl, Acl> {
    Acl$2() {
        super();
    }
    
    @Override
    public Acl apply(final BucketAccessControl aclPb) {
        return Acl.fromPb(aclPb);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((BucketAccessControl)o);
    }
}