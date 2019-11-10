package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.*;

abstract class TokenElement extends SignatureElement
{
    protected Token token;
    private boolean array;
    final /* synthetic */ SignatureParser this$1;
    
    TokenElement(final SignatureParser this$1) {
        this.this$1 = this$1;
        this$1.super();
    }
    
    public Token getToken() {
        if (this.token == null) {
            this.token = new Token();
        }
        return this.token;
    }
    
    protected void setArray() {
        this.array = true;
    }
    
    private boolean getArray() {
        final boolean array = this.array;
        this.array = false;
        return array;
    }
    
    @Override
    public void visitClassType(final String name) {
        this.getToken().setType(name);
    }
    
    @Override
    public SignatureVisitor visitClassBound() {
        this.getToken();
        return this.this$1.new BoundElement(this, true);
    }
    
    @Override
    public SignatureVisitor visitInterfaceBound() {
        this.getToken();
        return this.this$1.new BoundElement(this, false);
    }
    
    @Override
    public void visitInnerClassType(final String name) {
        this.token.addInnerClass(name);
    }
    
    @Override
    public SignatureVisitor visitArrayType() {
        this.setArray();
        return this;
    }
    
    @Override
    public SignatureVisitor visitTypeArgument(final char wildcard) {
        return this.this$1.new TypeArgElement(this, wildcard);
    }
    
    Token addTypeArgument() {
        return this.token.addTypeArgument('*').asToken();
    }
    
    IToken addTypeArgument(final char symbol) {
        return this.token.addTypeArgument(symbol).setArray(this.getArray());
    }
    
    IToken addTypeArgument(final String name) {
        return this.token.addTypeArgument(name).setArray(this.getArray());
    }
    
    IToken addTypeArgument(final Token token) {
        return this.token.addTypeArgument(token).setArray(this.getArray());
    }
    
    IToken addTypeArgument(final TokenHandle token) {
        return this.token.addTypeArgument(token).setArray(this.getArray());
    }
}
