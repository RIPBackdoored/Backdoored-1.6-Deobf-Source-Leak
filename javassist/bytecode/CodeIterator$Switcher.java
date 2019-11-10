package javassist.bytecode;

abstract static class Switcher extends Branch
{
    int gap;
    int defaultByte;
    int[] offsets;
    Pointers pointers;
    
    Switcher(final int pos, final int defaultByte, final int[] offsets, final Pointers ptrs) {
        super(pos);
        this.gap = 3 - (pos & 0x3);
        this.defaultByte = defaultByte;
        this.offsets = offsets;
        this.pointers = ptrs;
    }
    
    @Override
    void shift(final int where, final int gapLength, final boolean exclusive) {
        final int p = this.pos;
        this.defaultByte = Branch.shiftOffset(p, this.defaultByte, where, gapLength, exclusive);
        for (int num = this.offsets.length, i = 0; i < num; ++i) {
            this.offsets[i] = Branch.shiftOffset(p, this.offsets[i], where, gapLength, exclusive);
        }
        super.shift(where, gapLength, exclusive);
    }
    
    @Override
    int gapChanged() {
        final int newGap = 3 - (this.pos & 0x3);
        if (newGap > this.gap) {
            final int diff = newGap - this.gap;
            this.gap = newGap;
            return diff;
        }
        return 0;
    }
    
    @Override
    int deltaSize() {
        return this.gap - (3 - (this.orgPos & 0x3));
    }
    
    @Override
    int write(final int src, final byte[] code, int dest, final byte[] newcode) throws BadBytecode {
        int padding = 3 - (this.pos & 0x3);
        int nops = this.gap - padding;
        final int bytecodeSize = 5 + (3 - (this.orgPos & 0x3)) + this.tableSize();
        if (nops > 0) {
            this.adjustOffsets(bytecodeSize, nops);
        }
        newcode[dest++] = code[src];
        while (padding-- > 0) {
            newcode[dest++] = 0;
        }
        ByteArray.write32bit(this.defaultByte, newcode, dest);
        final int size = this.write2(dest + 4, newcode);
        dest += size + 4;
        while (nops-- > 0) {
            newcode[dest++] = 0;
        }
        return 5 + (3 - (this.orgPos & 0x3)) + size;
    }
    
    abstract int write2(final int p0, final byte[] p1);
    
    abstract int tableSize();
    
    void adjustOffsets(final int size, final int nops) throws BadBytecode {
        this.pointers.shiftForSwitch(this.pos + size, nops);
        if (this.defaultByte == size) {
            this.defaultByte -= nops;
        }
        for (int i = 0; i < this.offsets.length; ++i) {
            if (this.offsets[i] == size) {
                final int[] offsets = this.offsets;
                final int n = i;
                offsets[n] -= nops;
            }
        }
    }
}
