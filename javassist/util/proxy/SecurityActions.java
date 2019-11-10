package javassist.util.proxy;

import java.security.*;
import java.lang.reflect.*;

class SecurityActions
{
    SecurityActions() {
        super();
    }
    
    static Method[] getDeclaredMethods(final Class clazz) {
        if (System.getSecurityManager() == null) {
            return clazz.getDeclaredMethods();
        }
        return AccessController.doPrivileged((PrivilegedAction<Method[]>)new PrivilegedAction() {
            final /* synthetic */ Class val$clazz;
            
            SecurityActions$1() {
                super();
            }
            
            @Override
            public Object run() {
                return clazz.getDeclaredMethods();
            }
        });
    }
    
    static Constructor[] getDeclaredConstructors(final Class clazz) {
        if (System.getSecurityManager() == null) {
            return clazz.getDeclaredConstructors();
        }
        return AccessController.doPrivileged((PrivilegedAction<Constructor[]>)new PrivilegedAction() {
            final /* synthetic */ Class val$clazz;
            
            SecurityActions$2() {
                super();
            }
            
            @Override
            public Object run() {
                return clazz.getDeclaredConstructors();
            }
        });
    }
    
    static Method getDeclaredMethod(final Class clazz, final String name, final Class[] types) throws NoSuchMethodException {
        if (System.getSecurityManager() == null) {
            return clazz.getDeclaredMethod(name, (Class[])types);
        }
        try {
            return AccessController.doPrivileged((PrivilegedExceptionAction<Method>)new PrivilegedExceptionAction() {
                final /* synthetic */ Class val$clazz;
                final /* synthetic */ String val$name;
                final /* synthetic */ Class[] val$types;
                
                SecurityActions$3() {
                    super();
                }
                
                @Override
                public Object run() throws Exception {
                    return clazz.getDeclaredMethod(name, (Class[])types);
                }
            });
        }
        catch (PrivilegedActionException e) {
            if (e.getCause() instanceof NoSuchMethodException) {
                throw (NoSuchMethodException)e.getCause();
            }
            throw new RuntimeException(e.getCause());
        }
    }
    
    static Constructor getDeclaredConstructor(final Class clazz, final Class[] types) throws NoSuchMethodException {
        if (System.getSecurityManager() == null) {
            return clazz.getDeclaredConstructor((Class[])types);
        }
        try {
            return AccessController.doPrivileged((PrivilegedExceptionAction<Constructor>)new PrivilegedExceptionAction() {
                final /* synthetic */ Class val$clazz;
                final /* synthetic */ Class[] val$types;
                
                SecurityActions$4() {
                    super();
                }
                
                @Override
                public Object run() throws Exception {
                    return clazz.getDeclaredConstructor((Class[])types);
                }
            });
        }
        catch (PrivilegedActionException e) {
            if (e.getCause() instanceof NoSuchMethodException) {
                throw (NoSuchMethodException)e.getCause();
            }
            throw new RuntimeException(e.getCause());
        }
    }
    
    static void setAccessible(final AccessibleObject ao, final boolean accessible) {
        if (System.getSecurityManager() == null) {
            ao.setAccessible(accessible);
        }
        else {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                final /* synthetic */ AccessibleObject val$ao;
                final /* synthetic */ boolean val$accessible;
                
                SecurityActions$5() {
                    super();
                }
                
                @Override
                public Object run() {
                    ao.setAccessible(accessible);
                    return null;
                }
            });
        }
    }
    
    static void set(final Field fld, final Object target, final Object value) throws IllegalAccessException {
        if (System.getSecurityManager() == null) {
            fld.set(target, value);
        }
        else {
            try {
                AccessController.doPrivileged((PrivilegedExceptionAction<Object>)new PrivilegedExceptionAction() {
                    final /* synthetic */ Field val$fld;
                    final /* synthetic */ Object val$target;
                    final /* synthetic */ Object val$value;
                    
                    SecurityActions$6() {
                        super();
                    }
                    
                    @Override
                    public Object run() throws Exception {
                        fld.set(target, value);
                        return null;
                    }
                });
            }
            catch (PrivilegedActionException e) {
                if (e.getCause() instanceof NoSuchMethodException) {
                    throw (IllegalAccessException)e.getCause();
                }
                throw new RuntimeException(e.getCause());
            }
        }
    }
}
