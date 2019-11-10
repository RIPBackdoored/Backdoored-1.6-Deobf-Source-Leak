package javassist;

import javassist.compiler.ast.*;
import javassist.compiler.*;
import javassist.bytecode.*;

public abstract static class Initializer
{
    public Initializer() {
        super();
    }
    
    public static Initializer constant(final int i) {
        return new IntInitializer(i);
    }
    
    public static Initializer constant(final boolean b) {
        return new IntInitializer(b ? 1 : 0);
    }
    
    public static Initializer constant(final long l) {
        return new LongInitializer(l);
    }
    
    public static Initializer constant(final float l) {
        return new FloatInitializer(l);
    }
    
    public static Initializer constant(final double d) {
        return new DoubleInitializer(d);
    }
    
    public static Initializer constant(final String s) {
        return new StringInitializer(s);
    }
    
    public static Initializer byParameter(final int nth) {
        final ParamInitializer i = new ParamInitializer();
        i.nthParam = nth;
        return i;
    }
    
    public static Initializer byNew(final CtClass objectType) {
        final NewInitializer i = new NewInitializer();
        i.objectType = objectType;
        i.stringParams = null;
        i.withConstructorParams = false;
        return i;
    }
    
    public static Initializer byNew(final CtClass objectType, final String[] stringParams) {
        final NewInitializer i = new NewInitializer();
        i.objectType = objectType;
        i.stringParams = stringParams;
        i.withConstructorParams = false;
        return i;
    }
    
    public static Initializer byNewWithParams(final CtClass objectType) {
        final NewInitializer i = new NewInitializer();
        i.objectType = objectType;
        i.stringParams = null;
        i.withConstructorParams = true;
        return i;
    }
    
    public static Initializer byNewWithParams(final CtClass objectType, final String[] stringParams) {
        final NewInitializer i = new NewInitializer();
        i.objectType = objectType;
        i.stringParams = stringParams;
        i.withConstructorParams = true;
        return i;
    }
    
    public static Initializer byCall(final CtClass methodClass, final String methodName) {
        final MethodInitializer i = new MethodInitializer();
        i.objectType = methodClass;
        i.methodName = methodName;
        i.stringParams = null;
        i.withConstructorParams = false;
        return i;
    }
    
    public static Initializer byCall(final CtClass methodClass, final String methodName, final String[] stringParams) {
        final MethodInitializer i = new MethodInitializer();
        i.objectType = methodClass;
        i.methodName = methodName;
        i.stringParams = stringParams;
        i.withConstructorParams = false;
        return i;
    }
    
    public static Initializer byCallWithParams(final CtClass methodClass, final String methodName) {
        final MethodInitializer i = new MethodInitializer();
        i.objectType = methodClass;
        i.methodName = methodName;
        i.stringParams = null;
        i.withConstructorParams = true;
        return i;
    }
    
    public static Initializer byCallWithParams(final CtClass methodClass, final String methodName, final String[] stringParams) {
        final MethodInitializer i = new MethodInitializer();
        i.objectType = methodClass;
        i.methodName = methodName;
        i.stringParams = stringParams;
        i.withConstructorParams = true;
        return i;
    }
    
    public static Initializer byNewArray(final CtClass type, final int size) throws NotFoundException {
        return new ArrayInitializer(type.getComponentType(), size);
    }
    
    public static Initializer byNewArray(final CtClass type, final int[] sizes) {
        return new MultiArrayInitializer(type, sizes);
    }
    
    public static Initializer byExpr(final String source) {
        return new CodeInitializer(source);
    }
    
    static Initializer byExpr(final ASTree source) {
        return new PtreeInitializer(source);
    }
    
    void check(final String desc) throws CannotCompileException {
    }
    
    abstract int compile(final CtClass p0, final String p1, final Bytecode p2, final CtClass[] p3, final Javac p4) throws CannotCompileException;
    
    abstract int compileIfStatic(final CtClass p0, final String p1, final Bytecode p2, final Javac p3) throws CannotCompileException;
    
    int getConstantValue(final ConstPool cp, final CtClass type) {
        return 0;
    }
}
