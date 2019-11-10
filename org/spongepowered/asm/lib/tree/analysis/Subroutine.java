package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

class Subroutine
{
    LabelNode start;
    boolean[] access;
    List<JumpInsnNode> callers;
    
    private Subroutine() {
        super();
    }
    
    Subroutine(final LabelNode start, final int maxLocals, final JumpInsnNode caller) {
        super();
        this.start = start;
        this.access = new boolean[maxLocals];
        (this.callers = new ArrayList<JumpInsnNode>()).add(caller);
    }
    
    public Subroutine copy() {
        final Subroutine result = new Subroutine();
        result.start = this.start;
        result.access = new boolean[this.access.length];
        System.arraycopy(this.access, 0, result.access, 0, this.access.length);
        result.callers = new ArrayList<JumpInsnNode>(this.callers);
        return result;
    }
    
    public boolean merge(final Subroutine subroutine) throws AnalyzerException {
        boolean changes = false;
        for (int i = 0; i < this.access.length; ++i) {
            if (subroutine.access[i] && !this.access[i]) {
                this.access[i] = true;
                changes = true;
            }
        }
        if (subroutine.start == this.start) {
            for (int i = 0; i < subroutine.callers.size(); ++i) {
                final JumpInsnNode caller = subroutine.callers.get(i);
                if (!this.callers.contains(caller)) {
                    this.callers.add(caller);
                    changes = true;
                }
            }
        }
        return changes;
    }
}
