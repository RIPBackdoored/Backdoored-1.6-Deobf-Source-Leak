package javassist.bytecode;

public static class ClassType extends ObjectType
{
    String name;
    TypeArgument[] arguments;
    public static ClassType OBJECT;
    
    static ClassType make(final String s, final int b, final int e, final TypeArgument[] targs, final ClassType parent) {
        if (parent == null) {
            return new ClassType(s, b, e, targs);
        }
        return new NestedClassType(s, b, e, targs, parent);
    }
    
    ClassType(final String signature, final int begin, final int end, final TypeArgument[] targs) {
        super();
        this.name = signature.substring(begin, end).replace('/', '.');
        this.arguments = targs;
    }
    
    public ClassType(final String className, final TypeArgument[] args) {
        super();
        this.name = className;
        this.arguments = args;
    }
    
    public ClassType(final String className) {
        this(className, null);
    }
    
    public String getName() {
        return this.name;
    }
    
    public TypeArgument[] getTypeArguments() {
        return this.arguments;
    }
    
    public ClassType getDeclaringClass() {
        return null;
    }
    
    @Override
    public String toString() {
        final StringBuffer sbuf = new StringBuffer();
        final ClassType parent = this.getDeclaringClass();
        if (parent != null) {
            sbuf.append(parent.toString()).append('.');
        }
        return this.toString2(sbuf);
    }
    
    private String toString2(final StringBuffer sbuf) {
        sbuf.append(this.name);
        if (this.arguments != null) {
            sbuf.append('<');
            for (int n = this.arguments.length, i = 0; i < n; ++i) {
                if (i > 0) {
                    sbuf.append(", ");
                }
                sbuf.append(this.arguments[i].toString());
            }
            sbuf.append('>');
        }
        return sbuf.toString();
    }
    
    @Override
    public String jvmTypeName() {
        final StringBuffer sbuf = new StringBuffer();
        final ClassType parent = this.getDeclaringClass();
        if (parent != null) {
            sbuf.append(parent.jvmTypeName()).append('$');
        }
        return this.toString2(sbuf);
    }
    
    @Override
    void encode(final StringBuffer sb) {
        sb.append('L');
        this.encode2(sb);
        sb.append(';');
    }
    
    void encode2(final StringBuffer sb) {
        final ClassType parent = this.getDeclaringClass();
        if (parent != null) {
            parent.encode2(sb);
            sb.append('$');
        }
        sb.append(this.name.replace('.', '/'));
        if (this.arguments != null) {
            TypeArgument.encode(sb, this.arguments);
        }
    }
    
    static {
        ClassType.OBJECT = new ClassType("java.lang.Object", null);
    }
}
