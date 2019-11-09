package com.sun.jna;

import java.lang.reflect.*;
import java.util.*;

class NativeLibrary$1 extends Function {
    final /* synthetic */ NativeLibrary this$0;
    
    NativeLibrary$1(final NativeLibrary this$0, final NativeLibrary library, final String functionName, final int callFlags, final String encoding) {
        this.this$0 = this$0;
        super(library, functionName, callFlags, encoding);
    }
    
    @Override
    Object invoke(final Object[] args, final Class<?> returnType, final boolean b, final int fixedArgs) {
        return Native.getLastError();
    }
    
    @Override
    Object invoke(final Method invokingMethod, final Class<?>[] paramTypes, final Class<?> returnType, final Object[] inArgs, final Map<String, ?> options) {
        return Native.getLastError();
    }
}