package javassist.bytecode;

static class InsertLocal extends SimpleCopy
{
    private int varIndex;
    private int varTag;
    private int varData;
    
    InsertLocal(final StackMap map, final int varIndex, final int varTag, final int varData) {
        super(map);
        this.varIndex = varIndex;
        this.varTag = varTag;
        this.varData = varData;
    }
    
    @Override
    public int typeInfoArray(int pos, final int offset, final int num, final boolean isLocals) {
        if (!isLocals || num < this.varIndex) {
            return super.typeInfoArray(pos, offset, num, isLocals);
        }
        this.writer.write16bit(num + 1);
        for (int k = 0; k < num; ++k) {
            if (k == this.varIndex) {
                this.writeVarTypeInfo();
            }
            pos = this.typeInfoArray2(k, pos);
        }
        if (num == this.varIndex) {
            this.writeVarTypeInfo();
        }
        return pos;
    }
    
    private void writeVarTypeInfo() {
        if (this.varTag == 7) {
            this.writer.writeVerifyTypeInfo(7, this.varData);
        }
        else if (this.varTag == 8) {
            this.writer.writeVerifyTypeInfo(8, this.varData);
        }
        else {
            this.writer.writeVerifyTypeInfo(this.varTag, 0);
        }
    }
}
