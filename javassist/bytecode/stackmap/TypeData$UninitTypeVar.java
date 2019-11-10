package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import javassist.*;
import java.util.*;

public static class UninitTypeVar extends AbsTypeVar
{
    protected TypeData type;
    
    public UninitTypeVar(final UninitData t) {
        super();
        this.type = t;
    }
    
    @Override
    public int getTypeTag() {
        return this.type.getTypeTag();
    }
    
    @Override
    public int getTypeData(final ConstPool cp) {
        return this.type.getTypeData(cp);
    }
    
    @Override
    public BasicType isBasicType() {
        return this.type.isBasicType();
    }
    
    @Override
    public boolean is2WordType() {
        return this.type.is2WordType();
    }
    
    @Override
    public boolean isUninit() {
        return this.type.isUninit();
    }
    
    @Override
    public boolean eq(final TypeData d) {
        return this.type.eq(d);
    }
    
    @Override
    public String getName() {
        return this.type.getName();
    }
    
    @Override
    protected TypeVar toTypeVar(final int dim) {
        return null;
    }
    
    @Override
    public TypeData join() {
        return this.type.join();
    }
    
    @Override
    public void setType(final String s, final ClassPool cp) throws BadBytecode {
        this.type.setType(s, cp);
    }
    
    @Override
    public void merge(final TypeData t) {
        if (!t.eq(this.type)) {
            this.type = TypeTag.TOP;
        }
    }
    
    @Override
    public void constructorCalled(final int offset) {
        this.type.constructorCalled(offset);
    }
    
    public int offset() {
        if (this.type instanceof UninitData) {
            return ((UninitData)this.type).offset;
        }
        throw new RuntimeException("not available");
    }
    
    @Override
    public TypeData getArrayType(final int dim) throws NotFoundException {
        return this.type.getArrayType(dim);
    }
    
    @Override
    String toString2(final HashSet set) {
        return "";
    }
}
