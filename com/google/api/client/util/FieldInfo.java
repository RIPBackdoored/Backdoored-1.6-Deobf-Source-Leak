package com.google.api.client.util;

import java.lang.reflect.*;
import java.util.*;

public class FieldInfo
{
    private static final Map<Field, FieldInfo> CACHE;
    private final boolean isPrimitive;
    private final Field field;
    private final String name;
    
    public static FieldInfo of(final Enum<?> enumValue) {
        try {
            final FieldInfo result = of(enumValue.getClass().getField(enumValue.name()));
            Preconditions.checkArgument(result != null, "enum constant missing @Value or @NullValue annotation: %s", enumValue);
            return result;
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static FieldInfo of(final Field field) {
        if (field == null) {
            return null;
        }
        synchronized (FieldInfo.CACHE) {
            FieldInfo fieldInfo = FieldInfo.CACHE.get(field);
            final boolean isEnumContant = field.isEnumConstant();
            if (fieldInfo == null && (isEnumContant || !Modifier.isStatic(field.getModifiers()))) {
                String fieldName;
                if (isEnumContant) {
                    final Value value = field.getAnnotation(Value.class);
                    if (value != null) {
                        fieldName = value.value();
                    }
                    else {
                        final NullValue nullValue = field.getAnnotation(NullValue.class);
                        if (nullValue == null) {
                            return null;
                        }
                        fieldName = null;
                    }
                }
                else {
                    final Key key = field.getAnnotation(Key.class);
                    if (key == null) {
                        return null;
                    }
                    fieldName = key.value();
                    field.setAccessible(true);
                }
                if ("##default".equals(fieldName)) {
                    fieldName = field.getName();
                }
                fieldInfo = new FieldInfo(field, fieldName);
                FieldInfo.CACHE.put(field, fieldInfo);
            }
            return fieldInfo;
        }
    }
    
    FieldInfo(final Field field, final String name) {
        super();
        this.field = field;
        this.name = ((name == null) ? null : name.intern());
        this.isPrimitive = Data.isPrimitive(this.getType());
    }
    
    public Field getField() {
        return this.field;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Class<?> getType() {
        return this.field.getType();
    }
    
    public Type getGenericType() {
        return this.field.getGenericType();
    }
    
    public boolean isFinal() {
        return Modifier.isFinal(this.field.getModifiers());
    }
    
    public boolean isPrimitive() {
        return this.isPrimitive;
    }
    
    public Object getValue(final Object obj) {
        return getFieldValue(this.field, obj);
    }
    
    public void setValue(final Object obj, final Object value) {
        setFieldValue(this.field, obj, value);
    }
    
    public ClassInfo getClassInfo() {
        return ClassInfo.of(this.field.getDeclaringClass());
    }
    
    public <T extends Enum<T>> T enumValue() {
        return Enum.valueOf(this.field.getDeclaringClass(), this.field.getName());
    }
    
    public static Object getFieldValue(final Field field, final Object obj) {
        try {
            return field.get(obj);
        }
        catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    public static void setFieldValue(final Field field, final Object obj, final Object value) {
        if (Modifier.isFinal(field.getModifiers())) {
            final Object finalValue = getFieldValue(field, obj);
            if (value == null) {
                if (finalValue == null) {
                    return;
                }
            }
            else if (value.equals(finalValue)) {
                return;
            }
            throw new IllegalArgumentException("expected final value <" + finalValue + "> but was <" + value + "> on " + field.getName() + " field in " + obj.getClass().getName());
        }
        try {
            field.set(obj, value);
        }
        catch (SecurityException e) {
            throw new IllegalArgumentException(e);
        }
        catch (IllegalAccessException e2) {
            throw new IllegalArgumentException(e2);
        }
    }
    
    static {
        CACHE = new WeakHashMap<Field, FieldInfo>();
    }
}
