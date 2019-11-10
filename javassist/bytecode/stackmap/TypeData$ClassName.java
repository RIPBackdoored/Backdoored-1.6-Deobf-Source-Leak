package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import javassist.*;
import java.util.*;

public static class ClassName extends TypeData
{
    private String name;
    
    public ClassName(final String n) {
        super();
        this.name = n;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public BasicType isBasicType() {
        return null;
    }
    
    @Override
    public boolean is2WordType() {
        return false;
    }
    
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
        return this.name.equals(d.getName());
    }
    
    @Override
    public void setType(final String typeName, final ClassPool cp) throws BadBytecode {
    }
    
    @Override
    public TypeData getArrayType(final int dim) throws NotFoundException {
        if (dim == 0) {
            return this;
        }
        if (dim > 0) {
            final char[] dimType = new char[dim];
            for (int i = 0; i < dim; ++i) {
                dimType[i] = '[';
            }
            String elementType = this.getName();
            if (elementType.charAt(0) != '[') {
                elementType = "L" + elementType.replace('.', '/') + ";";
            }
            return new ClassName(new String(dimType) + elementType);
        }
        for (int j = 0; j < -dim; ++j) {
            if (this.name.charAt(j) != '[') {
                throw new NotFoundException("no " + dim + " dimensional array type: " + this.getName());
            }
        }
        final char type = this.name.charAt(-dim);
        if (type == '[') {
            return new ClassName(this.name.substring(-dim));
        }
        if (type == 'L') {
            return new ClassName(this.name.substring(-dim + 1, this.name.length() - 1).replace('/', '.'));
        }
        if (type == BasicType.access$100(TypeTag.DOUBLE)) {
            return TypeTag.DOUBLE;
        }
        if (type == BasicType.access$100(TypeTag.FLOAT)) {
            return TypeTag.FLOAT;
        }
        if (type == BasicType.access$100(TypeTag.LONG)) {
            return TypeTag.LONG;
        }
        return TypeTag.INTEGER;
    }
    
    @Override
    String toString2(final HashSet set) {
        return this.name;
    }
}
