package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class Acl$1 implements Function<ObjectAccessControl, Acl> {
    Acl$1() {
        super();
    }
    
    @Override
    public Acl apply(final ObjectAccessControl aclPb) {
        return Acl.fromPb(aclPb);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((ObjectAccessControl)o);
    }
}