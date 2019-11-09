package net.jodah.typetools;

import java.security.*;
import sun.misc.*;
import java.lang.reflect.*;

static final class TypeResolver$1 implements PrivilegedExceptionAction<Unsafe> {
    TypeResolver$1() {
        super();
    }
    
    @Override
    public Unsafe run() throws Exception {
        final Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (Unsafe)f.get(null);
    }
    
    @Override
    public /* bridge */ Object run() throws Exception {
        return this.run();
    }
}