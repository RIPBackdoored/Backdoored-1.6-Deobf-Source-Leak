package com.sun.jna;

import java.lang.reflect.*;

public class CallbackResultContext extends ToNativeContext
{
    private Method method;
    
    CallbackResultContext(final Method callbackMethod) {
        super();
        this.method = callbackMethod;
    }
    
    public Method getMethod() {
        return this.method;
    }
}
