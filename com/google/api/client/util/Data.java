package com.google.api.client.util;

import java.math.*;
import java.util.concurrent.*;
import java.util.*;
import java.lang.reflect.*;

public class Data
{
    public static final Boolean NULL_BOOLEAN;
    public static final String NULL_STRING;
    public static final Character NULL_CHARACTER;
    public static final Byte NULL_BYTE;
    public static final Short NULL_SHORT;
    public static final Integer NULL_INTEGER;
    public static final Float NULL_FLOAT;
    public static final Long NULL_LONG;
    public static final Double NULL_DOUBLE;
    public static final BigInteger NULL_BIG_INTEGER;
    public static final BigDecimal NULL_BIG_DECIMAL;
    public static final DateTime NULL_DATE_TIME;
    private static final ConcurrentHashMap<Class<?>, Object> NULL_CACHE;
    
    public Data() {
        super();
    }
    
    public static <T> T nullOf(final Class<?> objClass) {
        Object result = Data.NULL_CACHE.get(objClass);
        if (result == null) {
            synchronized (Data.NULL_CACHE) {
                result = Data.NULL_CACHE.get(objClass);
                if (result == null) {
                    if (objClass.isArray()) {
                        int dims = 0;
                        Class<?> componentType = objClass;
                        do {
                            componentType = componentType.getComponentType();
                            ++dims;
                        } while (componentType.isArray());
                        result = Array.newInstance(componentType, new int[dims]);
                    }
                    else if (objClass.isEnum()) {
                        final FieldInfo fieldInfo = ClassInfo.of(objClass).getFieldInfo(null);
                        Preconditions.checkNotNull(fieldInfo, "enum missing constant with @NullValue annotation: %s", objClass);
                        final Enum e = (Enum)(result = fieldInfo.enumValue());
                    }
                    else {
                        result = Types.newInstance(objClass);
                    }
                    Data.NULL_CACHE.put(objClass, result);
                }
            }
        }
        final T tResult = (T)result;
        return tResult;
    }
    
    public static boolean isNull(final Object object) {
        return object != null && object == Data.NULL_CACHE.get(object.getClass());
    }
    
    public static Map<String, Object> mapOf(final Object data) {
        if (data == null || isNull(data)) {
            return Collections.emptyMap();
        }
        if (data instanceof Map) {
            final Map<String, Object> result = (Map<String, Object>)data;
            return result;
        }
        final Map<String, Object> result = new DataMap(data, false);
        return result;
    }
    
    public static <T> T clone(final T data) {
        if (data == null || isPrimitive(data.getClass())) {
            return data;
        }
        if (data instanceof GenericData) {
            return (T)((GenericData)data).clone();
        }
        final Class<?> dataClass = data.getClass();
        T copy;
        if (dataClass.isArray()) {
            copy = (T)Array.newInstance(dataClass.getComponentType(), Array.getLength(data));
        }
        else if (data instanceof ArrayMap) {
            copy = (T)((ArrayMap)data).clone();
        }
        else {
            if ("java.util.Arrays$ArrayList".equals(dataClass.getName())) {
                final Object[] arrayCopy = ((List)data).toArray();
                deepCopy(arrayCopy, arrayCopy);
                copy = (T)Arrays.asList(arrayCopy);
                return copy;
            }
            copy = Types.newInstance(dataClass);
        }
        deepCopy(data, copy);
        return copy;
    }
    
