package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import org.spongepowered.tools.obfuscation.mirror.*;

class AnnotatedElementShadowMethod extends AnnotatedElementShadow<ExecutableElement, MappingMethod>
{
    final /* synthetic */ AnnotatedMixinElementHandlerShadow this$0;
    
    public AnnotatedElementShadowMethod(final AnnotatedMixinElementHandlerShadow this$0, final ExecutableElement element, final AnnotationHandle annotation, final boolean shouldRemap) {
        this.this$0 = this$0;
        super(element, annotation, shouldRemap, IMapping.Type.METHOD);
    }
    
    @Override
    public MappingMethod getMapping(final TypeHandle owner, final String name, final String desc) {
        return owner.getMappingMethod(name, desc);
    }
    
    @Override
    public void addMapping(final ObfuscationType type, final IMapping<?> remapped) {
        this.this$0.addMethodMapping(type, this.setObfuscatedName(remapped), this.getDesc(), remapped.getDesc());
    }
    
    @Override
    public /* bridge */ IMapping getMapping(final TypeHandle owner, final String name, final String desc) {
        return this.getMapping(owner, name, desc);
    }
}
