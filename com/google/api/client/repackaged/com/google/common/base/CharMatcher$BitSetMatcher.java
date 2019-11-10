package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.util.*;

@GwtIncompatible
private static final class BitSetMatcher extends NamedFastMatcher
{
    private final BitSet table;
    
    private BitSetMatcher(BitSet table, final String description) {
        super(description);
        if (table.length() + 64 < table.size()) {
            table = (BitSet)table.clone();
        }
        this.table = table;
    }
    
    @Override
    public boolean matches(final char c) {
        return this.table.get(c);
    }
    
    @Override
    void setBits(final BitSet bitSet) {
        bitSet.or(this.table);
    }
    
    BitSetMatcher(final BitSet x0, final String x1, final CharMatcher$1 x2) {
        this(x0, x1);
    }
}
