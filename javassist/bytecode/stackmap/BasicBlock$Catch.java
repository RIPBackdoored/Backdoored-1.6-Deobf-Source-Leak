package javassist.bytecode.stackmap;

public static class Catch
{
    public Catch next;
    public BasicBlock body;
    public int typeIndex;
    
    Catch(final BasicBlock b, final int i, final Catch c) {
        super();
        this.body = b;
        this.typeIndex = i;
        this.next = c;
    }
}
