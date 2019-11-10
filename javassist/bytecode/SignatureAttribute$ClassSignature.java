package javassist.bytecode;

public static class ClassSignature
{
    TypeParameter[] params;
    ClassType superClass;
    ClassType[] interfaces;
    
    public ClassSignature(final TypeParameter[] params, final ClassType superClass, final ClassType[] interfaces) {
        super();
        this.params = ((params == null) ? new TypeParameter[0] : params);
        this.superClass = ((superClass == null) ? ClassType.OBJECT : superClass);
        this.interfaces = ((interfaces == null) ? new ClassType[0] : interfaces);
    }
    
    public ClassSignature(final TypeParameter[] p) {
        this(p, null, null);
    }
    
    public TypeParameter[] getParameters() {
        return this.params;
    }
    
    public ClassType getSuperClass() {
        return this.superClass;
    }
    
    public ClassType[] getInterfaces() {
        return this.interfaces;
    }
    
    @Override
    public String toString() {
        final StringBuffer sbuf = new StringBuffer();
        TypeParameter.toString(sbuf, this.params);
        sbuf.append(" extends ").append(this.superClass);
        if (this.interfaces.length > 0) {
            sbuf.append(" implements ");
            Type.toString(sbuf, this.interfaces);
        }
        return sbuf.toString();
    }
    
    public String encode() {
        final StringBuffer sbuf = new StringBuffer();
        if (this.params.length > 0) {
            sbuf.append('<');
            for (int i = 0; i < this.params.length; ++i) {
                this.params[i].encode(sbuf);
            }
            sbuf.append('>');
        }
        this.superClass.encode(sbuf);
        for (int i = 0; i < this.interfaces.length; ++i) {
            this.interfaces[i].encode(sbuf);
        }
        return sbuf.toString();
    }
}
