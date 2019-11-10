package org.reflections;

import com.google.common.base.*;
import javax.annotation.*;

class Reflections$2 implements Predicate<String> {
    final /* synthetic */ Reflections this$0;
    
    Reflections$2(final Reflections this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public boolean apply(@Nullable final String input) {
        final Class<?> type = ReflectionUtils.forName(input, Reflections.access$000(this.this$0));
        return type != null && !type.isInterface();
    }
    
    @Override
    public /* bridge */ boolean apply(@Nullable final Object o) {
        return this.apply((String)o);
    }
}