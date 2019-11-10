package org.spongepowered.asm.lib.tree.analysis;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;

public class SourceValue implements Value
{
    public final int size;
    public final Set<AbstractInsnNode> insns;
    
    public SourceValue(final int size) {
        this(size, SmallSet.emptySet());
    }
    
    public SourceValue(final int size, final AbstractInsnNode insn) {
        super();
        this.size = size;
        this.insns = new SmallSet<AbstractInsnNode>(insn, null);
    }
    
    public SourceValue(final int size, final Set<AbstractInsnNode> insns) {
        super();
        this.size = size;
        this.insns = insns;
    }
    
    public int getSize() {
        return this.size;
    }
    
    @Override
    public boolean equals(final Object value) {
        if (!(value instanceof SourceValue)) {
            return false;
        }
        final SourceValue v = (SourceValue)value;
        return this.size == v.size && this.insns.equals(v.insns);
    }
    
    @Override
    public int hashCode() {
        return this.insns.hashCode();
    }
}
