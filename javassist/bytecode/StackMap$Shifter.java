package javassist.bytecode;

static class Shifter extends Walker
{
    private int where;
    private int gap;
    private boolean exclusive;
    
    public Shifter(final StackMap smt, final int where, final int gap, final boolean exclusive) {
        super(smt);
        this.where = where;
        this.gap = gap;
        this.exclusive = exclusive;
    }
    
    @Override
    public int locals(final int pos, final int offset, final int num) {
        if (this.exclusive) {
            if (this.where > offset) {
                return super.locals(pos, offset, num);
            }
        }
        else if (this.where >= offset) {
            return super.locals(pos, offset, num);
        }
        ByteArray.write16bit(offset + this.gap, this.info, pos - 4);
        return super.locals(pos, offset, num);
    }
    
    @Override
    public void uninitialized(final int pos, final int offset) {
        if (this.where <= offset) {
            ByteArray.write16bit(offset + this.gap, this.info, pos + 1);
        }
    }
}
