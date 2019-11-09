package com.fasterxml.jackson.core.sym;

import java.util.*;

public final class NameN extends Name
{
    private final int q1;
    private final int q2;
    private final int q3;
    private final int q4;
    private final int qlen;
    private final int[] q;
    
    NameN(final String name, final int hash, final int q1, final int q2, final int q3, final int q4, final int[] quads, final int quadLen) {
        super(name, hash);
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q = quads;
        this.qlen = quadLen;
    }
    
    public static NameN construct(final String name, final int hash, final int[] q, final int qlen) {
        if (qlen < 4) {
            throw new IllegalArgumentException();
        }
        final int q2 = q[0];
        final int q3 = q[1];
        final int q4 = q[2];
        final int q5 = q[3];
        final int rem = qlen - 4;
        int[] buf;
        if (rem > 0) {
            buf = Arrays.copyOfRange(q, 4, qlen);
        }
        else {
            buf = null;
        }
        return new NameN(name, hash, q2, q3, q4, q5, buf, qlen);
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
        return false;
    }
    
    @Override
    public boolean equals(final int[] quads, final int len) {
        if (len != this.qlen) {
            return false;
        }
        if (quads[0] != this.q1) {
            return false;
        }
        if (quads[1] != this.q2) {
            return false;
        }
        if (quads[2] != this.q3) {
            return false;
        }
        if (quads[3] != this.q4) {
            return false;
        }
        switch (len) {
            default: {
                return this._equals2(quads);
            }
            case 8: {
                if (quads[7] != this.q[3]) {
                    return false;
                }
            }
            case 7: {
                if (quads[6] != this.q[2]) {
                    return false;
                }
            }
            case 6: {
                if (quads[5] != this.q[1]) {
                    return false;
                }
            }
            case 5: {
                if (quads[4] != this.q[0]) {
                    return false;
                }
                return true;
            }
            case 4: {
                return true;
            }
        }
    }
    
    private final boolean _equals2(final int[] quads) {
        for (int end = this.qlen - 4, i = 0; i < end; ++i) {
            if (quads[i + 4] != this.q[i]) {
                return false;
            }
        }
        return true;
    }
}
