package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class DoubleInitializer extends Initializer
{
    double value;
    
    DoubleInitializer(final double v) {
        super();
        this.value = v;
    }
    
    @Override
    void check(final String desc) throws CannotCompileException {
        if (!desc.equals("D")) {
            throw new CannotCompileException("type mismatch");
        }
    }
    
    @Override
    int compile(final CtClass type, final String name, final Bytecode code, final CtClass[] parameters, final Javac drv) throws CannotCompileException {
        code.addAload(0);
        code.addLdc2w(this.value);
        code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
        return 3;
    }
    
    @Override
    int compileIfStatic(final CtClass type, final String name, final Bytecode code, final Javac drv) throws CannotCompileException {
        code.addLdc2w(this.value);
        code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
        return 2;
    }
    
    @Override
    int getConstantValue(final ConstPool cp, final CtClass type) {
        if (type == CtClass.doubleType) {
            return cp.addDoubleInfo(this.value);
        }
        return 0;
    }
}
