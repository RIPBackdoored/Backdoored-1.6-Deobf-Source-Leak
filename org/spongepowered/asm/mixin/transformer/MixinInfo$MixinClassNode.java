package org.spongepowered.asm.mixin.transformer;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;

class MixinClassNode extends ClassNode
{
    public final List<MixinMethodNode> mixinMethods;
    final /* synthetic */ MixinInfo this$0;
    
    public MixinClassNode(final MixinInfo this$0, final MixinInfo mixin) {
        this(this$0, 327680);
    }
    
    public MixinClassNode(final MixinInfo this$0, final int api) {
        this.this$0 = this$0;
        super(api);
        this.mixinMethods = (List<MixinMethodNode>)this.methods;
    }
    
    public MixinInfo getMixin() {
        return this.this$0;
    }
    
    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodNode method = this.this$0.new MixinMethodNode(access, name, desc, signature, exceptions);
        this.methods.add(method);
        return method;
    }
}
