package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import javassist.*;
import java.util.*;

public static class ArrayType extends AbsTypeVar
{
    private AbsTypeVar element;
    
    private ArrayType(final AbsTypeVar elementType) {
        super();
        this.element = elementType;
    }
    
    static TypeData make(final TypeData element) throws BadBytecode {
        if (element instanceof ArrayElement) {
            return ((ArrayElement)element).arrayType();
        }
        if (element instanceof AbsTypeVar) {
            return new ArrayType((AbsTypeVar)element);
        }
        if (element instanceof ClassName && !element.isNullType()) {
            return new ClassName(typeName(element.getName()));
        }
        throw new BadBytecode("bad AASTORE: " + element);
    }
    
    @Override
    public void merge(final TypeData t) {
        try {
            if (!t.isNullType()) {
                this.element.merge(ArrayElement.make(t));
            }
        }
        catch (BadBytecode e) {
            throw new RuntimeException("fatal: " + e);
        }
    }
    
    @Override
    public String getName() {
        return typeName(this.element.getName());
    }
    
    public AbsTypeVar elementType() {
        return this.element;
    }
    
    @Override
    public BasicType isBasicType() {
        return null;
    }
    
    @Override
    public boolean is2WordType() {
        return false;
    }
    
    public static String typeName(final String elementType) {
        if (elementType.charAt(0) == '[') {
            return "[" + elementType;
        }
        return "[L" + elementType.replace('.', '/') + ";";
    }
    
    @Override
    public void setType(final String s, final ClassPool cp) throws BadBytecode {
        this.element.setType(ArrayElement.access$000(s), cp);
    }
    
    @Override
    protected TypeVar toTypeVar(final int dim) {
        return this.element.toTypeVar(dim + 1);
    }
    
    @Override
    public TypeData getArrayType(final int dim) throws NotFoundException {
        return this.element.getArrayType(dim + 1);
    }
    
    @Override
    public int dfs(final ArrayList order, final int index, final ClassPool cp) throws NotFoundException {
        return this.element.dfs(order, index, cp);
    }
    
    @Override
    String toString2(final HashSet set) {
        return "[" + this.element.toString2(set);
    }
}
