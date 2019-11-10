package javassist;

import javassist.compiler.ast.*;
import javassist.compiler.*;
import javassist.bytecode.*;

static class PtreeInitializer extends CodeInitializer0
{
    private ASTree expression;
    
    PtreeInitializer(final ASTree expr) {
        super();
        this.expression = expr;
    }
    
    @Override
    void compileExpr(final Javac drv) throws CompileError {
        drv.compileExpr(this.expression);
    }
    
    @Override
    int getConstantValue(final ConstPool cp, final CtClass type) {
        return this.getConstantValue2(cp, type, this.expression);
    }
}
