package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class NewInitializer extends Initializer
{
    CtClass objectType;
    String[] stringParams;
    boolean withConstructorParams;
    
    NewInitializer() {
        super();
    }
    
    @Override
    int compile(final CtClass type, final String name, final Bytecode code, final CtClass[] parameters, final Javac drv) throws CannotCompileException {
        code.addAload(0);
        code.addNew(this.objectType);
        code.add(89);
        code.addAload(0);
        int stacksize;
        if (this.stringParams == null) {
            stacksize = 4;
        }
        else {
            stacksize = this.compileStringParameter(code) + 4;
        }
        if (this.withConstructorParams) {
            stacksize += CtNewWrappedMethod.compileParameterList(code, parameters, 1);
        }
        code.addInvokespecial(this.objectType, "<init>", this.getDescriptor());
        code.addPutfield(Bytecode.THIS, name, Descriptor.of(type));
        return stacksize;
    }
    
    private String getDescriptor() {
        final String desc3 = "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)V";
        if (this.stringParams == null) {
            if (this.withConstructorParams) {
                return "(Ljava/lang/Object;[Ljava/lang/Object;)V";
            }
            return "(Ljava/lang/Object;)V";
        }
        else {
            if (this.withConstructorParams) {
                return "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)V";
            }
            return "(Ljava/lang/Object;[Ljava/lang/String;)V";
        }
    }
    
    @Override
    int compileIfStatic(final CtClass type, final String name, final Bytecode code, final Javac drv) throws CannotCompileException {
        code.addNew(this.objectType);
        code.add(89);
        int stacksize = 2;
        String desc;
        if (this.stringParams == null) {
            desc = "()V";
        }
        else {
            desc = "([Ljava/lang/String;)V";
            stacksize += this.compileStringParameter(code);
        }
        code.addInvokespecial(this.objectType, "<init>", desc);
        code.addPutstatic(Bytecode.THIS, name, Descriptor.of(type));
        return stacksize;
    }
    
    protected final int compileStringParameter(final Bytecode code) throws CannotCompileException {
        final int nparam = this.stringParams.length;
        code.addIconst(nparam);
        code.addAnewarray("java.lang.String");
        for (int j = 0; j < nparam; ++j) {
            code.add(89);
            code.addIconst(j);
            code.addLdc(this.stringParams[j]);
            code.add(83);
        }
        return 4;
    }
}
