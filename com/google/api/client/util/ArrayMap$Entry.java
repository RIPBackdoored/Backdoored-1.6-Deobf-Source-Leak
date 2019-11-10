package com.google.api.client.util;

import java.util.*;

final class Entry implements Map.Entry<K, V>
{
    private int index;
    final /* synthetic */ ArrayMap this$0;
    
    Entry(final ArrayMap this$0, final int index) {
        this.this$0 = this$0;
        super();
        this.index = index;
    }
    
    @Override
    public K getKey() {
        return this.this$0.getKey(this.index);
    }
    
    @Override
    public V getValue() {
        return this.this$0.getValue(this.index);
    }
    
    @Override
    public V setValue(final V value) {
        return this.this$0.set(this.index, value);
    }
    
    @Override
    public int hashCode() {
        final K key = this.getKey();
        final V value = this.getValue();
        return ((key != null) ? key.hashCode() : 0) ^ ((value != null) ? value.hashCode() : 0);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        final Map.Entry<?, ?> other = (Map.Entry<?, ?>)obj;
        return Objects.equal(this.getKey(), other.getKey()) && Objects.equal(this.getValue(), other.getValue());
    }
}
