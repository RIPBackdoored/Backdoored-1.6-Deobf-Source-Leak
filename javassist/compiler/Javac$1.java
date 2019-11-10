package javassist.compiler;

import javassist.bytecode.*;
import javassist.compiler.ast.*;

class Javac$1 implements ProceedHandler {
    final /* synthetic */ String val$m;
    final /* synthetic */ ASTree val$texpr;
    final /* synthetic */ Javac this$0;
    
    Javac$1(final Javac this$0, final String val$m, final ASTree val$texpr) {
        this.this$0 = this$0;
        this.val$m = val$m;
        this.val$texpr = val$texpr;
        super();
    }
    
    @Override
    public void doit(final JvstCodeGen gen, final Bytecode b, final ASTList args) throws CompileError {
        ASTree expr = new Member(this.val$m);
        if (this.val$texpr != null) {
            expr = Expr.make(46, this.val$texpr, expr);
        }
        expr = CallExpr.makeCall(expr, args);
        gen.compileExpr(expr);
        gen.addNullIfVoid();
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker check, final ASTList args) throws CompileError {
        ASTree expr = new Member(this.val$m);
        if (this.val$texpr != null) {
            expr = Expr.make(46, this.val$texpr, expr);
        }
        expr = CallExpr.makeCall(expr, args);
        expr.accept(check);
        check.addNullIfVoid();
    }
}