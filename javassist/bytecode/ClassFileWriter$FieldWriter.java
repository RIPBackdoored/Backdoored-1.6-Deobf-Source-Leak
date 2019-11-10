package javassist.bytecode;

import java.io.*;

public static final class FieldWriter
{
    protected ByteStream output;
    protected ConstPoolWriter constPool;
    private int fieldCount;
    
    FieldWriter(final ConstPoolWriter cp) {
        super();
        this.output = new ByteStream(128);
        this.constPool = cp;
        this.fieldCount = 0;
    }
    
    public void add(final int accessFlags, final String name, final String descriptor, final AttributeWriter aw) {
        final int nameIndex = this.constPool.addUtf8Info(name);
        final int descIndex = this.constPool.addUtf8Info(descriptor);
        this.add(accessFlags, nameIndex, descIndex, aw);
    }
    
    public void add(final int accessFlags, final int name, final int descriptor, final AttributeWriter aw) {
        ++this.fieldCount;
        this.output.writeShort(accessFlags);
        this.output.writeShort(name);
        this.output.writeShort(descriptor);
        ClassFileWriter.writeAttribute(this.output, aw, 0);
    }
    
    int size() {
        return this.fieldCount;
    }
    
    int dataSize() {
        return this.output.size();
    }
    
    void write(final OutputStream out) throws IOException {
        this.output.writeTo(out);
    }
}