    public static void deepCopy(final Object src, final Object dest) {
        final Class<?> srcClass = src.getClass();
        Preconditions.checkArgument(srcClass == dest.getClass());
        if (srcClass.isArray()) {
            Preconditions.checkArgument(Array.getLength(src) == Array.getLength(dest));
            int index = 0;
            for (final Object value : Types.iterableOf(src)) {
                Array.set(dest, index++, clone(value));
            }
        }
        else if (Collection.class.isAssignableFrom(srcClass)) {
            final Collection<Object> srcCollection = (Collection<Object>)src;
            if (ArrayList.class.isAssignableFrom(srcClass)) {
                final ArrayList<Object> destArrayList = (ArrayList<Object>)dest;
                destArrayList.ensureCapacity(srcCollection.size());
            }
            final Collection<Object> destCollection = (Collection<Object>)dest;
            for (final Object srcValue : srcCollection) {
                destCollection.add(clone(srcValue));
            }
        }
        else {
            final boolean isGenericData = GenericData.class.isAssignableFrom(srcClass);
            if (isGenericData || !Map.class.isAssignableFrom(srcClass)) {
                final ClassInfo classInfo = isGenericData ? ((GenericData)src).classInfo : ClassInfo.of(srcClass);
                for (final String fieldName : classInfo.names) {
                    final FieldInfo fieldInfo = classInfo.getFieldInfo(fieldName);
                    if (!fieldInfo.isFinal() && (!isGenericData || !fieldInfo.isPrimitive())) {
                        final Object srcValue2 = fieldInfo.getValue(src);
                        if (srcValue2 == null) {
                            continue;
                        }
                        fieldInfo.setValue(dest, clone(srcValue2));
                    }
                }
            }
            else if (ArrayMap.class.isAssignableFrom(srcClass)) {
                final ArrayMap<Object, Object> destMap = (ArrayMap<Object, Object>)dest;
                final ArrayMap<Object, Object> srcMap = (ArrayMap<Object, Object>)src;
                for (int size = srcMap.size(), i = 0; i < size; ++i) {
                    final Object srcValue2 = srcMap.getValue(i);
                    destMap.set(i, clone(srcValue2));
                }
            }
            else {
                final Map<String, Object> destMap2 = (Map<String, Object>)dest;
                final Map<String, Object> srcMap2 = (Map<String, Object>)src;
                for (final Map.Entry<String, Object> srcEntry : srcMap2.entrySet()) {
                    destMap2.put(srcEntry.getKey(), clone(srcEntry.getValue()));
                }
            }
        }
    }
    
    public static boolean isPrimitive(Type type) {
        if (type instanceof WildcardType) {
            type = Types.getBound((WildcardType)type);
        }
        if (!(type instanceof Class)) {
            return false;
        }
        final Class<?> typeClass = (Class<?>)type;
        return typeClass.isPrimitive() || typeClass == Character.class || typeClass == String.class || typeClass == Integer.class || typeClass == Long.class || typeClass == Short.class || typeClass == Byte.class || typeClass == Float.class || typeClass == Double.class || typeClass == BigInteger.class || typeClass == BigDecimal.class || typeClass == DateTime.class || typeClass == Boolean.class;
    }
    
    public static boolean isValueOfPrimitiveType(final Object fieldValue) {
        return fieldValue == null || isPrimitive(fieldValue.getClass());
    }
    
    public static Object parsePrimitiveValue(final Type type, final String stringValue) {
        final Class<?> primitiveClass = (Class<?>)((type instanceof Class) ? ((Class)type) : null);
        if (type == null || primitiveClass != null) {
            if (primitiveClass == Void.class) {
                return null;
            }
            if (stringValue == null || primitiveClass == null || primitiveClass.isAssignableFrom(String.class)) {
                return stringValue;
            }
            if (primitiveClass == Character.class || primitiveClass == Character.TYPE) {
                if (stringValue.length() != 1) {
                    throw new IllegalArgumentException("expected type Character/char but got " + primitiveClass);
                }
                return stringValue.charAt(0);
            }
            else {
                if (primitiveClass == Boolean.class || primitiveClass == Boolean.TYPE) {
                    return Boolean.valueOf(stringValue);
                }
                if (primitiveClass == Byte.class || primitiveClass == Byte.TYPE) {
                    return Byte.valueOf(stringValue);
                }
                if (primitiveClass == Short.class || primitiveClass == Short.TYPE) {
                    return Short.valueOf(stringValue);
                }
                if (primitiveClass == Integer.class || primitiveClass == Integer.TYPE) {
                    return Integer.valueOf(stringValue);
                }
                if (primitiveClass == Long.class || primitiveClass == Long.TYPE) {
                    return Long.valueOf(stringValue);
                }
                if (primitiveClass == Float.class || primitiveClass == Float.TYPE) {
                    return Float.valueOf(stringValue);
                }
                if (primitiveClass == Double.class || primitiveClass == Double.TYPE) {
                    return Double.valueOf(stringValue);
                }
                if (primitiveClass == DateTime.class) {
                    return DateTime.parseRfc3339(stringValue);
                }
                if (primitiveClass == BigInteger.class) {
                    return new BigInteger(stringValue);
                }
                if (primitiveClass == BigDecimal.class) {
                    return new BigDecimal(stringValue);
                }
                if (primitiveClass.isEnum()) {
                    final Enum result = ClassInfo.of(primitiveClass).getFieldInfo(stringValue).enumValue();
                    return result;
                }
            }
        }
        throw new IllegalArgumentException("expected primitive class, but got: " + type);
    }
    
