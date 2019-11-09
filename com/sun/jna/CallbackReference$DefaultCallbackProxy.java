package com.sun.jna;

import java.lang.reflect.*;

private class DefaultCallbackProxy implements CallbackProxy
{
    private final Method callbackMethod;
    private ToNativeConverter toNative;
    private final FromNativeConverter[] fromNative;
    private final String encoding;
    final /* synthetic */ CallbackReference this$0;
    
    public DefaultCallbackProxy(final CallbackReference this$0, final Method callbackMethod, final TypeMapper mapper, final String encoding) {
        this.this$0 = this$0;
        super();
        this.callbackMethod = callbackMethod;
        this.encoding = encoding;
        final Class<?>[] argTypes = callbackMethod.getParameterTypes();
        final Class<?> returnType = callbackMethod.getReturnType();
        this.fromNative = new FromNativeConverter[argTypes.length];
        if (NativeMapped.class.isAssignableFrom(returnType)) {
            this.toNative = NativeMappedConverter.getInstance(returnType);
        }
        else if (mapper != null) {
            this.toNative = mapper.getToNativeConverter(returnType);
        }
        for (int i = 0; i < this.fromNative.length; ++i) {
            if (NativeMapped.class.isAssignableFrom(argTypes[i])) {
                this.fromNative[i] = new NativeMappedConverter(argTypes[i]);
            }
            else if (mapper != null) {
                this.fromNative[i] = mapper.getFromNativeConverter(argTypes[i]);
            }
        }
        if (!callbackMethod.isAccessible()) {
            try {
                callbackMethod.setAccessible(true);
            }
            catch (SecurityException e) {
                throw new IllegalArgumentException("Callback method is inaccessible, make sure the interface is public: " + callbackMethod);
            }
        }
    }
    
    public Callback getCallback() {
        return CallbackReference.access$000(this.this$0);
    }
    
    private Object invokeCallback(final Object[] args) {
        final Class<?>[] paramTypes = this.callbackMethod.getParameterTypes();
        final Object[] callbackArgs = new Object[args.length];
        for (int i = 0; i < args.length; ++i) {
            final Class<?> type = paramTypes[i];
            final Object arg = args[i];
            if (this.fromNative[i] != null) {
                final FromNativeContext context = new CallbackParameterContext(type, this.callbackMethod, args, i);
                callbackArgs[i] = this.fromNative[i].fromNative(arg, context);
            }
            else {
                callbackArgs[i] = this.convertArgument(arg, type);
            }
        }
        Object result = null;
        final Callback cb = this.getCallback();
        if (cb != null) {
            try {
                result = this.convertResult(this.callbackMethod.invoke(cb, callbackArgs));
            }
            catch (IllegalArgumentException e) {
                Native.getCallbackExceptionHandler().uncaughtException(cb, e);
            }
            catch (IllegalAccessException e2) {
                Native.getCallbackExceptionHandler().uncaughtException(cb, e2);
            }
            catch (InvocationTargetException e3) {
                Native.getCallbackExceptionHandler().uncaughtException(cb, e3.getTargetException());
            }
        }
        for (int j = 0; j < callbackArgs.length; ++j) {
            if (callbackArgs[j] instanceof Structure && !(callbackArgs[j] instanceof Structure.ByValue)) {
                ((Structure)callbackArgs[j]).autoWrite();
            }
        }
        return result;
    }
    
    @Override
    public Object callback(final Object[] args) {
        try {
            return this.invokeCallback(args);
        }
        catch (Throwable t) {
            Native.getCallbackExceptionHandler().uncaughtException(this.getCallback(), t);
            return null;
        }
    }
    
    private Object convertArgument(Object value, final Class<?> dstType) {
        if (value instanceof Pointer) {
            if (dstType == String.class) {
                value = ((Pointer)value).getString(0L, this.encoding);
            }
            else if (dstType == WString.class) {
                value = new WString(((Pointer)value).getWideString(0L));
            }
            else if (dstType == String[].class) {
                value = ((Pointer)value).getStringArray(0L, this.encoding);
            }
            else if (dstType == WString[].class) {
                value = ((Pointer)value).getWideStringArray(0L);
            }
            else if (Callback.class.isAssignableFrom(dstType)) {
                value = CallbackReference.getCallback(dstType, (Pointer)value);
            }
            else if (Structure.class.isAssignableFrom(dstType)) {
                if (Structure.ByValue.class.isAssignableFrom(dstType)) {
                    final Structure s = Structure.newInstance(dstType);
                    final byte[] buf = new byte[s.size()];
                    ((Pointer)value).read(0L, buf, 0, buf.length);
                    s.getPointer().write(0L, buf, 0, buf.length);
                    s.read();
                    value = s;
                }
                else {
                    final Structure s = Structure.newInstance(dstType, (Pointer)value);
                    s.conditionalAutoRead();
                    value = s;
                }
            }
        }
        else if ((Boolean.TYPE == dstType || Boolean.class == dstType) && value instanceof Number) {
            value = Function.valueOf(((Number)value).intValue() != 0);
        }
        return value;
    }
    
    private Object convertResult(Object value) {
        if (this.toNative != null) {
            value = this.toNative.toNative(value, new CallbackResultContext(this.callbackMethod));
        }
        if (value == null) {
            return null;
        }
        final Class<?> cls = value.getClass();
        if (Structure.class.isAssignableFrom(cls)) {
            if (Structure.ByValue.class.isAssignableFrom(cls)) {
                return value;
            }
            return ((Structure)value).getPointer();
        }
        else {
            if (cls == Boolean.TYPE || cls == Boolean.class) {
                return Boolean.TRUE.equals(value) ? Function.INTEGER_TRUE : Function.INTEGER_FALSE;
            }
            if (cls == String.class || cls == WString.class) {
                return CallbackReference.access$100(value, cls == WString.class);
            }
            if (cls == String[].class || cls == WString.class) {
                final StringArray sa = (cls == String[].class) ? new StringArray((String[])value, this.encoding) : new StringArray((WString[])value);
                CallbackReference.allocations.put(value, sa);
                return sa;
            }
            if (Callback.class.isAssignableFrom(cls)) {
                return CallbackReference.getFunctionPointer((Callback)value);
            }
            return value;
        }
    }
    
    @Override
    public Class<?>[] getParameterTypes() {
        return this.callbackMethod.getParameterTypes();
    }
    
    @Override
    public Class<?> getReturnType() {
        return this.callbackMethod.getReturnType();
    }
}
