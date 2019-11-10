package javassist.bytecode;

public static class TypeArgument
{
    ObjectType arg;
    char wildcard;
    
    TypeArgument(final ObjectType a, final char w) {
        super();
        this.arg = a;
        this.wildcard = w;
    }
    
    public TypeArgument(final ObjectType t) {
        this(t, ' ');
    }
    
    public TypeArgument() {
        this(null, '*');
    }
    
    public static TypeArgument subclassOf(final ObjectType t) {
        return new TypeArgument(t, '+');
    }
    
    public static TypeArgument superOf(final ObjectType t) {
        return new TypeArgument(t, '-');
    }
    
    public char getKind() {
        return this.wildcard;
    }
    
    public boolean isWildcard() {
        return this.wildcard != ' ';
    }
    
    public ObjectType getType() {
        return this.arg;
    }
    
    @Override
    public String toString() {
        if (this.wildcard == '*') {
            return "?";
        }
        final String type = this.arg.toString();
        if (this.wildcard == ' ') {
            return type;
        }
        if (this.wildcard == '+') {
            return "? extends " + type;
        }
        return "? super " + type;
    }
    
    static void encode(final StringBuffer sb, final TypeArgument[] args) {
        sb.append('<');
        for (int i = 0; i < args.length; ++i) {
            final TypeArgument ta = args[i];
            if (ta.isWildcard()) {
                sb.append(ta.wildcard);
            }
            if (ta.getType() != null) {
                ta.getType().encode(sb);
            }
        }
        sb.append('>');
    }
}
