package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.injection.code.*;

private class Callback extends InsnList
{
    private final MethodNode handler;
    private final AbstractInsnNode head;
    final Target target;
    final InjectionNodes.InjectionNode node;
    final LocalVariableNode[] locals;
    final Type[] localTypes;
    final int frameSize;
    final int extraArgs;
    final boolean canCaptureLocals;
    final boolean isAtReturn;
    final String desc;
    final String descl;
    final String[] argNames;
    int ctor;
    int invoke;
    private int marshalVar;
    private boolean captureArgs;
    final /* synthetic */ CallbackInjector this$0;
    
    Callback(final CallbackInjector this$0, final MethodNode handler, final Target target, final InjectionNodes.InjectionNode node, final LocalVariableNode[] locals, final boolean captureLocals) {
        this.this$0 = this$0;
        super();
        this.marshalVar = -1;
        this.captureArgs = true;
        this.handler = handler;
        this.target = target;
        this.head = target.insns.getFirst();
        this.node = node;
        this.locals = locals;
        this.localTypes = (Type[])((locals != null) ? new Type[locals.length] : null);
        this.frameSize = Bytecode.getFirstNonArgLocalIndex(target.arguments, !this$0.isStatic());
        List<String> argNames = null;
        if (locals != null) {
            final int baseArgIndex = this$0.isStatic() ? 0 : 1;
            argNames = new ArrayList<String>();
            for (int l = 0; l <= locals.length; ++l) {
                if (l == this.frameSize) {
                    argNames.add((target.returnType == Type.VOID_TYPE) ? "ci" : "cir");
                }
                if (l < locals.length && locals[l] != null) {
                    this.localTypes[l] = Type.getType(locals[l].desc);
                    if (l >= baseArgIndex) {
                        argNames.add(CallbackInjector.meltSnowman(l, locals[l].name));
                    }
                }
            }
        }
        this.extraArgs = Math.max(0, Bytecode.getFirstNonArgLocalIndex(this.handler) - (this.frameSize + 1));
        this.argNames = (String[])((argNames != null) ? ((String[])argNames.toArray(new String[argNames.size()])) : null);
        this.canCaptureLocals = (captureLocals && locals != null && locals.length > this.frameSize);
        this.isAtReturn = (this.node.getCurrentTarget() instanceof InsnNode && this.isValueReturnOpcode(this.node.getCurrentTarget().getOpcode()));
        this.desc = target.getCallbackDescriptor(this.localTypes, target.arguments);
        this.descl = target.getCallbackDescriptor(true, this.localTypes, target.arguments, this.frameSize, this.extraArgs);
        this.invoke = target.arguments.length + (this.canCaptureLocals ? (this.localTypes.length - this.frameSize) : 0);
    }
    
    private boolean isValueReturnOpcode(final int opcode) {
        return opcode >= 172 && opcode < 177;
    }
    
    String getDescriptor() {
        return this.canCaptureLocals ? this.descl : this.desc;
    }
    
    String getDescriptorWithAllLocals() {
        return this.target.getCallbackDescriptor(true, this.localTypes, this.target.arguments, this.frameSize, 32767);
    }
    
    String getCallbackInfoConstructorDescriptor() {
        return this.isAtReturn ? CallbackInfo.getConstructorDescriptor(this.target.returnType) : CallbackInfo.getConstructorDescriptor();
    }
    
    void add(final AbstractInsnNode insn, final boolean ctorStack, final boolean invokeStack) {
        this.add(insn, ctorStack, invokeStack, false);
    }
    
    void add(final AbstractInsnNode insn, final boolean ctorStack, final boolean invokeStack, final boolean head) {
        if (head) {
            this.target.insns.insertBefore(this.head, insn);
        }
        else {
            this.add(insn);
        }
        this.ctor += (ctorStack ? 1 : 0);
        this.invoke += (invokeStack ? 1 : 0);
    }
    
    void inject() {
        this.target.insertBefore(this.node, this);
        this.target.addToStack(Math.max(this.invoke, this.ctor));
    }
    
    boolean checkDescriptor(final String desc) {
        if (this.getDescriptor().equals(desc)) {
            return true;
        }
        if (this.target.getSimpleCallbackDescriptor().equals(desc) && !this.canCaptureLocals) {
            this.captureArgs = false;
            return true;
        }
        final Type[] inTypes = Type.getArgumentTypes(desc);
        final Type[] myTypes = Type.getArgumentTypes(this.descl);
        if (inTypes.length != myTypes.length) {
            return false;
        }
        for (int arg = 0; arg < myTypes.length; ++arg) {
            final Type type = inTypes[arg];
            if (!type.equals(myTypes[arg])) {
                if (type.getSort() == 9) {
                    return false;
                }
                if (Annotations.getInvisibleParameter(this.handler, Coerce.class, arg) == null) {
                    return false;
                }
                if (!Injector.canCoerce(inTypes[arg], myTypes[arg])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    boolean captureArgs() {
        return this.captureArgs;
    }
    
    int marshalVar() {
        if (this.marshalVar < 0) {
            this.marshalVar = this.target.allocateLocal();
        }
        return this.marshalVar;
    }
}
