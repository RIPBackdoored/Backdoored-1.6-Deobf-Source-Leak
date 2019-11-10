package javassist.bytecode.stackmap;

public static class Maker extends BasicBlock.Maker
{
    public Maker() {
        super();
    }
    
    @Override
    protected BasicBlock makeBlock(final int pos) {
        return new TypedBlock(pos);
    }
    
    @Override
    protected BasicBlock[] makeArray(final int size) {
        return new TypedBlock[size];
    }
}
