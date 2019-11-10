package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import javassist.*;
import java.util.*;

protected static class BasicType extends TypeData
{
    private String name;
    private int typeTag;
    private char decodedName;
    
    public BasicType(final String type, final int tag, final char decoded) {
        super();
        this.name = type;
        this.typeTag = tag;
        this.decodedName = decoded;
    }
    
    @Override
    public int getTypeTag() {
        return this.typeTag;
    }
    
    @Override
    public int getTypeData(final ConstPool cp) {
        return 0;
    }
    
    @Override
    public TypeData join() {
        if (this == TypeTag.TOP) {
            return this;
        }
        return super.join();
    }
    
    @Override
    public BasicType isBasicType() {
        return this;
    }
    
    @Override
    public boolean is2WordType() {
        return this.typeTag == 4 || this.typeTag == 3;
    }
    
    @Override
    public boolean eq(final TypeData d) {
        return this == d;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public char getDecodedName() {
        return this.decodedName;
    }
    
    @Override
    public void setType(final String s, final ClassPool cp) throws BadBytecode {
        throw new BadBytecode("conflict: " + this.name + " and " + s);
    }
    
    @Override
    public TypeData getArrayType(final int dim) throws NotFoundException {
        if (this == TypeTag.TOP) {
            return this;
        }
        if (dim < 0) {
            throw new NotFoundException("no element type: " + this.name);
        }
        if (dim == 0) {
            return this;
        }
        final char[] name = new char[dim + 1];
        for (int i = 0; i < dim; ++i) {
            name[i] = '[';
        }
        name[dim] = this.decodedName;
        return new ClassName(new String(name));
    }
    
    @Override
    String toString2(final HashSet set) {
        return this.name;
    }
    
    static /* synthetic */ char access$100(final BasicType x0) {
        return x0.decodedName;
    }
}
