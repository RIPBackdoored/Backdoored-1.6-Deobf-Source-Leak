package javassist.bytecode;

static class SimpleCopy extends Walker
{
    private Writer writer;
    
    public SimpleCopy(final byte[] data) {
        super(data);
        this.writer = new Writer(data.length);
    }
    
    public byte[] doit() throws BadBytecode {
        this.parse();
        return this.writer.toByteArray();
    }
    
    @Override
    public void sameFrame(final int pos, final int offsetDelta) {
        this.writer.sameFrame(offsetDelta);
    }
    
    @Override
    public void sameLocals(final int pos, final int offsetDelta, final int stackTag, final int stackData) {
        this.writer.sameLocals(offsetDelta, stackTag, this.copyData(stackTag, stackData));
    }
    
    @Override
    public void chopFrame(final int pos, final int offsetDelta, final int k) {
        this.writer.chopFrame(offsetDelta, k);
    }
    
    @Override
    public void appendFrame(final int pos, final int offsetDelta, final int[] tags, final int[] data) {
        this.writer.appendFrame(offsetDelta, tags, this.copyData(tags, data));
    }
    
    @Override
    public void fullFrame(final int pos, final int offsetDelta, final int[] localTags, final int[] localData, final int[] stackTags, final int[] stackData) {
        this.writer.fullFrame(offsetDelta, localTags, this.copyData(localTags, localData), stackTags, this.copyData(stackTags, stackData));
    }
    
    protected int copyData(final int tag, final int data) {
        return data;
    }
    
    protected int[] copyData(final int[] tags, final int[] data) {
        return data;
    }
}
