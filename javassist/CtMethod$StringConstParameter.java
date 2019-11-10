package javassist;

import javassist.bytecode.*;

static class StringConstParameter extends ConstParameter
{
    String param;
    
    StringConstParameter(final String s) {
        super();
        this.param = s;
    }
    
    @Override
    int compile(final Bytecode code) throws CannotCompileException {
        code.addLdc(this.param);
        return 1;
    }
    
    @Override
    String descriptor() {
        return "([Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;";
    }
    
    @Override
    String constDescriptor() {
        return "([Ljava/lang/Object;Ljava/lang/String;)V";
    }
}
