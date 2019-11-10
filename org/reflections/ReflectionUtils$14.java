package org.reflections;

import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;

static final class ReflectionUtils$14 implements Predicate<Field> {
    final /* synthetic */ Class val$type;
    
    ReflectionUtils$14(final Class val$type) {
        this.val$type = val$type;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final Field input) {
        return input != null && this.val$type.isAssignableFrom(input.getType());
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Field)o);
    }
}