package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

private static class RangesMatcher extends CharMatcher
{
    private final String description;
    private final char[] rangeStarts;
    private final char[] rangeEnds;
    
    RangesMatcher(final String description, final char[] rangeStarts, final char[] rangeEnds) {
        super();
        this.description = description;
        this.rangeStarts = rangeStarts;
        this.rangeEnds = rangeEnds;
        Preconditions.checkArgument(rangeStarts.length == rangeEnds.length);
        for (int i = 0; i < rangeStarts.length; ++i) {
            Preconditions.checkArgument(rangeStarts[i] <= rangeEnds[i]);
            if (i + 1 < rangeStarts.length) {
                Preconditions.checkArgument(rangeEnds[i] < rangeStarts[i + 1]);
            }
        }
    }
    
    @Override
    public boolean matches(final char c) {
        int index = Arrays.binarySearch(this.rangeStarts, c);
        if (index >= 0) {
            return true;
        }
        index = ~index - 1;
        return index >= 0 && c <= this.rangeEnds[index];
    }
    
    @Override
    public String toString() {
        return this.description;
    }
    
    @Override
    public /* bridge */ boolean apply(final Object x0) {
        return super.apply((Character)x0);
    }
}
