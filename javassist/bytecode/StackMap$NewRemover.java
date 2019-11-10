package javassist.bytecode;

static class NewRemover extends SimpleCopy
{
    int posOfNew;
    
    NewRemover(final StackMap map, final int where) {
        super(map);
        this.posOfNew = where;
    }
    
    @Override
    public int stack(final int pos, final int offset, final int num) {
        return this.stackTypeInfoArray(pos, offset, num);
    }
    
    private int stackTypeInfoArray(int pos, final int offset, final int num) {
        int p = pos;
        int count = 0;
        for (int k = 0; k < num; ++k) {
            final byte tag = this.info[p];
            if (tag == 7) {
                p += 3;
            }
            else if (tag == 8) {
                final int offsetOfNew = ByteArray.readU16bit(this.info, p + 1);
                if (offsetOfNew == this.posOfNew) {
                    ++count;
                }
                p += 3;
            }
            else {
                ++p;
            }
        }
        this.writer.write16bit(num - count);
        for (int k = 0; k < num; ++k) {
            final byte tag = this.info[pos];
            if (tag == 7) {
                final int clazz = ByteArray.readU16bit(this.info, pos + 1);
                this.objectVariable(pos, clazz);
                pos += 3;
            }
            else if (tag == 8) {
                final int offsetOfNew = ByteArray.readU16bit(this.info, pos + 1);
                if (offsetOfNew != this.posOfNew) {
                    this.uninitialized(pos, offsetOfNew);
                }
                pos += 3;
            }
            else {
                this.typeInfo(pos, tag);
                ++pos;
            }
        }
        return pos;
    }
}
