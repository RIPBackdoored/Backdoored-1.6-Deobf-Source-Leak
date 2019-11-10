package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class ArrayInitializer extends Initializer
{
    CtClass type;
    int size;
    
    ArrayInitializer(final CtClass t, final int s) {
        super();
        this.type = t;
        this.size = s;
    }
    
    private void addNewarray(final Bytecode code) {
        if (this.type.isPrimitive()) {
            code.addNewarray(((CtPrimitiveType)this.type).getArrayType(), this.size);
        }
        else {
            code.addAnewarray(this.type, this.size);
        }
    }
    
    @Override
    int compile(final CtClass type, final String name, final Bytecode code, final CtClass[] parameters, final Javac drv) throws CannotCompileException {
        code.addAload(0);
        this.addNewarray(code);
        code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
        return 2;
    }
    
    @Override
    int compileIfStatic(final CtClass type, final String name, final Bytecode code, final Javac drv) throws CannotCompileException {
        this.addNewarray(code);
        code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
        return 1;
    }
}
