package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class MultiArrayInitializer extends Initializer
{
    CtClass type;
    int[] dim;
    
    MultiArrayInitializer(final CtClass t, final int[] d) {
        super();
        this.type = t;
        this.dim = d;
    }
    
    @Override
    void check(final String desc) throws CannotCompileException {
        if (desc.charAt(0) != '[') {
            throw new CannotCompileException("type mismatch");
        }
    }
    
    @Override
    int compile(final CtClass type, final String name, final Bytecode code, final CtClass[] parameters, final Javac drv) throws CannotCompileException {
        code.addAload(0);
        final int s = code.addMultiNewarray(type, this.dim);
        code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
        return s + 1;
    }
    
    @Override
    int compileIfStatic(final CtClass type, final String name, final Bytecode code, final Javac drv) throws CannotCompileException {
        final int s = code.addMultiNewarray(type, this.dim);
        code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
        return s;
    }
}
