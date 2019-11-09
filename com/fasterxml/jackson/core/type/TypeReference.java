package com.fasterxml.jackson.core.type;

import java.lang.reflect.*;

public abstract class TypeReference<T> implements Comparable<TypeReference<T>>
{
    protected final Type _type;
    
    protected TypeReference() {
        super();
        final Type superClass = this.getClass().getGenericSuperclass();
        if (superClass instanceof Class) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        }
        this._type = ((ParameterizedType)superClass).getActualTypeArguments()[0];
    }
    
    public Type getType() {
        return this._type;
    }
    
    @Override
    public int compareTo(final TypeReference<T> o) {
        return 0;
    }
    
    @Override
    public /* bridge */ int compareTo(final Object x0) {
        return this.compareTo((TypeReference)x0);
    }
}
