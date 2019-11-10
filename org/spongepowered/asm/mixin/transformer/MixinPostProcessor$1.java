package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.*;

class MixinPostProcessor$1 extends ClassVisitor {
    final /* synthetic */ MixinPostProcessor this$0;
    
    MixinPostProcessor$1(final MixinPostProcessor this$0, final int x0, final ClassVisitor x1) {
        this.this$0 = this$0;
        super(x0, x1);
    }
    
    @Override
    public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        super.visit(version, access | 0x1, name, signature, superName, interfaces);
    }
    
    @Override
    public FieldVisitor visitField(int access, final String name, final String desc, final String signature, final Object value) {
        if ((access & 0x6) == 0x0) {
            access |= 0x1;
        }
        return super.visitField(access, name, desc, signature, value);
    }
    
    @Override
    public MethodVisitor visitMethod(int access, final String name, final String desc, final String signature, final String[] exceptions) {
        if ((access & 0x6) == 0x0) {
            access |= 0x1;
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }
}