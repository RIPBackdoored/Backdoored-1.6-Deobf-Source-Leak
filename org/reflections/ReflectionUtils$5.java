package org.reflections;

import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;
import java.lang.reflect.*;

static final class ReflectionUtils$5 implements Predicate<T> {
    final /* synthetic */ Class[] val$annotations;
    
    ReflectionUtils$5(final Class[] val$annotations) {
        this.val$annotations = val$annotations;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final T input) {
        return input != null && Arrays.equals(this.val$annotations, ReflectionUtils.access$000(input.getAnnotations()));
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((AnnotatedElement)o);
    }
}