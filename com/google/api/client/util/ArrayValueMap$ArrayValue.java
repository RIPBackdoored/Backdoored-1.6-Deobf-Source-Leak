package com.google.api.client.util;

import java.util.*;

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
