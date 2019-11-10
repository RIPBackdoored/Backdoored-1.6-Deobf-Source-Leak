package org.reflections;

import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;

static final class ReflectionUtils$10 implements Predicate<Member> {
    final /* synthetic */ int val$count;
    
    ReflectionUtils$10(final int val$count) {
        this.val$count = val$count;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final Member input) {
        return input != null && ReflectionUtils.access$200(input).length == this.val$count;
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((Member)o);
    }
}