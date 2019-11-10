package javassist.compiler;

import javassist.bytecode.*;

class CodeGen$1 extends ReturnHook {
    final /* synthetic */ int val$var;
    final /* synthetic */ CodeGen this$0;
    
    CodeGen$1(final CodeGen this$0, final CodeGen gen, final int val$var) {
        this.this$0 = this$0;
        this.val$var = val$var;
        super(gen);
    }
    
    @Override
    protected boolean doit(final Bytecode b, final int opcode) {
        b.addAload(this.val$var);
        b.addOpcode(195);
        return false;
    }
}