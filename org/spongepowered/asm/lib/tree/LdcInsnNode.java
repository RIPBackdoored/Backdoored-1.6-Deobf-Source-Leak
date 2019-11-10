package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class LdcInsnNode extends AbstractInsnNode
{
    public Object cst;
    
    public LdcInsnNode(final Object cst) {
        super(18);
        this.cst = cst;
    }
    
    @Override
    public int getType() {
        return 9;
    }
    
    @Override
    public void accept(final MethodVisitor mv) {
        mv.visitLdcInsn(this.cst);
        this.acceptAnnotations(mv);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> labels) {
        return new LdcInsnNode(this.cst).cloneAnnotations(this);
    }
}
