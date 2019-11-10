package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class VarInsnNode extends AbstractInsnNode
{
    public int var;
    
    public VarInsnNode(final int opcode, final int var) {
        super(opcode);
        this.var = var;
    }
    
    public void setOpcode(final int opcode) {
        this.opcode = opcode;
    }
    
    @Override
    public int getType() {
        return 2;
    }
    
    @Override
    public void accept(final MethodVisitor mv) {
        mv.visitVarInsn(this.opcode, this.var);
        this.acceptAnnotations(mv);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> labels) {
        return new VarInsnNode(this.opcode, this.var).cloneAnnotations(this);
    }
}
