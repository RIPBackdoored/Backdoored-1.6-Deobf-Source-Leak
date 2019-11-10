package com.google.api.client.repackaged.com.google.common.base;

private static final class JavaDigit extends CharMatcher
{
    static final JavaDigit INSTANCE;
    
    private JavaDigit() {
        super();
    }
    
    @Override
    public boolean matches(final char c) {
        return Character.isDigit(c);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.javaDigit()";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object x0) {
        return super.apply((Character)x0);
    }
    
    static {
        INSTANCE = new JavaDigit();
    }
}
