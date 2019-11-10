package javassist.compiler;

import javassist.*;
import javassist.bytecode.*;

public static class Method
{
    public CtClass declaring;
    public MethodInfo info;
    public int notmatch;
    
    public Method(final CtClass c, final MethodInfo i, final int n) {
        super();
        this.declaring = c;
        this.info = i;
        this.notmatch = n;
    }
    
    public boolean isStatic() {
        final int acc = this.info.getAccessFlags();
        return (acc & 0x8) != 0x0;
    }
}
