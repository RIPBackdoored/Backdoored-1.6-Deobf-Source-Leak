package com.google.api.client.util;

import java.lang.reflect.*;
import java.util.*;

public class Types
{
    public static ParameterizedType getSuperParameterizedType(Type type, final Class<?> superClass) {
        if (type instanceof Class || type instanceof ParameterizedType) {
        Label_0014:
            while (type != null && type != Object.class) {
                Class<?> rawType;
                if (type instanceof Class) {
                    rawType = (Class<?>)type;
                }
                else {
                    final ParameterizedType parameterizedType = (ParameterizedType)type;
                    rawType = getRawClass(parameterizedType);
                    if (rawType == superClass) {
                        return parameterizedType;
                    }
                    if (superClass.isInterface()) {
                        for (final Type interfaceType : rawType.getGenericInterfaces()) {
                            final Class<?> interfaceClass = (Class<?>)((interfaceType instanceof Class) ? ((Class)interfaceType) : getRawClass((ParameterizedType)interfaceType));
                            if (superClass.isAssignableFrom(interfaceClass)) {
                                type = interfaceType;
                                continue Label_0014;
                            }
                        }
                    }
                }
                type = rawType.getGenericSuperclass();
            }
        }
        return null;
    }
    
    public static boolean isAssignableToOrFrom(final Class<?> classToCheck, final Class<?> anotherClass) {
        return classToCheck.isAssignableFrom(anotherClass) || anotherClass.isAssignableFrom(classToCheck);
    }
    
    public static <T> T newInstance(final Class<T> clazz) {
        try {
            return clazz.newInstance();
        }
        catch (IllegalAccessException e) {
            throw handleExceptionForNewInstance(e, clazz);
        }
        catch (InstantiationException e2) {
            throw handleExceptionForNewInstance(e2, clazz);
        }
    }
    
