package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import javassist.*;
import java.util.*;

public static class ArrayElement extends AbsTypeVar
{
    private AbsTypeVar array;
    
    private ArrayElement(final AbsTypeVar a) {
        super();
        this.array = a;
    }
    
    public static TypeData make(final TypeData array) throws BadBytecode {
        if (array instanceof ArrayType) {
            return ((ArrayType)array).elementType();
        }
        if (array instanceof AbsTypeVar) {
            return new ArrayElement((AbsTypeVar)array);
        }
        if (array instanceof ClassName && !array.isNullType()) {
            return new ClassName(typeName(array.getName()));
        }
        throw new BadBytecode("bad AASTORE: " + array);
    }
    
    @Override
    public void merge(final TypeData t) {
        try {
            if (!t.isNullType()) {
                this.array.merge(ArrayType.make(t));
            }
        }
        catch (BadBytecode e) {
            throw new RuntimeException("fatal: " + e);
        }
    }
    
    @Override
    public String getName() {
        return typeName(this.array.getName());
    }
    
    public AbsTypeVar arrayType() {
        return this.array;
    }
    
    @Override
    public BasicType isBasicType() {
        return null;
    }
    
    @Override
    public boolean is2WordType() {
        return false;
    }
    
    private static String typeName(final String arrayType) {
        if (arrayType.length() > 1 && arrayType.charAt(0) == '[') {
            final char c = arrayType.charAt(1);
            if (c == 'L') {
                return arrayType.substring(2, arrayType.length() - 1).replace('/', '.');
            }
            if (c == '[') {
                return arrayType.substring(1);
            }
        }
        return "java.lang.Object";
    }
    
    @Override
    public void setType(final String s, final ClassPool cp) throws BadBytecode {
        this.array.setType(ArrayType.typeName(s), cp);
    }
    
    @Override
    protected TypeVar toTypeVar(final int dim) {
        return this.array.toTypeVar(dim - 1);
    }
    
    @Override
    public TypeData getArrayType(final int dim) throws NotFoundException {
        return this.array.getArrayType(dim - 1);
    }
    
    @Override
    public int dfs(final ArrayList order, final int index, final ClassPool cp) throws NotFoundException {
        return this.array.dfs(order, index, cp);
    }
    
    @Override
    String toString2(final HashSet set) {
        return "*" + this.array.toString2(set);
    }
    
    static /* synthetic */ String access$000(final String x0) {
        return typeName(x0);
    }
}
