package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

static final class Optional$1 implements Iterable<T> {
    final /* synthetic */ Iterable val$optionals;
    
    Optional$1(final Iterable val$optionals) {
        this.val$optionals = val$optionals;
        super();
    }
    
    @Override
    public Iterator<T> iterator() {
        return new AbstractIterator<T>() {
            private final Iterator<? extends Optional<? extends T>> iterator = Preconditions.checkNotNull(this.this$0.val$optionals.iterator());
            final /* synthetic */ Optional$1 this$0;
            
            Optional$1$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            protected T computeNext() {
                while (this.iterator.hasNext()) {
                    final Optional<? extends T> optional = (Optional<? extends T>)this.iterator.next();
                    if (optional.isPresent()) {
                        return (T)optional.get();
                    }
                }
                return this.endOfData();
            }
        };
    }
}