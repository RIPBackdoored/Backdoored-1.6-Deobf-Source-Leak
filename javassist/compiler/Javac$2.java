package javassist.compiler;

import javassist.bytecode.*;
import javassist.compiler.ast.*;

class Javac$2 implements ProceedHandler {
    final /* synthetic */ String val$c;
    final /* synthetic */ String val$m;
    final /* synthetic */ Javac this$0;
    
    Javac$2(final Javac this$0, final String val$c, final String val$m) {
        this.this$0 = this$0;
        this.val$c = val$c;
        this.val$m = val$m;
        super();
    }
    
    @Override
    public void doit(final JvstCodeGen gen, final Bytecode b, final ASTList args) throws CompileError {
        Expr expr = Expr.make(35, new Symbol(this.val$c), new Member(this.val$m));
        expr = CallExpr.makeCall(expr, args);
        gen.compileExpr(expr);
        gen.addNullIfVoid();
    }
    
    @Override
    public void setReturnType(final JvstTypeChecker check, final ASTList args) throws CompileError {
        Expr expr = Expr.make(35, new Symbol(this.val$c), new Member(this.val$m));
        expr = CallExpr.makeCall(expr, args);
        expr.accept(check);
        check.addNullIfVoid();
    }
}