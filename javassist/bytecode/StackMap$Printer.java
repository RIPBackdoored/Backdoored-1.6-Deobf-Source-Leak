package javassist.bytecode;

import java.io.*;

static class Printer extends Walker
{
    private PrintWriter writer;
    
    public Printer(final StackMap map, final PrintWriter out) {
        super(map);
        this.writer = out;
    }
    
    public void print() {
        final int num = ByteArray.readU16bit(this.info, 0);
        this.writer.println(num + " entries");
        this.visit();
    }
    
    @Override
    public int locals(final int pos, final int offset, final int num) {
        this.writer.println("  * offset " + offset);
        return super.locals(pos, offset, num);
    }
}
