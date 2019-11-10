package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class StringInitializer extends Initializer
{
    String value;
    
    StringInitializer(final String v) {
        super();
        this.value = v;
    }
    
    @Override
    int compile(final CtClass type, final String name, final Bytecode code, final CtClass[] parameters, final Javac drv) throws CannotCompileException {
        code.addAload(0);
        code.addLdc(this.value);
        code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
        return 2;
    }
    
    @Override
    int compileIfStatic(final CtClass type, final String name, final Bytecode code, final Javac drv) throws CannotCompileException {
        code.addLdc(this.value);
        code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
        return 1;
    }
    
    @Override
    int getConstantValue(final ConstPool cp, final CtClass type) {
        if (type.getName().equals("java.lang.String")) {
            return cp.addStringInfo(this.value);
        }
        return 0;
    }
}
