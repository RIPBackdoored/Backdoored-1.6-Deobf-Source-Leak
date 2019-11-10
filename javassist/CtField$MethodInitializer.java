package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class MethodInitializer extends NewInitializer
{
    String methodName;
    
    MethodInitializer() {
        super();
    }
    
    @Override
    int compile(final CtClass type, final String name, final Bytecode code, final CtClass[] parameters, final Javac drv) throws CannotCompileException {
        code.addAload(0);
        code.addAload(0);
        int stacksize;
        if (this.stringParams == null) {
            stacksize = 2;
        }
        else {
            stacksize = this.compileStringParameter(code) + 2;
        }
        if (this.withConstructorParams) {
            stacksize += CtNewWrappedMethod.compileParameterList(code, parameters, 1);
        }
        final String typeDesc = Descriptor.of(type);
        final String mDesc = this.getDescriptor() + typeDesc;
        code.addInvokestatic(this.objectType, this.methodName, mDesc);
        code.addPutfield(Bytecode.THIS, name, typeDesc);
        return stacksize;
    }
    
    private String getDescriptor() {
        final String desc3 = "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)";
        if (this.stringParams == null) {
            if (this.withConstructorParams) {
                return "(Ljava/lang/Object;[Ljava/lang/Object;)";
            }
            return "(Ljava/lang/Object;)";
        }
        else {
            if (this.withConstructorParams) {
                return "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)";
            }
            return "(Ljava/lang/Object;[Ljava/lang/String;)";
        }
    }
    
    @Override
    int compileIfStatic(final CtClass type, final String name, final Bytecode code, final Javac drv) throws CannotCompileException {
        int stacksize = 1;
        String desc;
        if (this.stringParams == null) {
            desc = "()";
        }
        else {
            desc = "([Ljava/lang/String;)";
            stacksize += this.compileStringParameter(code);
        }
        final String typeDesc = Descriptor.of(type);
        code.addInvokestatic(this.objectType, this.methodName, desc + typeDesc);
        code.addPutstatic(Bytecode.THIS, name, typeDesc);
        return stacksize;
    }
}
