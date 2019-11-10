package javassist.bytecode;

static class Lookup extends Switcher
{
    int[] matches;
    
    Lookup(final int pos, final int defaultByte, final int[] matches, final int[] offsets, final Pointers ptrs) {
        super(pos, defaultByte, offsets, ptrs);
        this.matches = matches;
    }
    
    @Override
    int write2(int dest, final byte[] newcode) {
        final int n = this.matches.length;
        ByteArray.write32bit(n, newcode, dest);
        dest += 4;
        for (int i = 0; i < n; ++i) {
            ByteArray.write32bit(this.matches[i], newcode, dest);
            ByteArray.write32bit(this.offsets[i], newcode, dest + 4);
            dest += 8;
        }
        return 4 + 8 * n;
    }
    
    @Override
    int tableSize() {
        return 4 + 8 * this.matches.length;
    }
}
