package com.google.api.client.util;

import java.lang.reflect.*;
import java.util.*;

public final class ArrayValueMap
{
    private final Map<String, ArrayValue> keyMap;
    private final Map<Field, ArrayValue> fieldMap;
    private final Object destination;
    
    public ArrayValueMap(final Object destination) {
        super();
        this.keyMap = (Map<String, ArrayValue>)ArrayMap.create();
        this.fieldMap = (Map<Field, ArrayValue>)ArrayMap.create();
        this.destination = destination;
    }
    
    public void setValues() {
        for (final Map.Entry<String, ArrayValue> entry : this.keyMap.entrySet()) {
            final Map<String, Object> destinationMap = (Map<String, Object>)this.destination;
            destinationMap.put(entry.getKey(), entry.getValue().toArray());
        }
        for (final Map.Entry<Field, ArrayValue> entry2 : this.fieldMap.entrySet()) {
            FieldInfo.setFieldValue(entry2.getKey(), this.destination, entry2.getValue().toArray());
        }
    }
    
    public void put(final Field field, final Class<?> arrayComponentType, final Object value) {
        ArrayValue arrayValue = this.fieldMap.get(field);
        if (arrayValue == null) {
            arrayValue = new ArrayValue(arrayComponentType);
            this.fieldMap.put(field, arrayValue);
        }
        arrayValue.addValue(arrayComponentType, value);
    }
    
    public void put(final String keyName, final Class<?> arrayComponentType, final Object value) {
        ArrayValue arrayValue = this.keyMap.get(keyName);
        if (arrayValue == null) {
            arrayValue = new ArrayValue(arrayComponentType);
            this.keyMap.put(keyName, arrayValue);
        }
        arrayValue.addValue(arrayComponentType, value);
    }
    
    static class ArrayValue
    {
        final Class<?> componentType;
        final ArrayList<Object> values;
        
        ArrayValue(final Class<?> componentType) {
            super();
            this.values = new ArrayList<Object>();
            this.componentType = componentType;
        }
        
        Object toArray() {
            return Types.toArray(this.values, this.componentType);
        }
        
        void addValue(final Class<?> componentType, final Object value) {
            Preconditions.checkArgument(componentType == this.componentType);
            this.values.add(value);
        }
    }
}
