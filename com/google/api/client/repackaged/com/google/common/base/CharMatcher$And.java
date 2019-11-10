package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class And extends CharMatcher
{
    final CharMatcher first;
    final CharMatcher second;
    
    And(final CharMatcher a, final CharMatcher b) {
        super();
        this.first = Preconditions.checkNotNull(a);
        this.second = Preconditions.checkNotNull(b);
    }
    
    @Override
    public boolean matches(final char c) {
        return this.first.matches(c) && this.second.matches(c);
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet table) {
        final BitSet tmp1 = new BitSet();
        this.first.setBits(tmp1);
        final BitSet tmp2 = new BitSet();
        this.second.setBits(tmp2);
        tmp1.and(tmp2);
        table.or(tmp1);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.and(" + this.first + ", " + this.second + ")";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object x0) {
        return super.apply((Character)x0);
    }
}
