package com.google.api.client.repackaged.com.google.common.base;

import java.util.regex.*;

private static final class JdkMatcher extends CommonMatcher
{
    final Matcher matcher;
    
    JdkMatcher(final Matcher matcher) {
        super();
        this.matcher = Preconditions.checkNotNull(matcher);
    }
    
    @Override
    boolean matches() {
        return this.matcher.matches();
    }
    
    @Override
    boolean find() {
        return this.matcher.find();
    }
    
    @Override
    boolean find(final int index) {
        return this.matcher.find(index);
    }
    
    @Override
    String replaceAll(final String replacement) {
        return this.matcher.replaceAll(replacement);
    }
    
    @Override
    int end() {
        return this.matcher.end();
    }
    
    @Override
    int start() {
        return this.matcher.start();
    }
}
