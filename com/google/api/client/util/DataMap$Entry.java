package com.google.api.client.util;

import java.util.*;

final class Entry implements Map.Entry<String, Object>
{
    private Object fieldValue;
    private final FieldInfo fieldInfo;
    final /* synthetic */ DataMap this$0;
    
    Entry(final DataMap this$0, final FieldInfo fieldInfo, final Object fieldValue) {
        this.this$0 = this$0;
        super();
        this.fieldInfo = fieldInfo;
        this.fieldValue = Preconditions.checkNotNull(fieldValue);
    }
    
    @Override
    public String getKey() {
        String result = this.fieldInfo.getName();
        if (this.this$0.classInfo.getIgnoreCase()) {
            result = result.toLowerCase(Locale.US);
        }
        return result;
    }
    
    @Override
    public Object getValue() {
        return this.fieldValue;
    }
    
    @Override
    public Object setValue(final Object value) {
        final Object oldValue = this.fieldValue;
        this.fieldValue = Preconditions.checkNotNull(value);
        this.fieldInfo.setValue(this.this$0.object, value);
        return oldValue;
    }
    
    @Override
    public int hashCode() {
        return this.getKey().hashCode() ^ this.getValue().hashCode();
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
        return this.getKey().equals(other.getKey()) && this.getValue().equals(other.getValue());
    }
    
    @Override
    public /* bridge */ Object getKey() {
        return this.getKey();
    }
}
