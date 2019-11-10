package org.reflections;

import com.google.common.base.*;
import java.lang.annotation.*;
import javax.annotation.*;
import java.lang.reflect.*;

static final class ReflectionUtils$7 implements Predicate<T> {
    final /* synthetic */ Annotation[] val$annotations;
    
    ReflectionUtils$7(final Annotation[] val$annotations) {
        this.val$annotations = val$annotations;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final T input) {
        if (input != null) {
            final Annotation[] inputAnnotations = input.getAnnotations();
            if (inputAnnotations.length == this.val$annotations.length) {
                for (int i = 0; i < inputAnnotations.length; ++i) {
                    if (!ReflectionUtils.access$100(inputAnnotations[i], this.val$annotations[i])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((AnnotatedElement)o);
    }
}