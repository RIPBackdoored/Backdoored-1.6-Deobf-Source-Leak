package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class MultiANewArrayInsnNode extends AbstractInsnNode
{
    public String desc;
    public int dims;
    
    public MultiANewArrayInsnNode(final String desc, final int dims) {
        super(197);
        this.desc = desc;
        this.dims = dims;
    }
    
    @Override
    public int getType() {
        return 13;
    }
    
    @Override
    public void accept(final MethodVisitor mv) {
        mv.visitMultiANewArrayInsn(this.desc, this.dims);
        this.acceptAnnotations(mv);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> labels) {
        return new MultiANewArrayInsnNode(this.desc, this.dims).cloneAnnotations(this);
    }
}
