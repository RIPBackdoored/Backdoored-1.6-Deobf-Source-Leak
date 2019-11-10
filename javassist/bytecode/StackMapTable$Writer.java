package javassist.bytecode;

import java.io.*;

public static class Writer
{
    ByteArrayOutputStream output;
    int numOfEntries;
    
    public Writer(final int size) {
        super();
        this.output = new ByteArrayOutputStream(size);
        this.numOfEntries = 0;
        this.output.write(0);
        this.output.write(0);
    }
    
    public byte[] toByteArray() {
        final byte[] b = this.output.toByteArray();
        ByteArray.write16bit(this.numOfEntries, b, 0);
        return b;
    }
    
    public StackMapTable toStackMapTable(final ConstPool cp) {
        return new StackMapTable(cp, this.toByteArray());
    }
    
    public void sameFrame(final int offsetDelta) {
        ++this.numOfEntries;
        if (offsetDelta < 64) {
            this.output.write(offsetDelta);
        }
        else {
            this.output.write(251);
            this.write16(offsetDelta);
        }
    }
    
    public void sameLocals(final int offsetDelta, final int tag, final int data) {
        ++this.numOfEntries;
        if (offsetDelta < 64) {
            this.output.write(offsetDelta + 64);
        }
        else {
            this.output.write(247);
            this.write16(offsetDelta);
        }
        this.writeTypeInfo(tag, data);
    }
    
    public void chopFrame(final int offsetDelta, final int k) {
        ++this.numOfEntries;
        this.output.write(251 - k);
        this.write16(offsetDelta);
    }
    
    public void appendFrame(final int offsetDelta, final int[] tags, final int[] data) {
        ++this.numOfEntries;
        final int k = tags.length;
        this.output.write(k + 251);
        this.write16(offsetDelta);
        for (int i = 0; i < k; ++i) {
            this.writeTypeInfo(tags[i], data[i]);
        }
    }
    
    public void fullFrame(final int offsetDelta, final int[] localTags, final int[] localData, final int[] stackTags, final int[] stackData) {
        ++this.numOfEntries;
        this.output.write(255);
        this.write16(offsetDelta);
        int n = localTags.length;
        this.write16(n);
        for (int i = 0; i < n; ++i) {
            this.writeTypeInfo(localTags[i], localData[i]);
        }
        n = stackTags.length;
        this.write16(n);
        for (int i = 0; i < n; ++i) {
            this.writeTypeInfo(stackTags[i], stackData[i]);
        }
    }
    
    private void writeTypeInfo(final int tag, final int data) {
        this.output.write(tag);
        if (tag == 7 || tag == 8) {
            this.write16(data);
        }
    }
    
    private void write16(final int value) {
        this.output.write(value >>> 8 & 0xFF);
        this.output.write(value & 0xFF);
    }
}
