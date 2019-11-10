package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.*;

class SignatureParser extends SignatureVisitor
{
    private FormalParamElement param;
    final /* synthetic */ ClassSignature this$0;
    
    SignatureParser(final ClassSignature this$0) {
        this.this$0 = this$0;
        super(327680);
    }
    
    @Override
    public void visitFormalTypeParameter(final String name) {
        this.param = new FormalParamElement(name);
    }
    
    @Override
    public SignatureVisitor visitClassBound() {
        return this.param.visitClassBound();
    }
    
    @Override
    public SignatureVisitor visitInterfaceBound() {
        return this.param.visitInterfaceBound();
    }
    
    @Override
    public SignatureVisitor visitSuperclass() {
        return new SuperClassElement();
    }
    
    @Override
    public SignatureVisitor visitInterface() {
        return new InterfaceElement();
    }
    
    abstract class SignatureElement extends SignatureVisitor
    {
        final /* synthetic */ SignatureParser this$1;
        
        public SignatureElement(final SignatureParser this$1) {
            this.this$1 = this$1;
            super(327680);
        }
    }
    
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
    
    class FormalParamElement extends TokenElement
    {
        private final TokenHandle handle;
        final /* synthetic */ SignatureParser this$1;
        
        FormalParamElement(final SignatureParser this$1, final String param) {
            this.this$1 = this$1;
            this$1.super();
            this.handle = this$1.this$0.getType(param);
            this.token = this.handle.asToken();
        }
    }
    
    class TypeArgElement extends TokenElement
    {
        private final TokenElement type;
        private final char wildcard;
        final /* synthetic */ SignatureParser this$1;
        
        TypeArgElement(final SignatureParser this$1, final TokenElement type, final char wildcard) {
            this.this$1 = this$1;
            this$1.super();
            this.type = type;
            this.wildcard = wildcard;
        }
        
        @Override
        public SignatureVisitor visitArrayType() {
            this.type.setArray();
            return this;
        }
        
        @Override
        public void visitBaseType(final char descriptor) {
            this.token = this.type.addTypeArgument(descriptor).asToken();
        }
        
        @Override
        public void visitTypeVariable(final String name) {
            final TokenHandle token = this.this$1.this$0.getType(name);
            this.token = this.type.addTypeArgument(token).setWildcard(this.wildcard).asToken();
        }
        
        @Override
        public void visitClassType(final String name) {
            this.token = this.type.addTypeArgument(name).setWildcard(this.wildcard).asToken();
        }
        
        @Override
        public void visitTypeArgument() {
            this.token.addTypeArgument('*');
        }
        
        @Override
        public SignatureVisitor visitTypeArgument(final char wildcard) {
            return this.this$1.new TypeArgElement(this, wildcard);
        }
        
        @Override
        public void visitEnd() {
        }
    }
    
    class BoundElement extends TokenElement
    {
        private final TokenElement type;
        private final boolean classBound;
        final /* synthetic */ SignatureParser this$1;
        
        BoundElement(final SignatureParser this$1, final TokenElement type, final boolean classBound) {
            this.this$1 = this$1;
            this$1.super();
            this.type = type;
            this.classBound = classBound;
        }
        
        @Override
        public void visitClassType(final String name) {
            this.token = this.type.token.addBound(name, this.classBound);
        }
        
        @Override
        public void visitTypeArgument() {
            this.token.addTypeArgument('*');
        }
        
        @Override
        public SignatureVisitor visitTypeArgument(final char wildcard) {
            return this.this$1.new TypeArgElement(this, wildcard);
        }
    }
    
    class SuperClassElement extends TokenElement
    {
        final /* synthetic */ SignatureParser this$1;
        
        SuperClassElement(final SignatureParser this$1) {
            this.this$1 = this$1;
            this$1.super();
        }
        
        @Override
        public void visitEnd() {
            this.this$1.this$0.setSuperClass(this.token);
        }
    }
    
    class InterfaceElement extends TokenElement
    {
        final /* synthetic */ SignatureParser this$1;
        
        InterfaceElement(final SignatureParser this$1) {
            this.this$1 = this$1;
            this$1.super();
        }
        
        @Override
        public void visitEnd() {
            this.this$1.this$0.addInterface(this.token);
        }
    }
}
