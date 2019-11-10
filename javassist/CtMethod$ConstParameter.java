package javassist;

import javassist.bytecode.*;

public static class ConstParameter
{
    public static ConstParameter integer(final int i) {
        return new IntConstParameter(i);
    }
    
    public static ConstParameter integer(final long i) {
        return new LongConstParameter(i);
    }
    
    public static ConstParameter string(final String s) {
        return new StringConstParameter(s);
    }
    
    ConstParameter() {
        super();
    }
    
    int compile(final Bytecode code) throws CannotCompileException {
        return 0;
    }
    
    String descriptor() {
        return defaultDescriptor();
    }
    
    static String defaultDescriptor() {
        return "([Ljava/lang/Object;)Ljava/lang/Object;";
    }
    
    String constDescriptor() {
        return defaultConstDescriptor();
    }
    
    static String defaultConstDescriptor() {
        return "([Ljava/lang/Object;)V";
    }
}
