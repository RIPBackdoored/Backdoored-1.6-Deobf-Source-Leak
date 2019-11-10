package com.google.api.client.util;

import java.util.*;

final class EntrySet extends AbstractSet<Map.Entry<String, Object>>
{
    final /* synthetic */ DataMap this$0;
    
    EntrySet(final DataMap this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public EntryIterator iterator() {
        return this.this$0.new EntryIterator();
    }
    
    @Override
    public int size() {
        int result = 0;
        for (final String name : this.this$0.classInfo.names) {
            if (this.this$0.classInfo.getFieldInfo(name).getValue(this.this$0.object) != null) {
                ++result;
            }
        }
        return result;
    }
    
    @Override
    public void clear() {
        for (final String name : this.this$0.classInfo.names) {
            this.this$0.classInfo.getFieldInfo(name).setValue(this.this$0.object, null);
        }
    }
    
    @Override
    public boolean isEmpty() {
        for (final String name : this.this$0.classInfo.names) {
            if (this.this$0.classInfo.getFieldInfo(name).getValue(this.this$0.object) != null) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public /* bridge */ Iterator iterator() {
        return this.iterator();
    }
}
