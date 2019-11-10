package org.reflections;

import java.util.*;
import com.google.common.collect.*;

private static class IterableChain<T> implements Iterable<T>
{
    private final List<Iterable<T>> chain;
    
    private IterableChain() {
        super();
        this.chain = (List<Iterable<T>>)Lists.newArrayList();
    }
    
    private void addAll(final Iterable<T> iterable) {
        this.chain.add(iterable);
    }
    
    @Override
    public Iterator<T> iterator() {
        return Iterables.concat((Iterable<? extends Iterable<? extends T>>)this.chain).iterator();
    }
    
    IterableChain(final Store$1 x0) {
        this();
    }
    
    static /* synthetic */ void access$100(final IterableChain x0, final Iterable x1) {
        x0.addAll(x1);
    }
}
