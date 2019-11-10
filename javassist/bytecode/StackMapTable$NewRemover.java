package javassist.bytecode;

static class NewRemover extends SimpleCopy
{
    int posOfNew;
    
    public NewRemover(final byte[] data, final int pos) {
        super(data);
        this.posOfNew = pos;
    }
    
    @Override
    public void sameLocals(final int pos, final int offsetDelta, final int stackTag, final int stackData) {
        if (stackTag == 8 && stackData == this.posOfNew) {
            super.sameFrame(pos, offsetDelta);
        }
        else {
            super.sameLocals(pos, offsetDelta, stackTag, stackData);
        }
    }
    
    @Override
    public void fullFrame(final int pos, final int offsetDelta, final int[] localTags, final int[] localData, int[] stackTags, int[] stackData) {
        for (int n = stackTags.length - 1, i = 0; i < n; ++i) {
            if (stackTags[i] == 8 && stackData[i] == this.posOfNew && stackTags[i + 1] == 8 && stackData[i + 1] == this.posOfNew) {
                final int[] stackTags2 = new int[++n - 2];
                final int[] stackData2 = new int[n - 2];
                int k = 0;
                for (int j = 0; j < n; ++j) {
                    if (j == i) {
                        ++j;
                    }
                    else {
                        stackTags2[k] = stackTags[j];
                        stackData2[k++] = stackData[j];
                    }
                }
                stackTags = stackTags2;
                stackData = stackData2;
                break;
            }
        }
        super.fullFrame(pos, offsetDelta, localTags, localData, stackTags, stackData);
    }
}
