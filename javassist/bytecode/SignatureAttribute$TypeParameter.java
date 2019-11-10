package javassist.bytecode;

public static class TypeParameter
{
    String name;
    ObjectType superClass;
    ObjectType[] superInterfaces;
    
    TypeParameter(final String sig, final int nb, final int ne, final ObjectType sc, final ObjectType[] si) {
        super();
        this.name = sig.substring(nb, ne);
        this.superClass = sc;
        this.superInterfaces = si;
    }
    
    public TypeParameter(final String name, final ObjectType superClass, final ObjectType[] superInterfaces) {
        super();
        this.name = name;
        this.superClass = superClass;
        if (superInterfaces == null) {
            this.superInterfaces = new ObjectType[0];
        }
        else {
            this.superInterfaces = superInterfaces;
        }
    }
    
    public TypeParameter(final String name) {
        this(name, null, null);
    }
    
    public String getName() {
        return this.name;
    }
    
    public ObjectType getClassBound() {
        return this.superClass;
    }
    
    public ObjectType[] getInterfaceBound() {
        return this.superInterfaces;
    }
    
    @Override
    public String toString() {
        final StringBuffer sbuf = new StringBuffer(this.getName());
        if (this.superClass != null) {
            sbuf.append(" extends ").append(this.superClass.toString());
        }
        final int len = this.superInterfaces.length;
        if (len > 0) {
            for (int i = 0; i < len; ++i) {
                if (i > 0 || this.superClass != null) {
                    sbuf.append(" & ");
                }
                else {
                    sbuf.append(" extends ");
                }
                sbuf.append(this.superInterfaces[i].toString());
            }
        }
        return sbuf.toString();
    }
    
    static void toString(final StringBuffer sbuf, final TypeParameter[] tp) {
        sbuf.append('<');
        for (int i = 0; i < tp.length; ++i) {
            if (i > 0) {
                sbuf.append(", ");
            }
            sbuf.append(tp[i]);
        }
        sbuf.append('>');
    }
    
    void encode(final StringBuffer sb) {
        sb.append(this.name);
        if (this.superClass == null) {
            sb.append(":Ljava/lang/Object;");
        }
        else {
            sb.append(':');
            this.superClass.encode(sb);
        }
        for (int i = 0; i < this.superInterfaces.length; ++i) {
            sb.append(':');
            this.superInterfaces[i].encode(sb);
        }
    }
}
