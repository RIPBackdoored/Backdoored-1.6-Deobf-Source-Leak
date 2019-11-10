package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import org.spongepowered.tools.obfuscation.mirror.*;

class AnnotatedElementShadowField extends AnnotatedElementShadow<VariableElement, MappingField>
{
    final /* synthetic */ AnnotatedMixinElementHandlerShadow this$0;
    
    public AnnotatedElementShadowField(final AnnotatedMixinElementHandlerShadow this$0, final VariableElement element, final AnnotationHandle annotation, final boolean shouldRemap) {
        this.this$0 = this$0;
        super(element, annotation, shouldRemap, IMapping.Type.FIELD);
    }
    
    @Override
    public MappingField getMapping(final TypeHandle owner, final String name, final String desc) {
        return new MappingField(owner.getName(), name, desc);
    }
    
    @Override
    public void addMapping(final ObfuscationType type, final IMapping<?> remapped) {
        this.this$0.addFieldMapping(type, this.setObfuscatedName(remapped), this.getDesc(), remapped.getDesc());
    }
    
    @Override
    public /* bridge */ IMapping getMapping(final TypeHandle owner, final String name, final String desc) {
        return this.getMapping(owner, name, desc);
    }
}
