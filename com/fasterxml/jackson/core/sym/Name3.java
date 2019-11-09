package com.fasterxml.jackson.core.sym;

public final class Name3 extends Name
{
    private final int q1;
    private final int q2;
    private final int q3;
    
    Name3(final String name, final int hash, final int i1, final int i2, final int i3) {
        super(name, hash);
        this.q1 = i1;
        this.q2 = i2;
        this.q3 = i3;
    }
    
    @Override
    public boolean equals(final int quad) {
        return false;
    }
    
    @Override
    public boolean equals(final int quad1, final int quad2) {
        return false;
    }
    
    @Override
    public boolean equals(final int quad1, final int quad2, final int quad3) {
        return this.q1 == quad1 && this.q2 == quad2 && this.q3 == quad3;
    }
    
    @Override
    public boolean equals(final int[] quads, final int qlen) {
        return qlen == 3 && quads[0] == this.q1 && quads[1] == this.q2 && quads[2] == this.q3;
    }
}
