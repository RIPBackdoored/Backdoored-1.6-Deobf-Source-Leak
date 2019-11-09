package com.sun.jna;

import java.lang.reflect.*;

private static final class NoVarArgsChecker extends VarArgsChecker
{
    private NoVarArgsChecker() {
        super(null);
    }
    
    @Override
    boolean isVarArgs(final Method m) {
        return false;
    }
    
    @Override
    int fixedArgs(final Method m) {
        return 0;
    }
    
    NoVarArgsChecker(final VarArgsChecker$1 x0) {
        this();
    }
}
