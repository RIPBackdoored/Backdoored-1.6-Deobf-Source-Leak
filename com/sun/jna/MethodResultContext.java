package com.sun.jna;

import java.lang.reflect.*;

public class MethodResultContext extends FunctionResultContext
{
    private final Method method;
    
    MethodResultContext(final Class<?> resultClass, final Function function, final Object[] args, final Method method) {
        super(resultClass, function, args);
        this.method = method;
    }
    
    public Method getMethod() {
        return this.method;
    }
}
