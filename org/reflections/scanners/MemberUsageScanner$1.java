package org.reflections.scanners;

import org.reflections.*;
import javassist.*;
import javassist.expr.*;

class MemberUsageScanner$1 extends ExprEditor {
    final /* synthetic */ String val$key;
    final /* synthetic */ MemberUsageScanner this$0;
    
    MemberUsageScanner$1(final MemberUsageScanner this$0, final String val$key) {
        this.this$0 = this$0;
        this.val$key = val$key;
        super();
    }
    
    @Override
    public void edit(final NewExpr e) throws CannotCompileException {
        try {
            MemberUsageScanner.access$000(this.this$0, e.getConstructor().getDeclaringClass().getName() + ".<init>(" + this.this$0.parameterNames(e.getConstructor().getMethodInfo()) + ")", e.getLineNumber(), this.val$key);
        }
        catch (NotFoundException e2) {
            throw new ReflectionsException("Could not find new instance usage in " + this.val$key, e2);
        }
    }
    
    @Override
    public void edit(final MethodCall m) throws CannotCompileException {
        try {
            MemberUsageScanner.access$000(this.this$0, m.getMethod().getDeclaringClass().getName() + "." + m.getMethodName() + "(" + this.this$0.parameterNames(m.getMethod().getMethodInfo()) + ")", m.getLineNumber(), this.val$key);
        }
        catch (NotFoundException e) {
            throw new ReflectionsException("Could not find member " + m.getClassName() + " in " + this.val$key, e);
        }
    }
    
    @Override
    public void edit(final ConstructorCall c) throws CannotCompileException {
        try {
            MemberUsageScanner.access$000(this.this$0, c.getConstructor().getDeclaringClass().getName() + ".<init>(" + this.this$0.parameterNames(c.getConstructor().getMethodInfo()) + ")", c.getLineNumber(), this.val$key);
        }
        catch (NotFoundException e) {
            throw new ReflectionsException("Could not find member " + c.getClassName() + " in " + this.val$key, e);
        }
    }
    
    @Override
    public void edit(final FieldAccess f) throws CannotCompileException {
        try {
            MemberUsageScanner.access$000(this.this$0, f.getField().getDeclaringClass().getName() + "." + f.getFieldName(), f.getLineNumber(), this.val$key);
        }
        catch (NotFoundException e) {
            throw new ReflectionsException("Could not find member " + f.getFieldName() + " in " + this.val$key, e);
        }
    }
}