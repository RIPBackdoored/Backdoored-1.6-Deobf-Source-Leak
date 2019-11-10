package javassist.bytecode;

abstract static class Branch
{
    int pos;
    int orgPos;
    
    Branch(final int p) {
        super();
        this.orgPos = p;
        this.pos = p;
    }
    
    void shift(final int where, final int gapLength, final boolean exclusive) {
        if (where < this.pos || (where == this.pos && exclusive)) {
            this.pos += gapLength;
        }
    }
    
    static int shiftOffset(final int i, int offset, final int where, final int gapLength, final boolean exclusive) {
        final int target = i + offset;
        if (i < where) {
            if (where < target || (exclusive && where == target)) {
                offset += gapLength;
            }
        }
        else if (i == where) {
            if (target < where && exclusive) {
                offset -= gapLength;
            }
            else if (where < target && !exclusive) {
                offset += gapLength;
            }
        }
        else if (target < where || (!exclusive && where == target)) {
            offset -= gapLength;
        }
        return offset;
    }
    
    boolean expanded() {
        return false;
    }
    
    int gapChanged() {
        return 0;
    }
    
    int deltaSize() {
        return 0;
    }
    
    abstract int write(final int p0, final byte[] p1, final int p2, final byte[] p3) throws BadBytecode;
}
