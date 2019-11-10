package javassist.expr;

import javassist.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.compiler.*;

static class ProceedForCast implements ProceedHandler
{
    int index;
    CtClass retType;
    
    ProceedForCast(final int i, final CtClass t) {
        super();
        this.index = i;
        this.retType = t;
    }
    
    @Override
    public void doit(final JvstCodeGen gen, final Bytecode bytecode, final ASTList args) throws CompileError {
        if (gen.getMethodArgsLength(args) != 1) {
            throw new CompileError("$proceed() cannot take more than one parameter for cast");
        }
        gen.atMethodArgs(args, new int[1], new int[1], new String[1]);
        bytecode.addOpcode(192);
        bytecode.addIndex(this.index);
        gen.setType(this.retType);
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker c, final ASTList args) throws CompileError {
        c.atMethodArgs(args, new int[1], new int[1], new String[1]);
        c.setType(this.retType);
    }
}
