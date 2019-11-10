package javassist.bytecode.stackmap;

import javassist.bytecode.*;

public abstract static class AbsTypeVar extends TypeData
{
    public AbsTypeVar() {
        super();
    }
    
    public abstract void merge(final TypeData p0);
    
    @Override
    public int getTypeTag() {
        return 7;
    }
    
    @Override
    public int getTypeData(final ConstPool cp) {
        return cp.addClassInfo(this.getName());
    }
    
    @Override
    public boolean eq(final TypeData d) {
        return this.getName().equals(d.getName());
    }
}
