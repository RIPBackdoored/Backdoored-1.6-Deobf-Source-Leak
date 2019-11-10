package javassist.bytecode;

abstract static class Branch16 extends Branch
{
    int offset;
    int state;
    static final int BIT16 = 0;
    static final int EXPAND = 1;
    static final int BIT32 = 2;
    
    Branch16(final int p, final int off) {
        super(p);
        this.offset = off;
        this.state = 0;
    }
    
    @Override
    void shift(final int where, final int gapLength, final boolean exclusive) {
        this.offset = Branch.shiftOffset(this.pos, this.offset, where, gapLength, exclusive);
        super.shift(where, gapLength, exclusive);
        if (this.state == 0 && (this.offset < -32768 || 32767 < this.offset)) {
            this.state = 1;
        }
    }
    
    @Override
    boolean expanded() {
        if (this.state == 1) {
            this.state = 2;
            return true;
        }
        return false;
    }
    
    @Override
    abstract int deltaSize();
    
    abstract void write32(final int p0, final byte[] p1, final int p2, final byte[] p3);
    
    @Override
    int write(final int src, final byte[] code, final int dest, final byte[] newcode) {
        if (this.state == 2) {
            this.write32(src, code, dest, newcode);
        }
        else {
            newcode[dest] = code[src];
            ByteArray.write16bit(this.offset, newcode, dest + 1);
        }
        return 3;
    }
}
