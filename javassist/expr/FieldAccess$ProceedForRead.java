package javassist.expr;

import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.*;
import javassist.compiler.*;

static class ProceedForRead implements ProceedHandler
{
    CtClass fieldType;
    int opcode;
    int targetVar;
    int index;
    
    ProceedForRead(final CtClass type, final int op, final int i, final int var) {
        super();
        this.fieldType = type;
        this.targetVar = var;
        this.opcode = op;
        this.index = i;
    }
    
    @Override
    public void doit(final JvstCodeGen gen, final Bytecode bytecode, final ASTList args) throws CompileError {
        if (args != null && !gen.isParamListName(args)) {
            throw new CompileError("$proceed() cannot take a parameter for field reading");
        }
        int stack;
        if (FieldAccess.isStatic(this.opcode)) {
            stack = 0;
        }
        else {
            stack = -1;
            bytecode.addAload(this.targetVar);
        }
        if (this.fieldType instanceof CtPrimitiveType) {
            stack += ((CtPrimitiveType)this.fieldType).getDataSize();
        }
        else {
            ++stack;
        }
        bytecode.add(this.opcode);
        bytecode.addIndex(this.index);
        bytecode.growStack(stack);
        gen.setType(this.fieldType);
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker c, final ASTList args) throws CompileError {
        c.setType(this.fieldType);
    }
}
