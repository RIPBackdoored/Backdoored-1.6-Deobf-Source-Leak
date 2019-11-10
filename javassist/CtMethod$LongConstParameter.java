package javassist;

import javassist.bytecode.*;

static class LongConstParameter extends ConstParameter
{
    long param;
    
    LongConstParameter(final long l) {
        super();
        this.param = l;
    }
    
    @Override
    int compile(final Bytecode code) throws CannotCompileException {
        code.addLconst(this.param);
        return 2;
    }
    
    @Override
    String descriptor() {
        return "([Ljava/lang/Object;J)Ljava/lang/Object;";
    }
    
    @Override
    String constDescriptor() {
        return "([Ljava/lang/Object;J)V";
    }
}
