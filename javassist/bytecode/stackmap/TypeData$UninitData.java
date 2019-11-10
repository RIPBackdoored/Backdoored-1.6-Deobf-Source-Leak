package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import java.util.*;

public static class UninitData extends ClassName
{
    int offset;
    boolean initialized;
    
    UninitData(final int offset, final String className) {
        super(className);
        this.offset = offset;
        this.initialized = false;
    }
    
    public UninitData copy() {
        return new UninitData(this.offset, this.getName());
    }
    
    @Override
    public int getTypeTag() {
        return 8;
    }
    
    @Override
    public int getTypeData(final ConstPool cp) {
        return this.offset;
    }
    
    @Override
    public TypeData join() {
        if (this.initialized) {
            return new TypeVar(new ClassName(this.getName()));
        }
        return new UninitTypeVar(this.copy());
    }
    
    @Override
    public boolean isUninit() {
        return true;
    }
    
    @Override
    public boolean eq(final TypeData d) {
        if (d instanceof UninitData) {
            final UninitData ud = (UninitData)d;
            return this.offset == ud.offset && this.getName().equals(ud.getName());
        }
        return false;
    }
    
    public int offset() {
        return this.offset;
    }
    
    @Override
    public void constructorCalled(final int offset) {
        if (offset == this.offset) {
            this.initialized = true;
        }
    }
    
    @Override
    String toString2(final HashSet set) {
        return this.getName() + "," + this.offset;
    }
}
