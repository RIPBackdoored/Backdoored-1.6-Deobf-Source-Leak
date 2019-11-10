package javassist.compiler;

import javassist.*;

public class CompileError extends Exception
{
    private Lex lex;
    private String reason;
    
    public CompileError(final String s, final Lex l) {
        super();
        this.reason = s;
        this.lex = l;
    }
    
    public CompileError(final String s) {
        super();
        this.reason = s;
        this.lex = null;
    }
    
    public CompileError(final CannotCompileException e) {
        this(e.getReason());
    }
    
    public CompileError(final NotFoundException e) {
        this("cannot find " + e.getMessage());
    }
    
    public Lex getLex() {
        return this.lex;
    }
    
    @Override
    public String getMessage() {
        return this.reason;
    }
    
    @Override
    public String toString() {
        return "compile error: " + this.reason;
    }
}
