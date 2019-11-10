package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.asm.mixin.gen.*;
import javax.lang.model.element.*;

static class AnnotatedElementInvoker extends AnnotatedElementAccessor
{
    public AnnotatedElementInvoker(final ExecutableElement element, final AnnotationHandle annotation, final boolean shouldRemap) {
        super(element, annotation, shouldRemap);
    }
    
    @Override
    public String getAccessorDesc() {
        return TypeUtils.getDescriptor(this.getElement());
    }
    
    @Override
    public AccessorInfo.AccessorType getAccessorType() {
        return AccessorInfo.AccessorType.METHOD_PROXY;
    }
    
    @Override
    public String getTargetTypeName() {
        return TypeUtils.getJavaSignature(((AnnotatedElement<Element>)this).getElement());
    }
}
