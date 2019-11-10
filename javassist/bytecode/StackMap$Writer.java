package javassist.bytecode;

import java.io.*;

public static class Writer
{
    private ByteArrayOutputStream output;
    
    public Writer() {
        super();
        this.output = new ByteArrayOutputStream();
    }
    
    public byte[] toByteArray() {
        return this.output.toByteArray();
    }
    
    public StackMap toStackMap(final ConstPool cp) {
        return new StackMap(cp, this.output.toByteArray());
    }
    
    public void writeVerifyTypeInfo(final int tag, final int data) {
        this.output.write(tag);
        if (tag == 7 || tag == 8) {
            this.write16bit(data);
        }
    }
    
    public void write16bit(final int value) {
        this.output.write(value >>> 8 & 0xFF);
        this.output.write(value & 0xFF);
    }
}
