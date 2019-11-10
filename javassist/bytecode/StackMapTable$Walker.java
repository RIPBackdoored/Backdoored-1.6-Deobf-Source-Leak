package javassist.bytecode;

public static class Walker
{
    byte[] info;
    int numOfEntries;
    
    public Walker(final StackMapTable smt) {
        this(smt.get());
    }
    
    public Walker(final byte[] data) {
        super();
        this.info = data;
        this.numOfEntries = ByteArray.readU16bit(data, 0);
    }
    
    public final int size() {
        return this.numOfEntries;
    }
    
    public void parse() throws BadBytecode {
        final int n = this.numOfEntries;
        int pos = 2;
        for (int i = 0; i < n; ++i) {
            pos = this.stackMapFrames(pos, i);
        }
    }
    
    int stackMapFrames(int pos, final int nth) throws BadBytecode {
        final int type = this.info[pos] & 0xFF;
        if (type < 64) {
            this.sameFrame(pos, type);
            ++pos;
        }
        else if (type < 128) {
            pos = this.sameLocals(pos, type);
        }
        else {
            if (type < 247) {
                throw new BadBytecode("bad frame_type in StackMapTable");
            }
            if (type == 247) {
                pos = this.sameLocals(pos, type);
            }
            else if (type < 251) {
                final int offset = ByteArray.readU16bit(this.info, pos + 1);
                this.chopFrame(pos, offset, 251 - type);
                pos += 3;
            }
            else if (type == 251) {
                final int offset = ByteArray.readU16bit(this.info, pos + 1);
                this.sameFrame(pos, offset);
                pos += 3;
            }
            else if (type < 255) {
                pos = this.appendFrame(pos, type);
            }
            else {
                pos = this.fullFrame(pos);
            }
        }
        return pos;
    }
    
    public void sameFrame(final int pos, final int offsetDelta) throws BadBytecode {
    }
    
    private int sameLocals(int pos, final int type) throws BadBytecode {
        final int top = pos;
        int offset;
        if (type < 128) {
            offset = type - 64;
        }
        else {
            offset = ByteArray.readU16bit(this.info, pos + 1);
            pos += 2;
        }
        final int tag = this.info[pos + 1] & 0xFF;
        int data = 0;
        if (tag == 7 || tag == 8) {
            data = ByteArray.readU16bit(this.info, pos + 2);
            this.objectOrUninitialized(tag, data, pos + 2);
            pos += 2;
        }
        this.sameLocals(top, offset, tag, data);
        return pos + 2;
    }
    
    public void sameLocals(final int pos, final int offsetDelta, final int stackTag, final int stackData) throws BadBytecode {
    }
    
    public void chopFrame(final int pos, final int offsetDelta, final int k) throws BadBytecode {
    }
    
    private int appendFrame(final int pos, final int type) throws BadBytecode {
        final int k = type - 251;
        final int offset = ByteArray.readU16bit(this.info, pos + 1);
        final int[] tags = new int[k];
        final int[] data = new int[k];
        int p = pos + 3;
        for (int i = 0; i < k; ++i) {
            final int tag = this.info[p] & 0xFF;
            tags[i] = tag;
            if (tag == 7 || tag == 8) {
                this.objectOrUninitialized(tag, data[i] = ByteArray.readU16bit(this.info, p + 1), p + 1);
                p += 3;
            }
            else {
                data[i] = 0;
                ++p;
            }
        }
        this.appendFrame(pos, offset, tags, data);
        return p;
    }
    
    public void appendFrame(final int pos, final int offsetDelta, final int[] tags, final int[] data) throws BadBytecode {
    }
    
    private int fullFrame(final int pos) throws BadBytecode {
        final int offset = ByteArray.readU16bit(this.info, pos + 1);
        final int numOfLocals = ByteArray.readU16bit(this.info, pos + 3);
        final int[] localsTags = new int[numOfLocals];
        final int[] localsData = new int[numOfLocals];
        int p = this.verifyTypeInfo(pos + 5, numOfLocals, localsTags, localsData);
        final int numOfItems = ByteArray.readU16bit(this.info, p);
        final int[] itemsTags = new int[numOfItems];
        final int[] itemsData = new int[numOfItems];
        p = this.verifyTypeInfo(p + 2, numOfItems, itemsTags, itemsData);
        this.fullFrame(pos, offset, localsTags, localsData, itemsTags, itemsData);
        return p;
    }
    
    public void fullFrame(final int pos, final int offsetDelta, final int[] localTags, final int[] localData, final int[] stackTags, final int[] stackData) throws BadBytecode {
    }
    
    private int verifyTypeInfo(int pos, final int n, final int[] tags, final int[] data) {
        for (int i = 0; i < n; ++i) {
            final int tag = this.info[pos++] & 0xFF;
            tags[i] = tag;
            if (tag == 7 || tag == 8) {
                this.objectOrUninitialized(tag, data[i] = ByteArray.readU16bit(this.info, pos), pos);
                pos += 2;
            }
        }
        return pos;
    }
    
    public void objectOrUninitialized(final int tag, final int data, final int pos) {
    }
}
