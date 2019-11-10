package com.google.api.client.json;

import java.io.*;
import java.math.*;
import com.google.api.client.util.*;
import java.util.*;
import java.lang.reflect.*;

public abstract class JsonGenerator
{
    public JsonGenerator() {
        super();
    }
    
    public abstract JsonFactory getFactory();
    
    public abstract void flush() throws IOException;
    
    public abstract void close() throws IOException;
    
    public abstract void writeStartArray() throws IOException;
    
    public abstract void writeEndArray() throws IOException;
    
    public abstract void writeStartObject() throws IOException;
    
    public abstract void writeEndObject() throws IOException;
    
    public abstract void writeFieldName(final String p0) throws IOException;
    
    public abstract void writeNull() throws IOException;
    
    public abstract void writeString(final String p0) throws IOException;
    
    public abstract void writeBoolean(final boolean p0) throws IOException;
    
    public abstract void writeNumber(final int p0) throws IOException;
    
    public abstract void writeNumber(final long p0) throws IOException;
    
    public abstract void writeNumber(final BigInteger p0) throws IOException;
    
    public abstract void writeNumber(final float p0) throws IOException;
    
    public abstract void writeNumber(final double p0) throws IOException;
    
    public abstract void writeNumber(final BigDecimal p0) throws IOException;
    
    public abstract void writeNumber(final String p0) throws IOException;
    
    public final void serialize(final Object value) throws IOException {
        this.serialize(false, value);
    }
    
    private void serialize(final boolean isJsonString, final Object value) throws IOException {
        if (value == null) {
            return;
        }
        final Class<?> valueClass = value.getClass();
        if (Data.isNull(value)) {
            this.writeNull();
        }
        else if (value instanceof String) {
            this.writeString((String)value);
        }
        else if (value instanceof Number) {
            if (isJsonString) {
                this.writeString(value.toString());
            }
            else if (value instanceof BigDecimal) {
                this.writeNumber((BigDecimal)value);
            }
            else if (value instanceof BigInteger) {
                this.writeNumber((BigInteger)value);
            }
            else if (value instanceof Long) {
                this.writeNumber((long)value);
            }
            else if (value instanceof Float) {
                final float floatValue = ((Number)value).floatValue();
                Preconditions.checkArgument(!Float.isInfinite(floatValue) && !Float.isNaN(floatValue));
                this.writeNumber(floatValue);
            }
            else if (value instanceof Integer || value instanceof Short || value instanceof Byte) {
                this.writeNumber(((Number)value).intValue());
            }
            else {
                final double doubleValue = ((Number)value).doubleValue();
                Preconditions.checkArgument(!Double.isInfinite(doubleValue) && !Double.isNaN(doubleValue));
                this.writeNumber(doubleValue);
            }
        }
        else if (value instanceof Boolean) {
            this.writeBoolean((boolean)value);
        }
        else if (value instanceof DateTime) {
            this.writeString(((DateTime)value).toStringRfc3339());
        }
        else if (value instanceof Iterable || valueClass.isArray()) {
            this.writeStartArray();
            for (final Object o : Types.iterableOf(value)) {
                this.serialize(isJsonString, o);
            }
            this.writeEndArray();
        }
        else if (valueClass.isEnum()) {
            final String name = FieldInfo.of((Enum<?>)value).getName();
            if (name == null) {
                this.writeNull();
            }
            else {
                this.writeString(name);
            }
        }
        else {
            this.writeStartObject();
            final boolean isMapNotGenericData = value instanceof Map && !(value instanceof GenericData);
            final ClassInfo classInfo = isMapNotGenericData ? null : ClassInfo.of(valueClass);
            for (final Map.Entry<String, Object> entry : Data.mapOf(value).entrySet()) {
                final Object fieldValue = entry.getValue();
                if (fieldValue != null) {
                    final String fieldName = entry.getKey();
                    boolean isJsonStringForField;
                    if (isMapNotGenericData) {
                        isJsonStringForField = isJsonString;
                    }
                    else {
                        final Field field = classInfo.getField(fieldName);
                        isJsonStringForField = (field != null && field.getAnnotation(JsonString.class) != null);
                    }
                    this.writeFieldName(fieldName);
                    this.serialize(isJsonStringForField, fieldValue);
                }
            }
            this.writeEndObject();
        }
    }
    
    public void enablePrettyPrint() throws IOException {
    }
}
