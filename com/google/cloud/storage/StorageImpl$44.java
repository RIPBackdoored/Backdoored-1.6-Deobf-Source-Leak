package com.google.cloud.storage;

import com.google.common.base.*;
import java.util.*;

class StorageImpl$44 implements Function<String, Boolean> {
    final /* synthetic */ Set val$heldPermissions;
    final /* synthetic */ StorageImpl this$0;
    
    StorageImpl$44(final StorageImpl this$0, final Set val$heldPermissions) {
        this.this$0 = this$0;
        this.val$heldPermissions = val$heldPermissions;
        super();
    }
    
    @Override
    public Boolean apply(final String permission) {
        return this.val$heldPermissions.contains(permission);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((String)o);
    }
}