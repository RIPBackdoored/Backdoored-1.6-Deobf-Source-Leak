package com.google.api.client.googleapis.auth.clientlogin;

import com.google.api.client.http.*;
import java.io.*;
import com.google.api.client.util.*;
import java.util.*;
import java.nio.charset.*;
import java.lang.reflect.*;

@Beta
final class AuthKeyValueParser implements ObjectParser
{
    public static final AuthKeyValueParser INSTANCE;
    
    public String getContentType() {
        return "text/plain";
    }
    
    public <T> T parse(final HttpResponse response, final Class<T> dataClass) throws IOException {
        response.setContentLoggingLimit(0);
        final InputStream content = response.getContent();
        try {
            return this.parse(content, dataClass);
        }
        finally {
            content.close();
        }
    }
    
    public <T> T parse(final InputStream content, final Class<T> dataClass) throws IOException {
        final ClassInfo classInfo = ClassInfo.of(dataClass);
        final T newInstance = Types.newInstance(dataClass);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        while (true) {
            final String line = reader.readLine();
            if (line == null) {
                break;
            }
            final int equals = line.indexOf(61);
            final String key = line.substring(0, equals);
            final String value = line.substring(equals + 1);
            final Field field = classInfo.getField(key);
            if (field != null) {
                final Class<?> fieldClass = field.getType();
                Object fieldValue;
                if (fieldClass == Boolean.TYPE || fieldClass == Boolean.class) {
                    fieldValue = Boolean.valueOf(value);
                }
                else {
                    fieldValue = value;
                }
                FieldInfo.setFieldValue(field, newInstance, fieldValue);
            }
            else if (GenericData.class.isAssignableFrom(dataClass)) {
                final GenericData data = (GenericData)newInstance;
                data.set(key, value);
            }
            else {
                if (!Map.class.isAssignableFrom(dataClass)) {
                    continue;
                }
                final Map<Object, Object> map = (Map<Object, Object>)newInstance;
                map.put(key, value);
            }
        }
        return newInstance;
    }
    
    private AuthKeyValueParser() {
        super();
    }
    
    public <T> T parseAndClose(final InputStream in, final Charset charset, final Class<T> dataClass) throws IOException {
        final Reader reader = new InputStreamReader(in, charset);
        return this.parseAndClose(reader, dataClass);
    }
    
    public Object parseAndClose(final InputStream in, final Charset charset, final Type dataType) {
        throw new UnsupportedOperationException("Type-based parsing is not yet supported -- use Class<T> instead");
    }
    
    public <T> T parseAndClose(final Reader reader, final Class<T> dataClass) throws IOException {
        try {
            final ClassInfo classInfo = ClassInfo.of(dataClass);
            final T newInstance = Types.newInstance(dataClass);
            final BufferedReader breader = new BufferedReader(reader);
            while (true) {
                final String line = breader.readLine();
                if (line == null) {
                    break;
                }
                final int equals = line.indexOf(61);
                final String key = line.substring(0, equals);
                final String value = line.substring(equals + 1);
                final Field field = classInfo.getField(key);
                if (field != null) {
                    final Class<?> fieldClass = field.getType();
                    Object fieldValue;
                    if (fieldClass == Boolean.TYPE || fieldClass == Boolean.class) {
                        fieldValue = Boolean.valueOf(value);
                    }
                    else {
                        fieldValue = value;
                    }
                    FieldInfo.setFieldValue(field, newInstance, fieldValue);
                }
                else if (GenericData.class.isAssignableFrom(dataClass)) {
                    final GenericData data = (GenericData)newInstance;
                    data.set(key, value);
                }
                else {
                    if (!Map.class.isAssignableFrom(dataClass)) {
                        continue;
                    }
                    final Map<Object, Object> map = (Map<Object, Object>)newInstance;
                    map.put(key, value);
                }
            }
            return newInstance;
        }
        finally {
            reader.close();
        }
    }
    
    public Object parseAndClose(final Reader reader, final Type dataType) {
        throw new UnsupportedOperationException("Type-based parsing is not yet supported -- use Class<T> instead");
    }
    
    static {
        INSTANCE = new AuthKeyValueParser();
    }
}
