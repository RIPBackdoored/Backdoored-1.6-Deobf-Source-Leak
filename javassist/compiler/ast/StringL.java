package javassist.compiler.ast;

import javassist.compiler.*;

public class StringL extends ASTree
{
    protected String text;
    
    public StringL(final String t) {
        super();
        this.text = t;
    }
    
    public String get() {
        return this.text;
    }
    
    @Override
    public String toString() {
        return "\"" + this.text + "\"";
    }
    
    @Override
    public void accept(final Visitor v) throws CompileError {
        v.atStringL(this);
    }
}
