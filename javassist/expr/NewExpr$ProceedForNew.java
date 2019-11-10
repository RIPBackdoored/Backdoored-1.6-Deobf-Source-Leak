package javassist.expr;

import javassist.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.compiler.*;

static class ProceedForNew implements ProceedHandler
{
    CtClass newType;
    int newIndex;
    int methodIndex;
    
    ProceedForNew(final CtClass nt, final int ni, final int mi) {
        super();
        this.newType = nt;
        this.newIndex = ni;
        this.methodIndex = mi;
    }
    
    @Override
    public void doit(final JvstCodeGen gen, final Bytecode bytecode, final ASTList args) throws CompileError {
        bytecode.addOpcode(187);
        bytecode.addIndex(this.newIndex);
        bytecode.addOpcode(89);
        gen.atMethodCallCore(this.newType, "<init>", args, false, true, -1, null);
        gen.setType(this.newType);
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker c, final ASTList args) throws CompileError {
        c.atMethodCallCore(this.newType, "<init>", args);
        c.setType(this.newType);
    }
}
