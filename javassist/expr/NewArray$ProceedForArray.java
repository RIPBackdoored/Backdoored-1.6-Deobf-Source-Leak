package javassist.expr;

import javassist.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;
import javassist.compiler.*;

static class ProceedForArray implements ProceedHandler
{
    CtClass arrayType;
    int opcode;
    int index;
    int dimension;
    
    ProceedForArray(final CtClass type, final int op, final int i, final int dim) {
        super();
        this.arrayType = type;
        this.opcode = op;
        this.index = i;
        this.dimension = dim;
    }
    
    @Override
    public void doit(final JvstCodeGen gen, final Bytecode bytecode, final ASTList args) throws CompileError {
        final int num = gen.getMethodArgsLength(args);
        if (num != this.dimension) {
            throw new CompileError("$proceed() with a wrong number of parameters");
        }
        gen.atMethodArgs(args, new int[num], new int[num], new String[num]);
        bytecode.addOpcode(this.opcode);
        if (this.opcode == 189) {
            bytecode.addIndex(this.index);
        }
        else if (this.opcode == 188) {
            bytecode.add(this.index);
        }
        else {
            bytecode.addIndex(this.index);
            bytecode.add(this.dimension);
            bytecode.growStack(1 - this.dimension);
        }
        gen.setType(this.arrayType);
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker c, final ASTList args) throws CompileError {
        c.setType(this.arrayType);
    }
}
