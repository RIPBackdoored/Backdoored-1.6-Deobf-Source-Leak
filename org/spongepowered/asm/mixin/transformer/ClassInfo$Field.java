package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.*;

class Field extends Member
{
    final /* synthetic */ ClassInfo this$0;
    
    public Field(final ClassInfo this$0, final Member member) {
        this.this$0 = this$0;
        super(member);
    }
    
    public Field(final ClassInfo this$0, final FieldNode field) {
        this(this$0, field, false);
    }
    
    public Field(final ClassInfo this$0, final FieldNode field, final boolean injected) {
        this.this$0 = this$0;
        super(Type.FIELD, field.name, field.desc, field.access, injected);
        this.setUnique(Annotations.getVisible(field, Unique.class) != null);
        if (Annotations.getVisible(field, Shadow.class) != null) {
            final boolean decoratedFinal = Annotations.getVisible(field, Final.class) != null;
            final boolean decoratedMutable = Annotations.getVisible(field, Mutable.class) != null;
            this.setDecoratedFinal(decoratedFinal, decoratedMutable);
        }
    }
    
    public Field(final ClassInfo this$0, final String name, final String desc, final int access) {
        this.this$0 = this$0;
        super(Type.FIELD, name, desc, access, false);
    }
    
    public Field(final ClassInfo this$0, final String name, final String desc, final int access, final boolean injected) {
        this.this$0 = this$0;
        super(Type.FIELD, name, desc, access, injected);
    }
    
    @Override
    public ClassInfo getOwner() {
        return this.this$0;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Field && super.equals(obj);
    }
    
    @Override
    protected String getDisplayFormat() {
        return "%s:%s";
    }
}
