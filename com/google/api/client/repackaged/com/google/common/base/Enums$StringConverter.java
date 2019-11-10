package com.google.api.client.repackaged.com.google.common.base;

import java.io.*;
import javax.annotation.*;

private static final class StringConverter<T extends Enum<T>> extends Converter<String, T> implements Serializable
{
    private final Class<T> enumClass;
    private static final long serialVersionUID = 0L;
    
    StringConverter(final Class<T> enumClass) {
        super();
        this.enumClass = Preconditions.checkNotNull(enumClass);
    }
    
    @Override
    protected T doForward(final String value) {
        return Enum.valueOf(this.enumClass, value);
    }
    
    @Override
    protected String doBackward(final T enumValue) {
        return enumValue.name();
    }
    
    @Override
    public boolean equals(@Nullable final Object object) {
        if (object instanceof StringConverter) {
            final StringConverter<?> that = (StringConverter<?>)object;
            return this.enumClass.equals(that.enumClass);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.enumClass.hashCode();
    }
    
    @Override
    public String toString() {
        return "Enums.stringConverter(" + this.enumClass.getName() + ".class)";
    }
    
    @Override
    protected /* bridge */ Object doBackward(final Object x0) {
        return this.doBackward((T)x0);
    }
    
    @Override
    protected /* bridge */ Object doForward(final Object x0) {
        return this.doForward((String)x0);
    }
}
