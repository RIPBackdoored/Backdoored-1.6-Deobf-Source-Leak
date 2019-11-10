package javassist.bytecode;

import javassist.*;

public static class BaseType extends Type
{
    char descriptor;
    
    BaseType(final char c) {
        super();
        this.descriptor = c;
    }
    
    public BaseType(final String typeName) {
        this(Descriptor.of(typeName).charAt(0));
    }
    
    public char getDescriptor() {
        return this.descriptor;
    }
    
    public CtClass getCtlass() {
        return Descriptor.toPrimitiveClass(this.descriptor);
    }
    
    @Override
    public String toString() {
        return Descriptor.toClassName(Character.toString(this.descriptor));
    }
    
    @Override
    void encode(final StringBuffer sb) {
        sb.append(this.descriptor);
    }
}
