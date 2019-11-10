package com.google.api.client.repackaged.com.google.common.base;

import java.io.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.util.regex.*;

@GwtIncompatible
final class JdkPattern extends CommonPattern implements Serializable
{
    private final Pattern pattern;
    private static final long serialVersionUID = 0L;
    
    JdkPattern(final Pattern pattern) {
        super();
        this.pattern = Preconditions.checkNotNull(pattern);
    }
    
    @Override
    CommonMatcher matcher(final CharSequence t) {
        return new JdkMatcher(this.pattern.matcher(t));
    }
    
    @Override
    String pattern() {
        return this.pattern.pattern();
    }
    
    @Override
    int flags() {
        return this.pattern.flags();
    }
    
    @Override
    public String toString() {
        return this.pattern.toString();
    }
    
    @Override
    public int hashCode() {
        return this.pattern.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof JdkPattern && this.pattern.equals(((JdkPattern)o).pattern);
    }
    
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
}
