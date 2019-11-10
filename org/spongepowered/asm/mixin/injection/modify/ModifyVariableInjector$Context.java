package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;

static class Context extends LocalVariableDiscriminator.Context
{
    final InsnList insns;
    
    public Context(final Type returnType, final boolean argsOnly, final Target target, final AbstractInsnNode node) {
        super(returnType, argsOnly, target, node);
        this.insns = new InsnList();
    }
}
