package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static class Negated extends CharMatcher
{
    final CharMatcher original;
    
    Negated(final CharMatcher original) {
        super();
        this.original = Preconditions.checkNotNull(original);
    }
    
    @Override
    public boolean matches(final char c) {
        return !this.original.matches(c);
    }
    
    @Override
    public boolean matchesAllOf(final CharSequence sequence) {
        return this.original.matchesNoneOf(sequence);
    }
    
    @Override
    public boolean matchesNoneOf(final CharSequence sequence) {
        return this.original.matchesAllOf(sequence);
    }
    
    @Override
    public int countIn(final CharSequence sequence) {
        return sequence.length() - this.original.countIn(sequence);
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet table) {
        final BitSet tmp = new BitSet();
        this.original.setBits(tmp);
        tmp.flip(0, 65536);
        table.or(tmp);
    }
    
    @Override
    public CharMatcher negate() {
        return this.original;
    }
    
    @Override
    public String toString() {
        return this.original + ".negate()";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object x0) {
        return super.apply((Character)x0);
    }
}
