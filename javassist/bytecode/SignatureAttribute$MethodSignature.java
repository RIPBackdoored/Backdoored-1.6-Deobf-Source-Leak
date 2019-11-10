package javassist.bytecode;

public static class MethodSignature
{
    TypeParameter[] typeParams;
    Type[] params;
    Type retType;
    ObjectType[] exceptions;
    
    public MethodSignature(final TypeParameter[] tp, final Type[] params, final Type ret, final ObjectType[] ex) {
        super();
        this.typeParams = ((tp == null) ? new TypeParameter[0] : tp);
        this.params = ((params == null) ? new Type[0] : params);
        this.retType = ((ret == null) ? new BaseType("void") : ret);
        this.exceptions = ((ex == null) ? new ObjectType[0] : ex);
    }
    
    public TypeParameter[] getTypeParameters() {
        return this.typeParams;
    }
    
    public Type[] getParameterTypes() {
        return this.params;
    }
    
    public Type getReturnType() {
        return this.retType;
    }
    
    public ObjectType[] getExceptionTypes() {
        return this.exceptions;
    }
    
    @Override
    public String toString() {
        final StringBuffer sbuf = new StringBuffer();
        TypeParameter.toString(sbuf, this.typeParams);
        sbuf.append(" (");
        Type.toString(sbuf, this.params);
        sbuf.append(") ");
        sbuf.append(this.retType);
        if (this.exceptions.length > 0) {
            sbuf.append(" throws ");
            Type.toString(sbuf, this.exceptions);
        }
        return sbuf.toString();
    }
    
    public String encode() {
        final StringBuffer sbuf = new StringBuffer();
        if (this.typeParams.length > 0) {
            sbuf.append('<');
            for (int i = 0; i < this.typeParams.length; ++i) {
                this.typeParams[i].encode(sbuf);
            }
            sbuf.append('>');
        }
        sbuf.append('(');
        for (int i = 0; i < this.params.length; ++i) {
            this.params[i].encode(sbuf);
        }
        sbuf.append(')');
        this.retType.encode(sbuf);
        if (this.exceptions.length > 0) {
            for (int i = 0; i < this.exceptions.length; ++i) {
                sbuf.append('^');
                this.exceptions[i].encode(sbuf);
            }
        }
        return sbuf.toString();
    }
}
