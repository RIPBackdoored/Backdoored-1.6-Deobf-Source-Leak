package javassist;

import java.security.*;
import java.net.*;

static final class ClassPool$1 implements PrivilegedExceptionAction {
    ClassPool$1() {
        super();
    }
    
    @Override
    public Object run() throws Exception {
        final Class cl = Class.forName("java.lang.ClassLoader");
        ClassPool.access$002(cl.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE));
        ClassPool.access$102(cl.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class));
        ClassPool.access$202(cl.getDeclaredMethod("definePackage", String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class));
        return null;
    }
}