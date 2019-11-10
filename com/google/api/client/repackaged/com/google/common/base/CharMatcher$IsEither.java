package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class IsEither extends FastMatcher
{
    private final char match1;
    private final char match2;
    
    IsEither(final char match1, final char match2) {
        super();
        this.match1 = match1;
        this.match2 = match2;
    }
    
    @Override
    public boolean matches(final char c) {
        return c == this.match1 || c == this.match2;
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet table) {
        table.set(this.match1);
        table.set(this.match2);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.anyOf(\"" + CharMatcher.access$100(this.match1) + CharMatcher.access$100(this.match2) + "\")";
    }
}
