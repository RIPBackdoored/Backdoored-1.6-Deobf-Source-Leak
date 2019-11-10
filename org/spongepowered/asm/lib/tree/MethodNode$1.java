package org.spongepowered.asm.lib.tree;

import java.util.*;

class MethodNode$1 extends ArrayList<Object> {
    final /* synthetic */ MethodNode this$0;
    
    MethodNode$1(final MethodNode this$0, final int x0) {
        this.this$0 = this$0;
        super(x0);
    }
    
    @Override
    public boolean add(final Object o) {
        this.this$0.annotationDefault = o;
        return super.add(o);
    }
}