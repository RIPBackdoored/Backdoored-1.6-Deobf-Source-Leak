package javassist.expr;

import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.*;
import javassist.compiler.*;

static class ProceedForWrite implements ProceedHandler
{
    CtClass fieldType;
    int opcode;
    int targetVar;
    int index;
    
    ProceedForWrite(final CtClass type, final int op, final int i, final int var) {
        super();
        this.fieldType = type;
        this.targetVar = var;
        this.opcode = op;
        this.index = i;
    }
    
    @Override
    public void doit(final JvstCodeGen gen, final Bytecode bytecode, final ASTList args) throws CompileError {
        if (gen.getMethodArgsLength(args) != 1) {
            throw new CompileError("$proceed() cannot take more than one parameter for field writing");
        }
        int stack;
        if (FieldAccess.isStatic(this.opcode)) {
            stack = 0;
        }
        else {
            stack = -1;
            bytecode.addAload(this.targetVar);
        }
        gen.atMethodArgs(args, new int[1], new int[1], new String[1]);
        gen.doNumCast(this.fieldType);
        if (this.fieldType instanceof CtPrimitiveType) {
            stack -= ((CtPrimitiveType)this.fieldType).getDataSize();
        }
        else {
            --stack;
        }
        bytecode.add(this.opcode);
        bytecode.addIndex(this.index);
        bytecode.growStack(stack);
        gen.setType(CtClass.voidType);
        gen.addNullIfVoid();
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker c, final ASTList args) throws CompileError {
        c.atMethodArgs(args, new int[1], new int[1], new String[1]);
        c.setType(CtClass.voidType);
        c.addNullIfVoid();
    }
}
