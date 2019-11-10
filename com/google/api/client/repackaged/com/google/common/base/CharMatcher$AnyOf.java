package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class AnyOf extends CharMatcher
{
    private final char[] chars;
    
    public AnyOf(final CharSequence chars) {
        super();
        Arrays.sort(this.chars = chars.toString().toCharArray());
    }
    
    @Override
    public boolean matches(final char c) {
        return Arrays.binarySearch(this.chars, c) >= 0;
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet table) {
        for (final char c : this.chars) {
            table.set(c);
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder description = new StringBuilder("CharMatcher.anyOf(\"");
        for (final char c : this.chars) {
            description.append(CharMatcher.access$100(c));
        }
        description.append("\")");
        return description.toString();
    }
    
    @Override
    public /* bridge */ boolean apply(final Object x0) {
        return super.apply((Character)x0);
    }
}
