package com.google.cloud.storage;

import com.google.api.core.*;

static final class Acl$Project$ProjectRole$1 implements ApiFunction<String, ProjectRole> {
    Acl$Project$ProjectRole$1() {
        super();
    }
    
    public ProjectRole apply(final String constant) {
        return new ProjectRole(constant);
    }
    
    public /* bridge */ Object apply(final Object o) {
        return this.apply((String)o);
    }
}