package javassist.bytecode;

static class SwitchShifter extends Shifter
{
    SwitchShifter(final StackMapTable smt, final int where, final int gap) {
        super(smt, where, gap, false);
    }
    
    @Override
    void update(final int pos, final int offsetDelta, final int base, final int entry) {
        final int oldPos = this.position;
        this.position = oldPos + offsetDelta + ((oldPos != 0) ? 1 : 0);
        int newDelta = offsetDelta;
        if (this.where == this.position) {
            newDelta = offsetDelta - this.gap;
        }
        else {
            if (this.where != oldPos) {
                return;
            }
            newDelta = offsetDelta + this.gap;
        }
        if (offsetDelta < 64) {
            if (newDelta < 64) {
                this.info[pos] = (byte)(newDelta + base);
            }
            else {
                final byte[] newinfo = Shifter.insertGap(this.info, pos, 2);
                newinfo[pos] = (byte)entry;
                ByteArray.write16bit(newDelta, newinfo, pos + 1);
                this.updatedInfo = newinfo;
            }
        }
        else if (newDelta < 64) {
            final byte[] newinfo = deleteGap(this.info, pos, 2);
            newinfo[pos] = (byte)(newDelta + base);
            this.updatedInfo = newinfo;
        }
        else {
            ByteArray.write16bit(newDelta, this.info, pos + 1);
        }
    }
    
    static byte[] deleteGap(final byte[] info, int where, final int gap) {
        where += gap;
        final int len = info.length;
        final byte[] newinfo = new byte[len - gap];
        for (int i = 0; i < len; ++i) {
            newinfo[i - ((i < where) ? 0 : gap)] = info[i];
        }
        return newinfo;
    }
    
    @Override
    void update(final int pos, final int offsetDelta) {
        final int oldPos = this.position;
        this.position = oldPos + offsetDelta + ((oldPos != 0) ? 1 : 0);
        int newDelta = offsetDelta;
        if (this.where == this.position) {
            newDelta = offsetDelta - this.gap;
        }
        else {
            if (this.where != oldPos) {
                return;
            }
            newDelta = offsetDelta + this.gap;
        }
        ByteArray.write16bit(newDelta, this.info, pos + 1);
    }
}