    public static Collection<Object> newCollectionInstance(Type type) {
        if (type instanceof WildcardType) {
            type = Types.getBound((WildcardType)type);
        }
        if (type instanceof ParameterizedType) {
            type = ((ParameterizedType)type).getRawType();
        }
        final Class<?> collectionClass = (Class<?>)((type instanceof Class) ? ((Class)type) : null);
        if (type == null || type instanceof GenericArrayType || (collectionClass != null && (collectionClass.isArray() || collectionClass.isAssignableFrom(ArrayList.class)))) {
            return new ArrayList<Object>();
        }
        if (collectionClass == null) {
            throw new IllegalArgumentException("unable to create new instance of type: " + type);
        }
        if (collectionClass.isAssignableFrom(HashSet.class)) {
            return new HashSet<Object>();
        }
        if (collectionClass.isAssignableFrom(TreeSet.class)) {
            return new TreeSet<Object>();
        }
        final Collection<Object> result = Types.newInstance(collectionClass);
        return result;
    }
    
    public static Map<String, Object> newMapInstance(final Class<?> mapClass) {
        if (mapClass == null || mapClass.isAssignableFrom(ArrayMap.class)) {
            return (Map<String, Object>)ArrayMap.create();
        }
        if (mapClass.isAssignableFrom(TreeMap.class)) {
            return new TreeMap<String, Object>();
        }
        final Map<String, Object> result = Types.newInstance(mapClass);
        return result;
    }
    
    public static Type resolveWildcardTypeOrTypeVariable(final List<Type> context, Type type) {
        if (type instanceof WildcardType) {
            type = Types.getBound((WildcardType)type);
        }
        while (type instanceof TypeVariable) {
            final Type resolved = Types.resolveTypeVariable(context, (TypeVariable<?>)type);
            if (resolved != null) {
                type = resolved;
            }
            if (type instanceof TypeVariable) {
                type = ((TypeVariable)type).getBounds()[0];
            }
        }
        return type;
    }
    
    static {
        NULL_BOOLEAN = new Boolean(true);
        NULL_STRING = new String();
        NULL_CHARACTER = new Character('\0');
        NULL_BYTE = new Byte((byte)0);
        NULL_SHORT = new Short((short)0);
        NULL_INTEGER = new Integer(0);
        NULL_FLOAT = new Float(0.0f);
        NULL_LONG = new Long(0L);
        NULL_DOUBLE = new Double(0.0);
        NULL_BIG_INTEGER = new BigInteger("0");
        NULL_BIG_DECIMAL = new BigDecimal("0");
        NULL_DATE_TIME = new DateTime(0L);
        (NULL_CACHE = new ConcurrentHashMap<Class<?>, Object>()).put(Boolean.class, Data.NULL_BOOLEAN);
        Data.NULL_CACHE.put(String.class, Data.NULL_STRING);
        Data.NULL_CACHE.put(Character.class, Data.NULL_CHARACTER);
        Data.NULL_CACHE.put(Byte.class, Data.NULL_BYTE);
        Data.NULL_CACHE.put(Short.class, Data.NULL_SHORT);
        Data.NULL_CACHE.put(Integer.class, Data.NULL_INTEGER);
        Data.NULL_CACHE.put(Float.class, Data.NULL_FLOAT);
        Data.NULL_CACHE.put(Long.class, Data.NULL_LONG);
        Data.NULL_CACHE.put(Double.class, Data.NULL_DOUBLE);
        Data.NULL_CACHE.put(BigInteger.class, Data.NULL_BIG_INTEGER);
        Data.NULL_CACHE.put(BigDecimal.class, Data.NULL_BIG_DECIMAL);
        Data.NULL_CACHE.put(DateTime.class, Data.NULL_DATE_TIME);
    }
}
