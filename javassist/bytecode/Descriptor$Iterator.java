package javassist.bytecode;

public static class Iterator
{
    private String desc;
    private int index;
    private int curPos;
    private boolean param;
    
    public Iterator(final String s) {
        super();
        this.desc = s;
        final int n = 0;
        this.curPos = n;
        this.index = n;
        this.param = false;
    }
    
    public boolean hasNext() {
        return this.index < this.desc.length();
    }
    
    public boolean isParameter() {
        return this.param;
    }
    
    public char currentChar() {
        return this.desc.charAt(this.curPos);
    }
    
    public boolean is2byte() {
        final char c = this.currentChar();
        return c == 'D' || c == 'J';
    }
    
    public int next() {
        int nextPos = this.index;
        char c = this.desc.charAt(nextPos);
        if (c == '(') {
            ++this.index;
            c = this.desc.charAt(++nextPos);
            this.param = true;
        }
        if (c == ')') {
            ++this.index;
            c = this.desc.charAt(++nextPos);
            this.param = false;
        }
        while (c == '[') {
            c = this.desc.charAt(++nextPos);
        }
        if (c == 'L') {
            nextPos = this.desc.indexOf(59, nextPos) + 1;
            if (nextPos <= 0) {
                throw new IndexOutOfBoundsException("bad descriptor");
            }
        }
        else {
            ++nextPos;
        }
        this.curPos = this.index;
        this.index = nextPos;
        return this.curPos;
    }
}
