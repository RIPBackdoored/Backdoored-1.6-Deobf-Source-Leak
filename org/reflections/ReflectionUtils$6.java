package org.reflections;

import com.google.common.base.*;
import java.lang.annotation.*;
import javax.annotation.*;
import java.lang.reflect.*;

static final class ReflectionUtils$6 implements Predicate<T> {
    final /* synthetic */ Annotation val$annotation;
    
    ReflectionUtils$6(final Annotation val$annotation) {
        this.val$annotation = val$annotation;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final T input) {
        return input != null && input.isAnnotationPresent(this.val$annotation.annotationType()) && ReflectionUtils.access$100(input.getAnnotation(this.val$annotation.annotationType()), this.val$annotation);
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((AnnotatedElement)o);
    }
}