    private static IllegalArgumentException handleExceptionForNewInstance(final Exception e, final Class<?> clazz) {
        final StringBuilder buf = new StringBuilder("unable to create new instance of class ").append(clazz.getName());
        final ArrayList<String> reasons = new ArrayList<String>();
        if (clazz.isArray()) {
            reasons.add("because it is an array");
        }
        else if (clazz.isPrimitive()) {
            reasons.add("because it is primitive");
        }
        else if (clazz == Void.class) {
            reasons.add("because it is void");
        }
        else {
            if (Modifier.isInterface(clazz.getModifiers())) {
                reasons.add("because it is an interface");
            }
            else if (Modifier.isAbstract(clazz.getModifiers())) {
                reasons.add("because it is abstract");
            }
            if (clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers())) {
                reasons.add("because it is not static");
            }
            if (!Modifier.isPublic(clazz.getModifiers())) {
                reasons.add("possibly because it is not public");
            }
            else {
                try {
                    clazz.getConstructor((Class<?>[])new Class[0]);
                }
                catch (NoSuchMethodException e2) {
                    reasons.add("because it has no accessible default constructor");
                }
            }
        }
        boolean and = false;
        for (final String reason : reasons) {
            if (and) {
                buf.append(" and");
            }
            else {
                and = true;
            }
            buf.append(" ").append(reason);
        }
        return new IllegalArgumentException(buf.toString(), e);
    }
    
    public static boolean isArray(final Type type) {
        return type instanceof GenericArrayType || (type instanceof Class && ((Class)type).isArray());
    }
    
    public static Type getArrayComponentType(final Type array) {
        return (array instanceof GenericArrayType) ? ((GenericArrayType)array).getGenericComponentType() : ((Class)array).getComponentType();
    }
    
    public static Class<?> getRawClass(final ParameterizedType parameterType) {
        return (Class<?>)parameterType.getRawType();
    }
    
    public static Type getBound(final WildcardType wildcardType) {
        final Type[] lowerBounds = wildcardType.getLowerBounds();
        if (lowerBounds.length != 0) {
            return lowerBounds[0];
        }
        return wildcardType.getUpperBounds()[0];
    }
    
    public static Type resolveTypeVariable(final List<Type> context, final TypeVariable<?> typeVariable) {
        final GenericDeclaration genericDeclaration = (GenericDeclaration)typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            Class<?> rawGenericDeclaration;
            int contextIndex;
            ParameterizedType parameterizedType;
            for (rawGenericDeclaration = (Class<?>)genericDeclaration, contextIndex = context.size(), parameterizedType = null; parameterizedType == null && --contextIndex >= 0; parameterizedType = getSuperParameterizedType(context.get(contextIndex), rawGenericDeclaration)) {}
            if (parameterizedType != null) {
                TypeVariable<?>[] typeParameters;
                int index;
                TypeVariable<?> typeParameter;
                for (typeParameters = genericDeclaration.getTypeParameters(), index = 0; index < typeParameters.length; ++index) {
                    typeParameter = typeParameters[index];
                    if (typeParameter.equals(typeVariable)) {
                        break;
                    }
                }
                final Type result = parameterizedType.getActualTypeArguments()[index];
                if (result instanceof TypeVariable) {
                    final Type resolve = resolveTypeVariable(context, (TypeVariable<?>)result);
                    if (resolve != null) {
                        return resolve;
                    }
                }
                return result;
            }
        }
        return null;
    }
    
    public static Class<?> getRawArrayComponentType(final List<Type> context, Type componentType) {
        if (componentType instanceof TypeVariable) {
            componentType = resolveTypeVariable(context, (TypeVariable<?>)componentType);
        }
        if (componentType instanceof GenericArrayType) {
            final Class<?> raw = getRawArrayComponentType(context, getArrayComponentType(componentType));
            return Array.newInstance(raw, 0).getClass();
        }
        if (componentType instanceof Class) {
            return (Class<?>)componentType;
        }
        if (componentType instanceof ParameterizedType) {
            return getRawClass((ParameterizedType)componentType);
        }
        Preconditions.checkArgument(componentType == null, "wildcard type is not supported: %s", componentType);
        return Object.class;
    }
    
    public static Type getIterableParameter(final Type iterableType) {
        return getActualParameterAtPosition(iterableType, Iterable.class, 0);
    }
    
    public static Type getMapValueParameter(final Type mapType) {
        return getActualParameterAtPosition(mapType, Map.class, 1);
    }
    
    private static Type getActualParameterAtPosition(final Type type, final Class<?> superClass, final int position) {
        final ParameterizedType parameterizedType = getSuperParameterizedType(type, superClass);
        if (parameterizedType == null) {
            return null;
        }
        final Type valueType = parameterizedType.getActualTypeArguments()[position];
        if (valueType instanceof TypeVariable) {
            final Type resolve = resolveTypeVariable(Arrays.asList(type), (TypeVariable<?>)valueType);
            if (resolve != null) {
                return resolve;
            }
        }
        return valueType;
    }
    
    public static <T> Iterable<T> iterableOf(final Object value) {
        if (value instanceof Iterable) {
            return (Iterable<T>)value;
        }
        final Class<?> valueClass = value.getClass();
        Preconditions.checkArgument(valueClass.isArray(), "not an array or Iterable: %s", valueClass);
        final Class<?> subClass = valueClass.getComponentType();
        if (!subClass.isPrimitive()) {
            return (Iterable<T>)Arrays.asList((Object[])value);
        }
        return new Iterable<T>() {
            final /* synthetic */ Object val$value;
            
            Types$1() {
                super();
            }
            
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    final int length = Array.getLength(value);
                    int index = 0;
                    final /* synthetic */ Types$1 this$0;
                    
                    Types$1$1() {
                        this.this$0 = this$0;
                        super();
                    }
                    
                    @Override
                    public boolean hasNext() {
                        return this.index < this.length;
                    }
                    
                    @Override
                    public T next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return (T)Array.get(value, this.index++);
                    }
                    
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
    
    public static Object toArray(final Collection<?> collection, final Class<?> componentType) {
        if (componentType.isPrimitive()) {
            final Object array = Array.newInstance(componentType, collection.size());
            int index = 0;
            for (final Object value : collection) {
                Array.set(array, index++, value);
            }
            return array;
        }
        return collection.toArray((Object[])Array.newInstance(componentType, collection.size()));
    }
    
    private Types() {
        super();
    }
}
