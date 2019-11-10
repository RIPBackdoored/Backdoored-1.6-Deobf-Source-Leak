package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.*;
import java.util.*;

class SignatureRemapper extends SignatureWriter
{
    private final Set<String> localTypeVars;
    final /* synthetic */ ClassSignature this$0;
    
    SignatureRemapper(final ClassSignature this$0) {
        this.this$0 = this$0;
        super();
        this.localTypeVars = new HashSet<String>();
    }
    
    @Override
    public void visitFormalTypeParameter(final String name) {
        this.localTypeVars.add(name);
        super.visitFormalTypeParameter(name);
    }
    
    @Override
    public void visitTypeVariable(final String name) {
        if (!this.localTypeVars.contains(name)) {
            final TypeVar typeVar = this.this$0.getTypeVar(name);
            if (typeVar != null) {
                super.visitTypeVariable(typeVar.toString());
                return;
            }
        }
        super.visitTypeVariable(name);
    }
}
