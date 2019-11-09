package com.fasterxml.jackson.core.sym;

public final class Name1 extends Name
{
    private static final Name1 EMPTY;
    private final int q;
    
    Name1(final String name, final int hash, final int quad) {
        super(name, hash);
        this.q = quad;
    }
    
    public static Name1 getEmptyName() {
        return Name1.EMPTY;
    }
    
    @Override
    public boolean equals(final int quad) {
        return quad == this.q;
    }
    
    @Override
    public boolean equals(final int quad1, final int quad2) {
        return quad1 == this.q && quad2 == 0;
    }
    
    @Override
    public boolean equals(final int q1, final int q2, final int q3) {
        return false;
    }
    
    @Override
    public boolean equals(final int[] quads, final int qlen) {
        return qlen == 1 && quads[0] == this.q;
    }
    
    static {
        EMPTY = new Name1("", 0, 0);
    }
}
