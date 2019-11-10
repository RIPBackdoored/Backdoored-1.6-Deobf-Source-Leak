package javassist.bytecode;

static class LdcW extends Branch
{
    int index;
    boolean state;
    
    LdcW(final int p, final int i) {
        super(p);
        this.index = i;
        this.state = true;
    }
    
    @Override
    boolean expanded() {
        if (this.state) {
            this.state = false;
            return true;
        }
        return false;
    }
    
    @Override
    int deltaSize() {
        return 1;
    }
    
    @Override
    int write(final int srcPos, final byte[] code, final int destPos, final byte[] newcode) {
        newcode[destPos] = 19;
        ByteArray.write16bit(this.index, newcode, destPos + 1);
        return 2;
    }
}
