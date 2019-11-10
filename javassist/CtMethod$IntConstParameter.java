package javassist;

import javassist.bytecode.*;

static class IntConstParameter extends ConstParameter
{
    int param;
    
    IntConstParameter(final int i) {
        super();
        this.param = i;
    }
    
    @Override
    int compile(final Bytecode code) throws CannotCompileException {
        code.addIconst(this.param);
        return 1;
    }
    
    @Override
    String descriptor() {
        return "([Ljava/lang/Object;I)Ljava/lang/Object;";
    }
    
    @Override
    String constDescriptor() {
        return "([Ljava/lang/Object;I)V";
    }
}
