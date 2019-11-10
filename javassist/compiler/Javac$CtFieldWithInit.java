package javassist.compiler;

import javassist.compiler.ast.*;
import javassist.*;

public static class CtFieldWithInit extends CtField
{
    private ASTree init;
    
    CtFieldWithInit(final CtClass type, final String name, final CtClass declaring) throws CannotCompileException {
        super(type, name, declaring);
        this.init = null;
    }
    
    protected void setInit(final ASTree i) {
        this.init = i;
    }
    
    @Override
    protected ASTree getInitAST() {
        return this.init;
    }
}
