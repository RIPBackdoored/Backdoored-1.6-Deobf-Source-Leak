package com.google.api.client.repackaged.com.google.common.base;

import javax.annotation.*;
import com.google.errorprone.annotations.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

@GwtCompatible(emulated = true)
public final class Throwables
{
    @GwtIncompatible
    private static final String JAVA_LANG_ACCESS_CLASSNAME = "sun.misc.JavaLangAccess";
    @GwtIncompatible
    @VisibleForTesting
    static final String SHARED_SECRETS_CLASSNAME = "sun.misc.SharedSecrets";
    @Nullable
    @GwtIncompatible
    private static final Object jla;
    @Nullable
    @GwtIncompatible
    private static final Method getStackTraceElementMethod;
    @Nullable
    @GwtIncompatible
    private static final Method getStackTraceDepthMethod;
    
    private Throwables() {
        super();
    }
    
    @GwtIncompatible
    public static <X extends Throwable> void throwIfInstanceOf(final Throwable throwable, final Class<X> declaredType) throws X, Throwable {
        Preconditions.checkNotNull(throwable);
        if (declaredType.isInstance(throwable)) {
            throw declaredType.cast(throwable);
        }
    }
    
    @Deprecated
    @GwtIncompatible
    public static <X extends Throwable> void propagateIfInstanceOf(@Nullable final Throwable throwable, final Class<X> declaredType) throws X, Throwable {
        if (throwable != null) {
            throwIfInstanceOf(throwable, (Class<Throwable>)declaredType);
        }
    }
    
    public static void throwIfUnchecked(final Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
    }
    
    @Deprecated
    @GwtIncompatible
    public static void propagateIfPossible(@Nullable final Throwable throwable) {
        if (throwable != null) {
            throwIfUnchecked(throwable);
        }
    }
    
    @GwtIncompatible
    public static <X extends Throwable> void propagateIfPossible(@Nullable final Throwable throwable, final Class<X> declaredType) throws X, Throwable {
        propagateIfInstanceOf(throwable, (Class<Throwable>)declaredType);
        propagateIfPossible(throwable);
    }
    
    @GwtIncompatible
    public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(@Nullable final Throwable throwable, final Class<X1> declaredType1, final Class<X2> declaredType2) throws X1, X2, Throwable {
        Preconditions.checkNotNull(declaredType2);
        propagateIfInstanceOf(throwable, declaredType1);
        propagateIfPossible(throwable, declaredType2);
    }
    
    @Deprecated
    @CanIgnoreReturnValue
    @GwtIncompatible
    public static RuntimeException propagate(final Throwable throwable) {
        throwIfUnchecked(throwable);
        throw new RuntimeException(throwable);
    }
    
    public static Throwable getRootCause(Throwable throwable) {
        Throwable cause;
        while ((cause = throwable.getCause()) != null) {
            throwable = cause;
        }
        return throwable;
    }
    
    @Beta
    public static List<Throwable> getCausalChain(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        final List<Throwable> causes = new ArrayList<Throwable>(4);
        while (throwable != null) {
            causes.add(throwable);
            throwable = throwable.getCause();
        }
        return Collections.unmodifiableList((List<? extends Throwable>)causes);
    }
    
    @GwtIncompatible
    public static String getStackTraceAsString(final Throwable throwable) {
        final StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
    
    @Beta
    @GwtIncompatible
    public static List<StackTraceElement> lazyStackTrace(final Throwable throwable) {
        return lazyStackTraceIsLazy() ? jlaStackTrace(throwable) : Collections.unmodifiableList((List<? extends StackTraceElement>)Arrays.asList((T[])throwable.getStackTrace()));
    }
    
    @Beta
    @GwtIncompatible
    public static boolean lazyStackTraceIsLazy() {
        return Throwables.getStackTraceElementMethod != null & Throwables.getStackTraceDepthMethod != null;
    }
    
    @GwtIncompatible
    private static List<StackTraceElement> jlaStackTrace(final Throwable t) {
        Preconditions.checkNotNull(t);
        return new AbstractList<StackTraceElement>() {
            final /* synthetic */ Throwable val$t;
            
            Throwables$1() {
                super();
            }
            
            @Override
            public StackTraceElement get(final int n) {
                return (StackTraceElement)invokeAccessibleNonThrowingMethod(Throwables.getStackTraceElementMethod, Throwables.jla, new Object[] { t, n });
            }
            
            @Override
            public int size() {
                return (int)invokeAccessibleNonThrowingMethod(Throwables.getStackTraceDepthMethod, Throwables.jla, new Object[] { t });
            }
            
            @Override
            public /* bridge */ Object get(final int x0) {
                return this.get(x0);
            }
        };
    }
    
    @GwtIncompatible
    private static Object invokeAccessibleNonThrowingMethod(final Method method, final Object receiver, final Object... params) {
        try {
            return method.invoke(receiver, params);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e2) {
            throw propagate(e2.getCause());
        }
    }
    
    @Nullable
    @GwtIncompatible
    private static Object getJLA() {
        try {
            final Class<?> sharedSecrets = Class.forName("sun.misc.SharedSecrets", false, null);
            final Method langAccess = sharedSecrets.getMethod("getJavaLangAccess", (Class<?>[])new Class[0]);
            return langAccess.invoke(null, new Object[0]);
        }
        catch (ThreadDeath death) {
            throw death;
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    @Nullable
    @GwtIncompatible
    private static Method getGetMethod() {
        return getJlaMethod("getStackTraceElement", Throwable.class, Integer.TYPE);
    }
    
    @Nullable
    @GwtIncompatible
    private static Method getSizeMethod() {
        return getJlaMethod("getStackTraceDepth", Throwable.class);
    }
    
    @Nullable
    @GwtIncompatible
    private static Method getJlaMethod(final String name, final Class<?>... parameterTypes) throws ThreadDeath {
        try {
            return Class.forName("sun.misc.JavaLangAccess", false, null).getMethod(name, parameterTypes);
        }
        catch (ThreadDeath death) {
            throw death;
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    static /* synthetic */ Method access$000() {
        return Throwables.getStackTraceElementMethod;
    }
    
    static /* synthetic */ Object access$100() {
        return Throwables.jla;
    }
    
    static /* synthetic */ Object access$200(final Method x0, final Object x1, final Object[] x2) {
        return invokeAccessibleNonThrowingMethod(x0, x1, x2);
    }
    
    static /* synthetic */ Method access$300() {
        return Throwables.getStackTraceDepthMethod;
    }
    
    static {
        jla = getJLA();
        getStackTraceElementMethod = ((Throwables.jla == null) ? null : getGetMethod());
        getStackTraceDepthMethod = ((Throwables.jla == null) ? null : getSizeMethod());
    }
}
