package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.injection.invoke.*;

public class ModifyArgsInjectionInfo extends InjectionInfo
{
    public ModifyArgsInjectionInfo(final MixinTargetContext mixin, final MethodNode method, final AnnotationNode annotation) {
        super(mixin, method, annotation);
    }
    
    @Override
    protected Injector parseInjector(final AnnotationNode injectAnnotation) {
        return new ModifyArgsInjector(this);
    }
    
    @Override
    protected String getDescription() {
        return "Multi-argument modifier method";
    }
}
