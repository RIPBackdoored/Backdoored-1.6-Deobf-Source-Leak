package com.google.cloud.storage;

import com.google.common.base.*;
import com.google.cloud.*;
import com.google.api.services.storage.model.*;

static final class Blob$1 implements Function<Tuple<Storage, StorageObject>, Blob> {
    Blob$1() {
        super();
    }
    
    @Override
    public Blob apply(final Tuple<Storage, StorageObject> pb) {
        return Blob.fromPb((Storage)pb.x(), (StorageObject)pb.y());
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((Tuple<Storage, StorageObject>)o);
    }
}