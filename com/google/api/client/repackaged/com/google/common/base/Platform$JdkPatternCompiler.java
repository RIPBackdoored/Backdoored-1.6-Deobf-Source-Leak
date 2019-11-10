package com.google.api.client.repackaged.com.google.common.base;

import java.util.regex.*;

private static final class JdkPatternCompiler implements PatternCompiler
{
    private JdkPatternCompiler() {
        super();
    }
    
    @Override
    public CommonPattern compile(final String pattern) {
        return new JdkPattern(Pattern.compile(pattern));
    }
    
    JdkPatternCompiler(final Platform$1 x0) {
        this();
    }
}
