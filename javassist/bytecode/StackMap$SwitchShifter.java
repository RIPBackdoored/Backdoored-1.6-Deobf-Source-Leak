package javassist.bytecode;

static class SwitchShifter extends Walker
{
    private int where;
    private int gap;
    
    public SwitchShifter(final StackMap smt, final int where, final int gap) {
        super(smt);
        this.where = where;
        this.gap = gap;
    }
    
    @Override
    public int locals(final int pos, final int offset, final int num) {
        if (this.where == pos + offset) {
            ByteArray.write16bit(offset - this.gap, this.info, pos - 4);
        }
        else if (this.where == pos) {
            ByteArray.write16bit(offset + this.gap, this.info, pos - 4);
        }
        return super.locals(pos, offset, num);
    }
}
