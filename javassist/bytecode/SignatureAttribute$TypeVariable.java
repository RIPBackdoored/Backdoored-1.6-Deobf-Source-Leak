package javassist.bytecode;

public static class TypeVariable extends ObjectType
{
    String name;
    
    TypeVariable(final String sig, final int begin, final int end) {
        super();
        this.name = sig.substring(begin, end);
    }
    
    public TypeVariable(final String name) {
        super();
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @Override
    void encode(final StringBuffer sb) {
        sb.append('T').append(this.name).append(';');
    }
}
