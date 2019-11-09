package com.sun.jna;

import java.lang.reflect.*;

private static final class RealVarArgsChecker extends VarArgsChecker
{
    private RealVarArgsChecker() {
        super(null);
    }
    
    @Override
    boolean isVarArgs(final Method m) {
        return m.isVarArgs();
    }
    
    @Override
    int fixedArgs(final Method m) {
        return m.isVarArgs() ? (m.getParameterTypes().length - 1) : 0;
    }
    
    RealVarArgsChecker(final VarArgsChecker$1 x0) {
        this();
    }
}
