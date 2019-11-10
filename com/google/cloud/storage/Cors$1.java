package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.api.services.storage.model.*;

static final class Cors$1 implements Function<Bucket.Cors, Cors> {
    Cors$1() {
        super();
    }
    
    @Override
    public Cors apply(final Bucket.Cors pb) {
        return Cors.fromPb(pb);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((Bucket.Cors)o);
    }
}