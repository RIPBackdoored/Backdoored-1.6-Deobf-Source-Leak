package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class IntInitializer extends Initializer
{
    int value;
    
    IntInitializer(final int v) {
        super();
        this.value = v;
    }
    
    @Override
    void check(final String desc) throws CannotCompileException {
        final char c = desc.charAt(0);
        if (c != 'I' && c != 'S' && c != 'B' && c != 'C' && c != 'Z') {
            throw new CannotCompileException("type mismatch");
        }
    }
    
    @Override
    int compile(final CtClass type, final String name, final Bytecode code, final CtClass[] parameters, final Javac drv) throws CannotCompileException {
        code.addAload(0);
        code.addIconst(this.value);
        code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
        return 2;
    }
    
    @Override
    int compileIfStatic(final CtClass type, final String name, final Bytecode code, final Javac drv) throws CannotCompileException {
        code.addIconst(this.value);
        code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
        return 1;
    }
    
    @Override
    int getConstantValue(final ConstPool cp, final CtClass type) {
        return cp.addIntegerInfo(this.value);
    }
}
