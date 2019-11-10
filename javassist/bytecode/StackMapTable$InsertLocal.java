package javassist.bytecode;

static class InsertLocal extends SimpleCopy
{
    private int varIndex;
    private int varTag;
    private int varData;
    
    public InsertLocal(final byte[] data, final int varIndex, final int varTag, final int varData) {
        super(data);
        this.varIndex = varIndex;
        this.varTag = varTag;
        this.varData = varData;
    }
    
    @Override
    public void fullFrame(final int pos, final int offsetDelta, final int[] localTags, final int[] localData, final int[] stackTags, final int[] stackData) {
        final int len = localTags.length;
        if (len < this.varIndex) {
            super.fullFrame(pos, offsetDelta, localTags, localData, stackTags, stackData);
            return;
        }
        final int typeSize = (this.varTag == 4 || this.varTag == 3) ? 2 : 1;
        final int[] localTags2 = new int[len + typeSize];
        final int[] localData2 = new int[len + typeSize];
        final int index = this.varIndex;
        int j = 0;
        for (int i = 0; i < len; ++i) {
            if (j == index) {
                j += typeSize;
            }
            localTags2[j] = localTags[i];
            localData2[j++] = localData[i];
        }
        localTags2[index] = this.varTag;
        localData2[index] = this.varData;
        if (typeSize > 1) {
            localData2[index + 1] = (localTags2[index + 1] = 0);
        }
        super.fullFrame(pos, offsetDelta, localTags2, localData2, stackTags, stackData);
    }
}
