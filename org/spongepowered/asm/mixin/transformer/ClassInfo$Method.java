package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

public class Method extends Member
{
    private final List<FrameData> frames;
    private boolean isAccessor;
    final /* synthetic */ ClassInfo this$0;
    
    public Method(final ClassInfo this$0, final Member member) {
        this.this$0 = this$0;
        super(member);
        this.frames = ((member instanceof Method) ? ((Method)member).frames : null);
    }
    
    public Method(final ClassInfo this$0, final MethodNode method) {
        this(this$0, method, false);
        this.setUnique(Annotations.getVisible(method, Unique.class) != null);
        this.isAccessor = (Annotations.getSingleVisible(method, Accessor.class, Invoker.class) != null);
    }
    
    public Method(final ClassInfo this$0, final MethodNode method, final boolean injected) {
        this.this$0 = this$0;
        super(Type.METHOD, method.name, method.desc, method.access, injected);
        this.frames = this.gatherFrames(method);
        this.setUnique(Annotations.getVisible(method, Unique.class) != null);
        this.isAccessor = (Annotations.getSingleVisible(method, Accessor.class, Invoker.class) != null);
    }
    
    public Method(final ClassInfo this$0, final String name, final String desc) {
        this.this$0 = this$0;
        super(Type.METHOD, name, desc, 1, false);
        this.frames = null;
    }
    
    public Method(final ClassInfo this$0, final String name, final String desc, final int access) {
        this.this$0 = this$0;
        super(Type.METHOD, name, desc, access, false);
        this.frames = null;
    }
    
    public Method(final ClassInfo this$0, final String name, final String desc, final int access, final boolean injected) {
        this.this$0 = this$0;
        super(Type.METHOD, name, desc, access, injected);
        this.frames = null;
    }
    
    private List<FrameData> gatherFrames(final MethodNode method) {
        final List<FrameData> frames = new ArrayList<FrameData>();
        for (final AbstractInsnNode insn : method.instructions) {
            if (insn instanceof FrameNode) {
                frames.add(new FrameData(method.instructions.indexOf(insn), (FrameNode)insn));
            }
        }
        return frames;
    }
    
    public List<FrameData> getFrames() {
        return this.frames;
    }
    
    @Override
    public ClassInfo getOwner() {
        return this.this$0;
    }
    
    public boolean isAccessor() {
        return this.isAccessor;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Method && super.equals(obj);
    }
    
    @Override
    public /* bridge */ String toString() {
        return super.toString();
    }
    
    @Override
    public /* bridge */ int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public /* bridge */ boolean equals(final String name, final String desc) {
        return super.equals(name, desc);
    }
    
    @Override
    public /* bridge */ String remapTo(final String desc) {
        return super.remapTo(desc);
    }
    
    @Override
    public /* bridge */ String renameTo(final String name) {
        return super.renameTo(name);
    }
    
    @Override
    public /* bridge */ int getAccess() {
        return super.getAccess();
    }
    
    @Override
    public /* bridge */ ClassInfo getImplementor() {
        return super.getImplementor();
    }
    
    @Override
    public /* bridge */ boolean matchesFlags(final int flags) {
        return super.matchesFlags(flags);
    }
    
    @Override
    public /* bridge */ void setDecoratedFinal(final boolean decoratedFinal, final boolean decoratedMutable) {
        super.setDecoratedFinal(decoratedFinal, decoratedMutable);
    }
    
    @Override
    public /* bridge */ boolean isDecoratedMutable() {
        return super.isDecoratedMutable();
    }
    
    @Override
    public /* bridge */ boolean isDecoratedFinal() {
        return super.isDecoratedFinal();
    }
    
    @Override
    public /* bridge */ void setUnique(final boolean unique) {
        super.setUnique(unique);
    }
    
    @Override
    public /* bridge */ boolean isUnique() {
        return super.isUnique();
    }
    
    @Override
    public /* bridge */ boolean isSynthetic() {
        return super.isSynthetic();
    }
    
    @Override
    public /* bridge */ boolean isFinal() {
        return super.isFinal();
    }
    
    @Override
    public /* bridge */ boolean isAbstract() {
        return super.isAbstract();
    }
    
    @Override
    public /* bridge */ boolean isStatic() {
        return super.isStatic();
    }
    
    @Override
    public /* bridge */ boolean isPrivate() {
        return super.isPrivate();
    }
    
    @Override
    public /* bridge */ boolean isRemapped() {
        return super.isRemapped();
    }
    
    @Override
    public /* bridge */ boolean isRenamed() {
        return super.isRenamed();
    }
    
    @Override
    public /* bridge */ boolean isInjected() {
        return super.isInjected();
    }
    
    @Override
    public /* bridge */ String getDesc() {
        return super.getDesc();
    }
    
    @Override
    public /* bridge */ String getOriginalDesc() {
        return super.getOriginalDesc();
    }
    
    @Override
    public /* bridge */ String getName() {
        return super.getName();
    }
    
    @Override
    public /* bridge */ String getOriginalName() {
        return super.getOriginalName();
    }
}
