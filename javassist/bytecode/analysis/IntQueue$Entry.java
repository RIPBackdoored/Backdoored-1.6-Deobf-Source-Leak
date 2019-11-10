package javassist.bytecode.analysis;

private static class Entry
{
    private Entry next;
    private int value;
    
    private Entry(final int value) {
        super();
        this.value = value;
    }
    
    Entry(final int x0, final IntQueue$1 x1) {
        this(x0);
    }
    
    static /* synthetic */ Entry access$102(final Entry x0, final Entry x1) {
        return x0.next = x1;
    }
    
    static /* synthetic */ int access$200(final Entry x0) {
        return x0.value;
    }
    
    static /* synthetic */ Entry access$100(final Entry x0) {
        return x0.next;
    }
}
