package com.google.api.client.repackaged.com.google.common.base;

private static final class JavaLetter extends CharMatcher
{
    static final JavaLetter INSTANCE;
    
    private JavaLetter() {
        super();
    }
    
    @Override
    public boolean matches(final char c) {
        return Character.isLetter(c);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.javaLetter()";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object x0) {
        return super.apply((Character)x0);
    }
    
    static {
        INSTANCE = new JavaLetter();
    }
}
