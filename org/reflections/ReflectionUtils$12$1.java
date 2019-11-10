package org.reflections;

import com.google.common.base.*;
import java.lang.annotation.*;
import javax.annotation.*;

class ReflectionUtils$12$1 implements Predicate<Annotation> {
    final /* synthetic */ ReflectionUtils$12 this$0;
    
    ReflectionUtils$12$1(final ReflectionUtils$12 this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final Annotation input) {
        return ReflectionUtils.access$100(this.this$0.val$annotation, input);
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Annotation)o);
    }
}