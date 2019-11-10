package javassist.expr;

static final class LoopContext
{
    NewOp newList;
    int maxLocals;
    int maxStack;
    
    LoopContext(final int locals) {
        super();
        this.maxLocals = locals;
        this.maxStack = 0;
        this.newList = null;
    }
    
    void updateMax(final int locals, final int stack) {
        if (this.maxLocals < locals) {
            this.maxLocals = locals;
        }
        if (this.maxStack < stack) {
            this.maxStack = stack;
        }
    }
}
