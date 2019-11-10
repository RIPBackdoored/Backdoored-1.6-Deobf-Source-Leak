package javassist.bytecode;

static class OffsetShifter extends Walker
{
    int where;
    int gap;
    
    public OffsetShifter(final StackMapTable smt, final int where, final int gap) {
        super(smt);
        this.where = where;
        this.gap = gap;
    }
    
    @Override
    public void objectOrUninitialized(final int tag, final int data, final int pos) {
        if (tag == 8 && this.where <= data) {
            ByteArray.write16bit(data + this.gap, this.info, pos);
        }
    }
}
