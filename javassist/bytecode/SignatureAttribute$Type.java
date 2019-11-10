package javassist.bytecode;

public abstract static class Type
{
    public Type() {
        super();
    }
    
    abstract void encode(final StringBuffer p0);
    
    static void toString(final StringBuffer sbuf, final Type[] ts) {
        for (int i = 0; i < ts.length; ++i) {
            if (i > 0) {
                sbuf.append(", ");
            }
            sbuf.append(ts[i]);
        }
    }
    
    public String jvmTypeName() {
        return this.toString();
    }
}
