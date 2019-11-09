package com.fasterxml.jackson.core.sym;

static final class Bucket
{
    public final String symbol;
    public final Bucket next;
    public final int length;
    
    public Bucket(final String s, final Bucket n) {
        super();
        this.symbol = s;
        this.next = n;
        this.length = ((n == null) ? 1 : (n.length + 1));
    }
    
    public String has(final char[] buf, final int start, final int len) {
        if (this.symbol.length() != len) {
            return null;
        }
        int i = 0;
        while (this.symbol.charAt(i) == buf[start + i]) {
            if (++i >= len) {
                return this.symbol;
            }
        }
        return null;
    }
}
