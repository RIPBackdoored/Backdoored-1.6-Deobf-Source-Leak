package org.reflections;

import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;
import java.lang.annotation.*;
import com.google.common.collect.*;

static final class ReflectionUtils$11 implements Predicate<Member> {
    final /* synthetic */ Class val$annotationClass;
    
    ReflectionUtils$11(final Class val$annotationClass) {
        this.val$annotationClass = val$annotationClass;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final Member input) {
        return input != null && Iterables.any((Iterable<Object>)ReflectionUtils.access$400(ReflectionUtils.access$300(input)), (Predicate<? super Object>)new Predicate<Class<? extends Annotation>>() {
            final /* synthetic */ ReflectionUtils$11 this$0;
            
            ReflectionUtils$11$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Class<? extends Annotation> input) {
                return input.equals(this.this$0.val$annotationClass);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Class<? extends Annotation>)o);
            }
        });
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Member)o);
    }
}