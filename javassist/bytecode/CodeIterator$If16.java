package javassist.bytecode;

static class If16 extends Branch16
{
    If16(final int p, final int off) {
        super(p, off);
    }
    
    @Override
    int deltaSize() {
        return (this.state == 2) ? 5 : 0;
    }
    
    @Override
    void write32(final int src, final byte[] code, final int dest, final byte[] newcode) {
        newcode[dest] = (byte)this.opcode(code[src] & 0xFF);
        newcode[dest + 1] = 0;
        newcode[dest + 2] = 8;
        newcode[dest + 3] = -56;
        ByteArray.write32bit(this.offset - 3, newcode, dest + 4);
    }
    
    int opcode(final int op) {
        if (op == 198) {
            return 199;
        }
        if (op == 199) {
            return 198;
        }
        if ((op - 153 & 0x1) == 0x0) {
            return op + 1;
        }
        return op - 1;
    }
}
