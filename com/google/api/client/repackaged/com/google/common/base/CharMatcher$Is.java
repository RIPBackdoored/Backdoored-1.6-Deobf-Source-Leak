package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class Is extends FastMatcher
{
    private final char match;
    
    Is(final char match) {
        super();
        this.match = match;
    }
    
    @Override
    public boolean matches(final char c) {
        return c == this.match;
    }
    
    @Override
    public String replaceFrom(final CharSequence sequence, final char replacement) {
        return sequence.toString().replace(this.match, replacement);
    }
    
    @Override
    public CharMatcher and(final CharMatcher other) {
        return other.matches(this.match) ? this : CharMatcher.none();
    }
    
    @Override
    public CharMatcher or(final CharMatcher other) {
        return other.matches(this.match) ? other : super.or(other);
    }
    
    @Override
    public CharMatcher negate() {
        return CharMatcher.isNot(this.match);
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet table) {
        table.set(this.match);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.is('" + CharMatcher.access$100(this.match) + "')";
    }
}
