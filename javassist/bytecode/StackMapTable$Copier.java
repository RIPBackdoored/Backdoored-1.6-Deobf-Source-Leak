package javassist.bytecode;

import java.util.*;

static class Copier extends SimpleCopy
{
    private ConstPool srcPool;
    private ConstPool destPool;
    private Map classnames;
    
    public Copier(final ConstPool src, final byte[] data, final ConstPool dest, final Map names) {
        super(data);
        this.srcPool = src;
        this.destPool = dest;
        this.classnames = names;
    }
    
    @Override
    protected int copyData(final int tag, final int data) {
        if (tag == 7) {
            return this.srcPool.copy(data, this.destPool, this.classnames);
        }
        return data;
    }
    
    @Override
    protected int[] copyData(final int[] tags, final int[] data) {
        final int[] newData = new int[data.length];
        for (int i = 0; i < data.length; ++i) {
            if (tags[i] == 7) {
                newData[i] = this.srcPool.copy(data[i], this.destPool, this.classnames);
            }
            else {
                newData[i] = data[i];
            }
        }
        return newData;
    }
}
