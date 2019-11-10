package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class InRange extends FastMatcher
{
    private final char startInclusive;
    private final char endInclusive;
    
    InRange(final char startInclusive, final char endInclusive) {
        super();
        Preconditions.checkArgument(endInclusive >= startInclusive);
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
    }
    
    @Override
    public boolean matches(final char c) {
        return this.startInclusive <= c && c <= this.endInclusive;
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet table) {
        table.set(this.startInclusive, this.endInclusive + '\u0001');
    }
    
    @Override
    public String toString() {
        return "CharMatcher.inRange('" + CharMatcher.access$100(this.startInclusive) + "', '" + CharMatcher.access$100(this.endInclusive) + "')";
    }
}
