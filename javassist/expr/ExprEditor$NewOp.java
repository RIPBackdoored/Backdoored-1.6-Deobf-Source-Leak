package javassist.expr;

static final class NewOp
{
    NewOp next;
    int pos;
    String type;
    
    NewOp(final NewOp n, final int p, final String t) {
        super();
        this.next = n;
        this.pos = p;
        this.type = t;
    }
}
