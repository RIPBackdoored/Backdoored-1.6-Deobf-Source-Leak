package com.google.api.client.util;

import java.util.*;

final class EntryIterator implements Iterator<Map.Entry<K, V>>
{
    private boolean removed;
    private int nextIndex;
    final /* synthetic */ ArrayMap this$0;
    
    EntryIterator(final ArrayMap this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public boolean hasNext() {
        return this.nextIndex < this.this$0.size;
    }
    
    @Override
    public Map.Entry<K, V> next() {
        final int index = this.nextIndex;
        if (index == this.this$0.size) {
            throw new NoSuchElementException();
        }
        ++this.nextIndex;
        return this.this$0.new Entry(index);
    }
    
    @Override
    public void remove() {
        final int index = this.nextIndex - 1;
        if (this.removed || index < 0) {
            throw new IllegalArgumentException();
        }
        this.this$0.remove(index);
        this.removed = true;
    }
    
    @Override
    public /* bridge */ Object next() {
        return this.next();
    }
}
