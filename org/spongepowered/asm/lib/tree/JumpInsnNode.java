package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class JumpInsnNode extends AbstractInsnNode
{
    public LabelNode label;
    
    public JumpInsnNode(final int opcode, final LabelNode label) {
        super(opcode);
        this.label = label;
    }
    
    public void setOpcode(final int opcode) {
        this.opcode = opcode;
    }
    
    @Override
    public int getType() {
        return 7;
    }
    
    @Override
    public void accept(final MethodVisitor mv) {
        mv.visitJumpInsn(this.opcode, this.label.getLabel());
        this.acceptAnnotations(mv);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> labels) {
        return new JumpInsnNode(this.opcode, AbstractInsnNode.clone(this.label, labels)).cloneAnnotations(this);
    }
}
