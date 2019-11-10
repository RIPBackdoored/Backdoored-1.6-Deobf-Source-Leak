package javassist.expr;

import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.*;
import javassist.compiler.*;

static class ProceedForInstanceof implements ProceedHandler
{
    int index;
    
    ProceedForInstanceof(final int i) {
        super();
        this.index = i;
    }
    
    @Override
    public void doit(final JvstCodeGen gen, final Bytecode bytecode, final ASTList args) throws CompileError {
        if (gen.getMethodArgsLength(args) != 1) {
            throw new CompileError("$proceed() cannot take more than one parameter for instanceof");
        }
        gen.atMethodArgs(args, new int[1], new int[1], new String[1]);
        bytecode.addOpcode(193);
        bytecode.addIndex(this.index);
        gen.setType(CtClass.booleanType);
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker c, final ASTList args) throws CompileError {
        c.atMethodArgs(args, new int[1], new int[1], new String[1]);
        c.setType(CtClass.booleanType);
    }
}
