package javassist.bytecode;

static class SimpleCopy extends Walker
{
    Writer writer;
    
    SimpleCopy(final StackMap map) {
        super(map);
        this.writer = new Writer();
    }
    
    byte[] doit() {
        this.visit();
        return this.writer.toByteArray();
    }
    
    @Override
    public void visit() {
        final int num = ByteArray.readU16bit(this.info, 0);
        this.writer.write16bit(num);
        super.visit();
    }
    
    @Override
    public int locals(final int pos, final int offset, final int num) {
        this.writer.write16bit(offset);
        return super.locals(pos, offset, num);
    }
    
    @Override
    public int typeInfoArray(final int pos, final int offset, final int num, final boolean isLocals) {
        this.writer.write16bit(num);
        return super.typeInfoArray(pos, offset, num, isLocals);
    }
    
    @Override
    public void typeInfo(final int pos, final byte tag) {
        this.writer.writeVerifyTypeInfo(tag, 0);
    }
    
    @Override
    public void objectVariable(final int pos, final int clazz) {
        this.writer.writeVerifyTypeInfo(7, clazz);
    }
    
    @Override
    public void uninitialized(final int pos, final int offset) {
        this.writer.writeVerifyTypeInfo(8, offset);
    }
}
