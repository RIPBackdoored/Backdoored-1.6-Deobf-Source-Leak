package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class Or extends CharMatcher
{
    final CharMatcher first;
    final CharMatcher second;
    
    Or(final CharMatcher a, final CharMatcher b) {
        super();
        this.first = Preconditions.checkNotNull(a);
        this.second = Preconditions.checkNotNull(b);
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet table) {
        this.first.setBits(table);
        this.second.setBits(table);
    }
    
    @Override
    public boolean matches(final char c) {
        return this.first.matches(c) || this.second.matches(c);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.or(" + this.first + ", " + this.second + ")";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object x0) {
        return super.apply((Character)x0);
    }
}
