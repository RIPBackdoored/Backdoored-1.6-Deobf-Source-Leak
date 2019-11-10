package javassist.bytecode;

static class Shifter extends Walker
{
    private StackMapTable stackMap;
    int where;
    int gap;
    int position;
    byte[] updatedInfo;
    boolean exclusive;
    
    public Shifter(final StackMapTable smt, final int where, final int gap, final boolean exclusive) {
        super(smt);
        this.stackMap = smt;
        this.where = where;
        this.gap = gap;
        this.position = 0;
        this.updatedInfo = null;
        this.exclusive = exclusive;
    }
    
    public void doit() throws BadBytecode {
        this.parse();
        if (this.updatedInfo != null) {
            this.stackMap.set(this.updatedInfo);
        }
    }
    
    @Override
    public void sameFrame(final int pos, final int offsetDelta) {
        this.update(pos, offsetDelta, 0, 251);
    }
    
    @Override
    public void sameLocals(final int pos, final int offsetDelta, final int stackTag, final int stackData) {
        this.update(pos, offsetDelta, 64, 247);
    }
    
    void update(final int pos, final int offsetDelta, final int base, final int entry) {
        final int oldPos = this.position;
        this.position = oldPos + offsetDelta + ((oldPos != 0) ? 1 : 0);
        boolean match;
        if (this.exclusive) {
            match = (oldPos < this.where && this.where <= this.position);
        }
        else {
            match = (oldPos <= this.where && this.where < this.position);
        }
        if (match) {
            final int newDelta = offsetDelta + this.gap;
            this.position += this.gap;
            if (newDelta < 64) {
                this.info[pos] = (byte)(newDelta + base);
            }
            else if (offsetDelta < 64) {
                final byte[] newinfo = insertGap(this.info, pos, 2);
                newinfo[pos] = (byte)entry;
                ByteArray.write16bit(newDelta, newinfo, pos + 1);
                this.updatedInfo = newinfo;
            }
            else {
                ByteArray.write16bit(newDelta, this.info, pos + 1);
            }
        }
    }
    
    static byte[] insertGap(final byte[] info, final int where, final int gap) {
        final int len = info.length;
        final byte[] newinfo = new byte[len + gap];
        for (int i = 0; i < len; ++i) {
            newinfo[i + ((i < where) ? 0 : gap)] = info[i];
        }
        return newinfo;
    }
    
    @Override
    public void chopFrame(final int pos, final int offsetDelta, final int k) {
        this.update(pos, offsetDelta);
    }
    
    @Override
    public void appendFrame(final int pos, final int offsetDelta, final int[] tags, final int[] data) {
        this.update(pos, offsetDelta);
    }
    
    @Override
    public void fullFrame(final int pos, final int offsetDelta, final int[] localTags, final int[] localData, final int[] stackTags, final int[] stackData) {
        this.update(pos, offsetDelta);
    }
    
    void update(final int pos, final int offsetDelta) {
        final int oldPos = this.position;
        this.position = oldPos + offsetDelta + ((oldPos != 0) ? 1 : 0);
        boolean match;
        if (this.exclusive) {
            match = (oldPos < this.where && this.where <= this.position);
        }
        else {
            match = (oldPos <= this.where && this.where < this.position);
        }
        if (match) {
            final int newDelta = offsetDelta + this.gap;
            ByteArray.write16bit(newDelta, this.info, pos + 1);
            this.position += this.gap;
        }
    }
}
