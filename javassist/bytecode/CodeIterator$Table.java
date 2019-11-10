package javassist.bytecode;

static class Table extends Switcher
{
    int low;
    int high;
    
    Table(final int pos, final int defaultByte, final int low, final int high, final int[] offsets, final Pointers ptrs) {
        super(pos, defaultByte, offsets, ptrs);
        this.low = low;
        this.high = high;
    }
    
    @Override
    int write2(int dest, final byte[] newcode) {
        ByteArray.write32bit(this.low, newcode, dest);
        ByteArray.write32bit(this.high, newcode, dest + 4);
        final int n = this.offsets.length;
        dest += 8;
        for (int i = 0; i < n; ++i) {
            ByteArray.write32bit(this.offsets[i], newcode, dest);
            dest += 4;
        }
        return 8 + 4 * n;
    }
    
    @Override
    int tableSize() {
        return 8 + 4 * this.offsets.length;
    }
}
