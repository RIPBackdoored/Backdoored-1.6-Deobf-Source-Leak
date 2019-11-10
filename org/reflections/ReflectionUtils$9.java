package org.reflections;

import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;

static final class ReflectionUtils$9 implements Predicate<Member> {
    final /* synthetic */ Class[] val$types;
    
    ReflectionUtils$9(final Class[] val$types) {
        this.val$types = val$types;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final Member input) {
        if (input != null) {
            final Class<?>[] parameterTypes = (Class<?>[])ReflectionUtils.access$200(input);
            if (parameterTypes.length == this.val$types.length) {
                for (int i = 0; i < parameterTypes.length; ++i) {
                    if (!parameterTypes[i].isAssignableFrom(this.val$types[i]) || (parameterTypes[i] == Object.class && this.val$types[i] != Object.class)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Member)o);
    }
}