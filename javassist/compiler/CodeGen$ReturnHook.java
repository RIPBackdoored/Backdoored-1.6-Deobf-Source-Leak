package javassist.compiler;

import javassist.bytecode.*;

protected abstract static class ReturnHook
{
    ReturnHook next;
    
    protected abstract boolean doit(final Bytecode p0, final int p1);
    
    protected ReturnHook(final CodeGen gen) {
        super();
        this.next = gen.returnHooks;
        gen.returnHooks = this;
    }
    
    protected void remove(final CodeGen gen) {
        gen.returnHooks = this.next;
    }
}
