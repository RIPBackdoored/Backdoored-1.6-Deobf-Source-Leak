package javassist.bytecode;

public static class NestedClassType extends ClassType
{
    ClassType parent;
    
    NestedClassType(final String s, final int b, final int e, final TypeArgument[] targs, final ClassType p) {
        super(s, b, e, targs);
        this.parent = p;
    }
    
    public NestedClassType(final ClassType parent, final String className, final TypeArgument[] args) {
        super(className, args);
        this.parent = parent;
    }
    
    @Override
    public ClassType getDeclaringClass() {
        return this.parent;
    }
}
