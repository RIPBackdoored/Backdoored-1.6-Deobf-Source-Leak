package javassist.bytecode;

public static class Walker
{
    byte[] info;
    
    public Walker(final StackMap sm) {
        super();
        this.info = sm.get();
    }
    
    public void visit() {
        final int num = ByteArray.readU16bit(this.info, 0);
        int pos = 2;
        for (int i = 0; i < num; ++i) {
            final int offset = ByteArray.readU16bit(this.info, pos);
            final int numLoc = ByteArray.readU16bit(this.info, pos + 2);
            pos = this.locals(pos + 4, offset, numLoc);
            final int numStack = ByteArray.readU16bit(this.info, pos);
            pos = this.stack(pos + 2, offset, numStack);
        }
    }
    
    public int locals(final int pos, final int offset, final int num) {
        return this.typeInfoArray(pos, offset, num, true);
    }
    
    public int stack(final int pos, final int offset, final int num) {
        return this.typeInfoArray(pos, offset, num, false);
    }
    
    public int typeInfoArray(int pos, final int offset, final int num, final boolean isLocals) {
        for (int k = 0; k < num; ++k) {
            pos = this.typeInfoArray2(k, pos);
        }
        return pos;
    }
    
    int typeInfoArray2(final int k, int pos) {
        final byte tag = this.info[pos];
        if (tag == 7) {
            final int clazz = ByteArray.readU16bit(this.info, pos + 1);
            this.objectVariable(pos, clazz);
            pos += 3;
        }
        else if (tag == 8) {
            final int offsetOfNew = ByteArray.readU16bit(this.info, pos + 1);
            this.uninitialized(pos, offsetOfNew);
            pos += 3;
        }
        else {
            this.typeInfo(pos, tag);
            ++pos;
        }
        return pos;
    }
    
    public void typeInfo(final int pos, final byte tag) {
    }
    
    public void objectVariable(final int pos, final int clazz) {
    }
    
    public void uninitialized(final int pos, final int offset) {
    }
}
