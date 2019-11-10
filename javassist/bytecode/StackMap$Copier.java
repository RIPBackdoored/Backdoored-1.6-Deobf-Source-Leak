package javassist.bytecode;

import java.util.*;

static class Copier extends Walker
{
    byte[] dest;
    ConstPool srcCp;
    ConstPool destCp;
    Map classnames;
    
    Copier(final StackMap map, final ConstPool newCp, final Map classnames) {
        super(map);
        this.srcCp = map.getConstPool();
        this.dest = new byte[this.info.length];
        this.destCp = newCp;
        this.classnames = classnames;
    }
    
    @Override
    public void visit() {
        final int num = ByteArray.readU16bit(this.info, 0);
        ByteArray.write16bit(num, this.dest, 0);
        super.visit();
    }
    
    @Override
    public int locals(final int pos, final int offset, final int num) {
        ByteArray.write16bit(offset, this.dest, pos - 4);
        return super.locals(pos, offset, num);
    }
    
    @Override
    public int typeInfoArray(final int pos, final int offset, final int num, final boolean isLocals) {
        ByteArray.write16bit(num, this.dest, pos - 2);
        return super.typeInfoArray(pos, offset, num, isLocals);
    }
    
    @Override
    public void typeInfo(final int pos, final byte tag) {
        this.dest[pos] = tag;
    }
    
    @Override
    public void objectVariable(final int pos, final int clazz) {
        this.dest[pos] = 7;
        final int newClazz = this.srcCp.copy(clazz, this.destCp, this.classnames);
        ByteArray.write16bit(newClazz, this.dest, pos + 1);
    }
    
    @Override
    public void uninitialized(final int pos, final int offset) {
        this.dest[pos] = 8;
        ByteArray.write16bit(offset, this.dest, pos + 1);
    }
    
    public StackMap getStackMap() {
        return new StackMap(this.destCp, this.dest);
    }
}
