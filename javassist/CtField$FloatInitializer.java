package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class FloatInitializer extends Initializer
{
    float value;
    
    FloatInitializer(final float v) {
        super();
        this.value = v;
    }
    
    @Override
    void check(final String desc) throws CannotCompileException {
        if (!desc.equals("F")) {
            throw new CannotCompileException("type mismatch");
        }
    }
    
    @Override
    int compile(final CtClass type, final String name, final Bytecode code, final CtClass[] parameters, final Javac drv) throws CannotCompileException {
        code.addAload(0);
        code.addFconst(this.value);
        code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
        return 3;
    }
    
    @Override
    int compileIfStatic(final CtClass type, final String name, final Bytecode code, final Javac drv) throws CannotCompileException {
        code.addFconst(this.value);
        code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
        return 2;
    }
    
    @Override
    int getConstantValue(final ConstPool cp, final CtClass type) {
        if (type == CtClass.floatType) {
            return cp.addFloatInfo(this.value);
        }
        return 0;
    }
}
