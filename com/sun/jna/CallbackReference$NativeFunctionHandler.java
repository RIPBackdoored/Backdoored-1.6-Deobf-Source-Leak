package com.sun.jna;

import java.util.*;
import java.lang.reflect.*;

private static class NativeFunctionHandler implements InvocationHandler
{
    private final Function function;
    private final Map<String, ?> options;
    
    public NativeFunctionHandler(final Pointer address, final int callingConvention, final Map<String, ?> options) {
        super();
        this.options = options;
        this.function = new Function(address, callingConvention, (String)options.get("string-encoding"));
    }
    
    @Override
    public Object invoke(final Object proxy, final Method method, Object[] args) throws Throwable {
        if (Library.Handler.OBJECT_TOSTRING.equals(method)) {
            String str = "Proxy interface to " + this.function;
            final Method m = (Method)this.options.get("invoking-method");
            final Class<?> cls = CallbackReference.findCallbackClass(m.getDeclaringClass());
            str = str + " (" + cls.getName() + ")";
            return str;
        }
        if (Library.Handler.OBJECT_HASHCODE.equals(method)) {
            return this.hashCode();
        }
        if (!Library.Handler.OBJECT_EQUALS.equals(method)) {
            if (Function.isVarArgs(method)) {
                args = Function.concatenateVarArgs(args);
            }
            return this.function.invoke(method.getReturnType(), args, this.options);
        }
        final Object o = args[0];
        if (o != null && Proxy.isProxyClass(o.getClass())) {
            return Function.valueOf(Proxy.getInvocationHandler(o) == this);
        }
        return Boolean.FALSE;
    }
    
    public Pointer getPointer() {
        return this.function;
    }
}
