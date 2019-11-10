package com.google.cloud.storage;

import com.google.common.base.*;

static final class Cors$4 implements Function<String, Origin> {
    Cors$4() {
        super();
    }
    
    @Override
    public Origin apply(final String value) {
        return Origin.of(value);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((String)o);
    }
}