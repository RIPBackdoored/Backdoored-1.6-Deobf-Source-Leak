package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

@AtCode("RETURN")
public class BeforeReturn extends InjectionPoint
{
    private final int ordinal;
    
    public BeforeReturn(final InjectionPointData data) {
        super(data);
        this.ordinal = data.getOrdinal();
    }
    
    @Override
    public boolean checkPriority(final int targetPriority, final int ownerPriority) {
        return true;
    }
    
    @Override
    public boolean find(final String desc, final InsnList insns, final Collection<AbstractInsnNode> nodes) {
        boolean found = false;
        final int returnOpcode = Type.getReturnType(desc).getOpcode(172);
        int ordinal = 0;
        for (final AbstractInsnNode insn : insns) {
            if (insn instanceof InsnNode && insn.getOpcode() == returnOpcode) {
                if (this.ordinal == -1 || this.ordinal == ordinal) {
                    nodes.add(insn);
                    found = true;
                }
                ++ordinal;
            }
        }
        return found;
    }
}
