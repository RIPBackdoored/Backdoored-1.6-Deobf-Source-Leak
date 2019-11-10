package com.google.api.client.util;

import java.util.*;

public class GenericData extends AbstractMap<String, Object> implements Cloneable
{
    Map<String, Object> unknownFields;
    final ClassInfo classInfo;
    
    public GenericData() {
        this(EnumSet.noneOf(Flags.class));
    }
    
    public GenericData(final EnumSet<Flags> flags) {
        super();
        this.unknownFields = (Map<String, Object>)ArrayMap.create();
        this.classInfo = ClassInfo.of(this.getClass(), flags.contains(Flags.IGNORE_CASE));
    }
    
    @Override
    public final Object get(final Object name) {
        if (!(name instanceof String)) {
            return null;
        }
        String fieldName = (String)name;
        final FieldInfo fieldInfo = this.classInfo.getFieldInfo(fieldName);
        if (fieldInfo != null) {
            return fieldInfo.getValue(this);
        }
        if (this.classInfo.getIgnoreCase()) {
            fieldName = fieldName.toLowerCase(Locale.US);
        }
        return this.unknownFields.get(fieldName);
    }
    
    @Override
    public final Object put(String fieldName, final Object value) {
        final FieldInfo fieldInfo = this.classInfo.getFieldInfo(fieldName);
        if (fieldInfo != null) {
            final Object oldValue = fieldInfo.getValue(this);
            fieldInfo.setValue(this, value);
            return oldValue;
        }
        if (this.classInfo.getIgnoreCase()) {
            fieldName = fieldName.toLowerCase(Locale.US);
        }
        return this.unknownFields.put(fieldName, value);
    }
    
    public GenericData set(String fieldName, final Object value) {
        final FieldInfo fieldInfo = this.classInfo.getFieldInfo(fieldName);
        if (fieldInfo != null) {
            fieldInfo.setValue(this, value);
        }
        else {
            if (this.classInfo.getIgnoreCase()) {
                fieldName = fieldName.toLowerCase(Locale.US);
            }
            this.unknownFields.put(fieldName, value);
        }
        return this;
    }
    
    @Override
    public final void putAll(final Map<? extends String, ?> map) {
        for (final Map.Entry<? extends String, ?> entry : map.entrySet()) {
            this.set((String)entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public final Object remove(final Object name) {
        if (!(name instanceof String)) {
            return null;
        }
        String fieldName = (String)name;
        final FieldInfo fieldInfo = this.classInfo.getFieldInfo(fieldName);
        if (fieldInfo != null) {
            throw new UnsupportedOperationException();
        }
        if (this.classInfo.getIgnoreCase()) {
            fieldName = fieldName.toLowerCase(Locale.US);
        }
        return this.unknownFields.remove(fieldName);
    }
    
    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return new EntrySet();
    }
    
    public GenericData clone() {
        try {
            final GenericData result = (GenericData)super.clone();
            Data.deepCopy(this, result);
            result.unknownFields = Data.clone(this.unknownFields);
            return result;
        }
        catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
    
    public final Map<String, Object> getUnknownKeys() {
        return this.unknownFields;
    }
    
    public final void setUnknownKeys(final Map<String, Object> unknownFields) {
        this.unknownFields = unknownFields;
    }
    
    public final ClassInfo getClassInfo() {
        return this.classInfo;
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    @Override
    public /* bridge */ Object put(final Object o, final Object value) {
        return this.put((String)o, value);
    }
    
    public enum Flags
    {
        IGNORE_CASE;
        
        private static final /* synthetic */ Flags[] $VALUES;
        
        public static Flags[] values() {
            return Flags.$VALUES.clone();
        }
        
        public static Flags valueOf(final String name) {
            return Enum.valueOf(Flags.class, name);
        }
        
        static {
            $VALUES = new Flags[] { Flags.IGNORE_CASE };
        }
    }
    
    final class EntrySet extends AbstractSet<Map.Entry<String, Object>>
    {
        private final DataMap.EntrySet dataEntrySet;
        final /* synthetic */ GenericData this$0;
        
        EntrySet(final GenericData this$0) {
            this.this$0 = this$0;
            super();
            this.dataEntrySet = new DataMap(this$0, this$0.classInfo.getIgnoreCase()).entrySet();
        }
        
        @Override
        public Iterator<Map.Entry<String, Object>> iterator() {
            return this.this$0.new EntryIterator(this.dataEntrySet);
        }
        
        @Override
        public int size() {
            return this.this$0.unknownFields.size() + this.dataEntrySet.size();
        }
        
        @Override
        public void clear() {
            this.this$0.unknownFields.clear();
            this.dataEntrySet.clear();
        }
    }
    
    final class EntryIterator implements Iterator<Map.Entry<String, Object>>
    {
        private boolean startedUnknown;
        private final Iterator<Map.Entry<String, Object>> fieldIterator;
        private final Iterator<Map.Entry<String, Object>> unknownIterator;
        final /* synthetic */ GenericData this$0;
        
        EntryIterator(final GenericData this$0, final DataMap.EntrySet dataEntrySet) {
            this.this$0 = this$0;
            super();
            this.fieldIterator = dataEntrySet.iterator();
            this.unknownIterator = this$0.unknownFields.entrySet().iterator();
        }
        
        @Override
        public boolean hasNext() {
            return this.fieldIterator.hasNext() || this.unknownIterator.hasNext();
        }
        
        @Override
        public Map.Entry<String, Object> next() {
            if (!this.startedUnknown) {
                if (this.fieldIterator.hasNext()) {
                    return this.fieldIterator.next();
                }
                this.startedUnknown = true;
            }
            return this.unknownIterator.next();
        }
        
        @Override
        public void remove() {
            if (this.startedUnknown) {
                this.unknownIterator.remove();
            }
            this.fieldIterator.remove();
        }
        
        @Override
        public /* bridge */ Object next() {
            return this.next();
        }
    }
}
