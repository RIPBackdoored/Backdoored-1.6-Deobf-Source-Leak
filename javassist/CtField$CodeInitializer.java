package javassist;

import javassist.bytecode.*;
import javassist.compiler.*;
import javassist.compiler.ast.*;

static class CodeInitializer extends CodeInitializer0
{
    private String expression;
    
    CodeInitializer(final String expr) {
        super();
        this.expression = expr;
    }
    
    @Override
    void compileExpr(final Javac drv) throws CompileError {
        drv.compileExpr(this.expression);
    }
    
    @Override
    int getConstantValue(final ConstPool cp, final CtClass type) {
        try {
            final ASTree t = Javac.parseExpr(this.expression, new SymbolTable());
            return this.getConstantValue2(cp, type, t);
        }
        catch (CompileError e) {
            return 0;
        }
    }
}
