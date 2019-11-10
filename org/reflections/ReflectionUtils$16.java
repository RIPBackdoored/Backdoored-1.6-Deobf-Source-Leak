package org.reflections;

import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;

static final class ReflectionUtils$16 implements Predicate<Method> {
    final /* synthetic */ Class val$type;
    
    ReflectionUtils$16(final Class val$type) {
        this.val$type = val$type;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final Method input) {
        return input != null && this.val$type.isAssignableFrom(input.getReturnType());
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Method)o);
    }
}