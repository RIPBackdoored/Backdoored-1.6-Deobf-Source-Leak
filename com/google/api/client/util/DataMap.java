package com.google.api.client.util;

import java.util.*;

final class DataMap extends AbstractMap<String, Object>
{
    final Object object;
    final ClassInfo classInfo;
    
    DataMap(final Object object, final boolean ignoreCase) {
        super();
        this.object = object;
        this.classInfo = ClassInfo.of(object.getClass(), ignoreCase);
        Preconditions.checkArgument(!this.classInfo.isEnum());
    }
    
    @Override
    public EntrySet entrySet() {
        return new EntrySet();
    }
    
    @Override
    public boolean containsKey(final Object key) {
        return this.get(key) != null;
    }
    
    @Override
    public Object get(final Object key) {
        if (!(key instanceof String)) {
            return null;
        }
        final FieldInfo fieldInfo = this.classInfo.getFieldInfo((String)key);
        if (fieldInfo == null) {
            return null;
        }
        return fieldInfo.getValue(this.object);
    }
    
    @Override
    public Object put(final String key, final Object value) {
        final FieldInfo fieldInfo = this.classInfo.getFieldInfo(key);
        Preconditions.checkNotNull(fieldInfo, (Object)("no field of key " + key));
        final Object oldValue = fieldInfo.getValue(this.object);
        fieldInfo.setValue(this.object, Preconditions.checkNotNull(value));
        return oldValue;
    }
    
    @Override
    public /* bridge */ Set entrySet() {
        return this.entrySet();
    }
    
    @Override
    public /* bridge */ Object put(final Object o, final Object value) {
        return this.put((String)o, value);
    }
    
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
    
    final class EntryIterator implements Iterator<Map.Entry<String, Object>>
    {
        private int nextKeyIndex;
        private FieldInfo nextFieldInfo;
        private Object nextFieldValue;
        private boolean isRemoved;
        private boolean isComputed;
        private FieldInfo currentFieldInfo;
        final /* synthetic */ DataMap this$0;
        
        EntryIterator(final DataMap this$0) {
            this.this$0 = this$0;
            super();
            this.nextKeyIndex = -1;
        }
        
        @Override
        public boolean hasNext() {
            if (!this.isComputed) {
                this.isComputed = true;
                this.nextFieldValue = null;
                while (this.nextFieldValue == null && ++this.nextKeyIndex < this.this$0.classInfo.names.size()) {
                    this.nextFieldInfo = this.this$0.classInfo.getFieldInfo(this.this$0.classInfo.names.get(this.nextKeyIndex));
                    this.nextFieldValue = this.nextFieldInfo.getValue(this.this$0.object);
                }
            }
            return this.nextFieldValue != null;
        }
        
        @Override
        public Map.Entry<String, Object> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.currentFieldInfo = this.nextFieldInfo;
            final Object currentFieldValue = this.nextFieldValue;
            this.isComputed = false;
            this.isRemoved = false;
            this.nextFieldInfo = null;
            this.nextFieldValue = null;
            return this.this$0.new Entry(this.currentFieldInfo, currentFieldValue);
        }
        
        @Override
        public void remove() {
            Preconditions.checkState(this.currentFieldInfo != null && !this.isRemoved);
            this.isRemoved = true;
            this.currentFieldInfo.setValue(this.this$0.object, null);
        }
        
        @Override
        public /* bridge */ Object next() {
            return this.next();
        }
    }
    
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
}
