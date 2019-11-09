package com.sun.jna;

import java.security.*;
import java.lang.reflect.*;

static final class Native$4 implements PrivilegedAction<Method> {
    Native$4() {
        super();
    }
    
    @Override
    public Method run() {
        try {
            final Method m = ClassLoader.class.getDeclaredMethod("findLibrary", String.class);
            m.setAccessible(true);
            return m;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public /* bridge */ Object run() {
        return this.run();
    }
}