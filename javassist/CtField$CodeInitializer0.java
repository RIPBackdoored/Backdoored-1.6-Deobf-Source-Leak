package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;
import javassist.compiler.ast.*;

abstract static class CodeInitializer0 extends Initializer
{
    CodeInitializer0() {
        super();
    }
    
    abstract void compileExpr(final Javac p0) throws CompileError;
    
    @Override
    int compile(final CtClass type, final String name, final Bytecode code, final CtClass[] parameters, final Javac drv) throws CannotCompileException {
        try {
            code.addAload(0);
            this.compileExpr(drv);
            code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
            return code.getMaxStack();
        }
        catch (CompileError e) {
            throw new CannotCompileException(e);
        }
    }
    
    @Override
    int compileIfStatic(final CtClass type, final String name, final Bytecode code, final Javac drv) throws CannotCompileException {
        try {
            this.compileExpr(drv);
            code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
            return code.getMaxStack();
        }
        catch (CompileError e) {
            throw new CannotCompileException(e);
        }
    }
    
    int getConstantValue2(final ConstPool cp, final CtClass type, final ASTree tree) {
        if (type.isPrimitive()) {
            if (tree instanceof IntConst) {
                final long value = ((IntConst)tree).get();
                if (type == CtClass.doubleType) {
                    return cp.addDoubleInfo((double)value);
                }
                if (type == CtClass.floatType) {
                    return cp.addFloatInfo((float)value);
                }
                if (type == CtClass.longType) {
                    return cp.addLongInfo(value);
                }
                if (type != CtClass.voidType) {
                    return cp.addIntegerInfo((int)value);
                }
            }
            else if (tree instanceof DoubleConst) {
                final double value2 = ((DoubleConst)tree).get();
                if (type == CtClass.floatType) {
                    return cp.addFloatInfo((float)value2);
                }
                if (type == CtClass.doubleType) {
                    return cp.addDoubleInfo(value2);
                }
            }
        }
        else if (tree instanceof StringL && type.getName().equals("java.lang.String")) {
            return cp.addStringInfo(((StringL)tree).get());
        }
        return 0;
    }
}
