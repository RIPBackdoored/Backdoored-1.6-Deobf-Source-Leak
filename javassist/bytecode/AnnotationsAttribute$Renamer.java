package javassist.bytecode;

import java.util.*;

static class Renamer extends Walker
{
    ConstPool cpool;
    Map classnames;
    
    Renamer(final byte[] info, final ConstPool cp, final Map map) {
        super(info);
        this.cpool = cp;
        this.classnames = map;
    }
    
    @Override
    int annotation(final int pos, final int type, final int numPairs) throws Exception {
        this.renameType(pos - 4, type);
        return super.annotation(pos, type, numPairs);
    }
    
    @Override
    void enumMemberValue(final int pos, final int typeNameIndex, final int constNameIndex) throws Exception {
        this.renameType(pos + 1, typeNameIndex);
        super.enumMemberValue(pos, typeNameIndex, constNameIndex);
    }
    
    @Override
    void classMemberValue(final int pos, final int index) throws Exception {
        this.renameType(pos + 1, index);
        super.classMemberValue(pos, index);
    }
    
    private void renameType(final int pos, final int index) {
        final String name = this.cpool.getUtf8Info(index);
        final String newName = Descriptor.rename(name, this.classnames);
        if (!name.equals(newName)) {
            final int index2 = this.cpool.addUtf8Info(newName);
            ByteArray.write16bit(index2, this.info, pos);
        }
    }
}
