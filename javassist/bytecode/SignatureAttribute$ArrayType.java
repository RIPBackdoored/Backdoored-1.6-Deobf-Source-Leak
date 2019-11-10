package javassist.bytecode;

public static class ArrayType extends ObjectType
{
    int dim;
    Type componentType;
    
    public ArrayType(final int d, final Type comp) {
        super();
        this.dim = d;
        this.componentType = comp;
    }
    
    public int getDimension() {
        return this.dim;
    }
    
    public Type getComponentType() {
        return this.componentType;
    }
    
    @Override
    public String toString() {
        final StringBuffer sbuf = new StringBuffer(this.componentType.toString());
        for (int i = 0; i < this.dim; ++i) {
            sbuf.append("[]");
        }
        return sbuf.toString();
    }
    
    @Override
    void encode(final StringBuffer sb) {
        for (int i = 0; i < this.dim; ++i) {
            sb.append('[');
        }
        this.componentType.encode(sb);
    }
}
