package javassist.bytecode;

private static class Cursor
{
    int position;
    
    private Cursor() {
        super();
        this.position = 0;
    }
    
    int indexOf(final String s, final int ch) throws BadBytecode {
        final int i = s.indexOf(ch, this.position);
        if (i < 0) {
            throw SignatureAttribute.access$000(s);
        }
        this.position = i + 1;
        return i;
    }
    
    Cursor(final SignatureAttribute$1 x0) {
        this();
    }
}
