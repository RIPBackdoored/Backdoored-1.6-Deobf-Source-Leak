package javassist.bytecode;

static class Jump16 extends Branch16
{
    Jump16(final int p, final int off) {
        super(p, off);
    }
    
    @Override
    int deltaSize() {
        return (this.state == 2) ? 2 : 0;
    }
    
    @Override
    void write32(final int src, final byte[] code, final int dest, final byte[] newcode) {
        newcode[dest] = (byte)(((code[src] & 0xFF) == 0xA7) ? 200 : 201);
        ByteArray.write32bit(this.offset, newcode, dest + 1);
    }
}
