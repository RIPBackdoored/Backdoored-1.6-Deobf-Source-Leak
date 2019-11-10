package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class ParamInitializer extends Initializer
{
    int nthParam;
    
    ParamInitializer() {
        super();
    }
    
    @Override
    int compile(final CtClass type, final String name, final Bytecode code, final CtClass[] parameters, final Javac drv) throws CannotCompileException {
        if (parameters != null && this.nthParam < parameters.length) {
            code.addAload(0);
            final int nth = nthParamToLocal(this.nthParam, parameters, false);
            final int s = code.addLoad(nth, type) + 1;
            code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
            return s;
        }
        return 0;
    }
    
    static int nthParamToLocal(final int nth, final CtClass[] params, final boolean isStatic) {
        final CtClass longType = CtClass.longType;
        final CtClass doubleType = CtClass.doubleType;
        int k;
        if (isStatic) {
            k = 0;
        }
        else {
            k = 1;
        }
        for (final CtClass type : params) {
            if (type == longType || type == doubleType) {
                k += 2;
            }
            else {
                ++k;
            }
        }
        return k;
    }
    
    @Override
    int compileIfStatic(final CtClass type, final String name, final Bytecode code, final Javac drv) throws CannotCompileException {
        return 0;
    }
}
