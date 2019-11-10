package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class IsNot extends FastMatcher
{
    private final char match;
    
    IsNot(final char match) {
        super();
        this.match = match;
    }
    
    @Override
    public boolean matches(final char c) {
        return c != this.match;
    }
    
    @Override
    public CharMatcher and(final CharMatcher other) {
        return other.matches(this.match) ? super.and(other) : other;
    }
    
    @Override
    public CharMatcher or(final CharMatcher other) {
        return other.matches(this.match) ? CharMatcher.any() : this;
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet table) {
        table.set(0, this.match);
        table.set(this.match + '\u0001', 65536);
    }
    
    @Override
    public CharMatcher negate() {
        return CharMatcher.is(this.match);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.isNot('" + CharMatcher.access$100(this.match) + "')";
    }
}
