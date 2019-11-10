package javassist.bytecode;

static class Jump32 extends Branch
{
    int offset;
    
    Jump32(final int p, final int off) {
        super(p);
        this.offset = off;
    }
    
    @Override
    void shift(final int where, final int gapLength, final boolean exclusive) {
        this.offset = Branch.shiftOffset(this.pos, this.offset, where, gapLength, exclusive);
        super.shift(where, gapLength, exclusive);
    }
    
    @Override
    int write(final int src, final byte[] code, final int dest, final byte[] newcode) {
        newcode[dest] = code[src];
        ByteArray.write32bit(this.offset, newcode, dest + 1);
        return 5;
    }
}
