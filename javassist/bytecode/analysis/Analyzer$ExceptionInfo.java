package javassist.bytecode.analysis;

private static class ExceptionInfo
{
    private int end;
    private int handler;
    private int start;
    private Type type;
    
    private ExceptionInfo(final int start, final int end, final int handler, final Type type) {
        super();
        this.start = start;
        this.end = end;
        this.handler = handler;
        this.type = type;
    }
    
    ExceptionInfo(final int x0, final int x1, final int x2, final Type x3, final Analyzer$1 x4) {
        this(x0, x1, x2, x3);
    }
    
    static /* synthetic */ int access$100(final ExceptionInfo x0) {
        return x0.start;
    }
    
    static /* synthetic */ int access$200(final ExceptionInfo x0) {
        return x0.end;
    }
    
    static /* synthetic */ Type access$300(final ExceptionInfo x0) {
        return x0.type;
    }
    
    static /* synthetic */ int access$400(final ExceptionInfo x0) {
        return x0.handler;
    }
}
