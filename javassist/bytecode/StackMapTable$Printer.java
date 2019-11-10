package javassist.bytecode;

import java.io.*;

static class Printer extends Walker
{
    private PrintWriter writer;
    private int offset;
    
    public static void print(final StackMapTable smt, final PrintWriter writer) {
        try {
            new Printer(smt.get(), writer).parse();
        }
        catch (BadBytecode e) {
            writer.println(e.getMessage());
        }
    }
    
    Printer(final byte[] data, final PrintWriter pw) {
        super(data);
        this.writer = pw;
        this.offset = -1;
    }
    
    @Override
    public void sameFrame(final int pos, final int offsetDelta) {
        this.offset += offsetDelta + 1;
        this.writer.println(this.offset + " same frame: " + offsetDelta);
    }
    
    @Override
    public void sameLocals(final int pos, final int offsetDelta, final int stackTag, final int stackData) {
        this.offset += offsetDelta + 1;
        this.writer.println(this.offset + " same locals: " + offsetDelta);
        this.printTypeInfo(stackTag, stackData);
    }
    
    @Override
    public void chopFrame(final int pos, final int offsetDelta, final int k) {
        this.offset += offsetDelta + 1;
        this.writer.println(this.offset + " chop frame: " + offsetDelta + ",    " + k + " last locals");
    }
    
    @Override
    public void appendFrame(final int pos, final int offsetDelta, final int[] tags, final int[] data) {
        this.offset += offsetDelta + 1;
        this.writer.println(this.offset + " append frame: " + offsetDelta);
        for (int i = 0; i < tags.length; ++i) {
            this.printTypeInfo(tags[i], data[i]);
        }
    }
    
    @Override
    public void fullFrame(final int pos, final int offsetDelta, final int[] localTags, final int[] localData, final int[] stackTags, final int[] stackData) {
        this.offset += offsetDelta + 1;
        this.writer.println(this.offset + " full frame: " + offsetDelta);
        this.writer.println("[locals]");
        for (int i = 0; i < localTags.length; ++i) {
            this.printTypeInfo(localTags[i], localData[i]);
        }
        this.writer.println("[stack]");
        for (int i = 0; i < stackTags.length; ++i) {
            this.printTypeInfo(stackTags[i], stackData[i]);
        }
    }
    
    private void printTypeInfo(final int tag, final int data) {
        String msg = null;
        switch (tag) {
            case 0: {
                msg = "top";
                break;
            }
            case 1: {
                msg = "integer";
                break;
            }
            case 2: {
                msg = "float";
                break;
            }
            case 3: {
                msg = "double";
                break;
            }
            case 4: {
                msg = "long";
                break;
            }
            case 5: {
                msg = "null";
                break;
            }
            case 6: {
                msg = "this";
                break;
            }
            case 7: {
                msg = "object (cpool_index " + data + ")";
                break;
            }
            case 8: {
                msg = "uninitialized (offset " + data + ")";
                break;
            }
        }
        this.writer.print("    ");
        this.writer.println(msg);
    }
}
