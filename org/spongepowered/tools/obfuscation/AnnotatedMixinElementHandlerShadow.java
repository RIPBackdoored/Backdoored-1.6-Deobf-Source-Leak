package org.spongepowered.tools.obfuscation;

import java.util.*;
import javax.tools.*;
import javax.annotation.processing.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import javax.lang.model.element.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;

class AnnotatedMixinElementHandlerShadow extends AnnotatedMixinElementHandler
{
    AnnotatedMixinElementHandlerShadow(final IMixinAnnotationProcessor ap, final AnnotatedMixin mixin) {
        super(ap, mixin);
    }
    
    public void registerShadow(final AnnotatedElementShadow<?, ?> elem) {
        this.validateTarget(elem.getElement(), elem.getAnnotation(), elem.getName(), "@Shadow");
        if (!elem.shouldRemap()) {
            return;
        }
        for (final TypeHandle target : this.mixin.getTargets()) {
            this.registerShadowForTarget(elem, target);
        }
    }
    
    private void registerShadowForTarget(final AnnotatedElementShadow<?, ?> elem, final TypeHandle target) {
        final ObfuscationData<? extends IMapping<?>> obfData = (ObfuscationData<? extends IMapping<?>>)elem.getObfuscationData(this.obf.getDataProvider(), target);
        if (obfData.isEmpty()) {
            final String info = this.mixin.isMultiTarget() ? (" in target " + target) : "";
            if (target.isSimulated()) {
                elem.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Shadow " + elem);
            }
            else {
                elem.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Shadow " + elem);
            }
            return;
        }
        for (final ObfuscationType type : obfData) {
            try {
                elem.addMapping(type, (IMapping<?>)obfData.get(type));
            }
            catch (Mappings.MappingConflictException ex) {
                elem.printMessage(this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Shadow " + elem + ": " + ex.getNew().getSimpleName() + " for target " + target + " conflicts with existing mapping " + ex.getOld().getSimpleName());
            }
        }
    }
    
    abstract static class AnnotatedElementShadow<E extends Element, M extends IMapping<M>> extends AnnotatedElement<E>
    {
        private final boolean shouldRemap;
        private final ShadowElementName name;
        private final IMapping.Type type;
        
        protected AnnotatedElementShadow(final E element, final AnnotationHandle annotation, final boolean shouldRemap, final IMapping.Type type) {
            super(element, annotation);
            this.shouldRemap = shouldRemap;
            this.name = new ShadowElementName(element, annotation);
            this.type = type;
        }
        
        public boolean shouldRemap() {
            return this.shouldRemap;
        }
        
        public ShadowElementName getName() {
            return this.name;
        }
        
        public IMapping.Type getElementType() {
            return this.type;
        }
        
        @Override
        public String toString() {
            return this.getElementType().name().toLowerCase();
        }
        
        public ShadowElementName setObfuscatedName(final IMapping<?> name) {
            return this.setObfuscatedName(name.getSimpleName());
        }
        
        public ShadowElementName setObfuscatedName(final String name) {
            return this.getName().setObfuscatedName(name);
        }
        
        public ObfuscationData<M> getObfuscationData(final IObfuscationDataProvider provider, final TypeHandle owner) {
            return provider.getObfEntry((IMapping<M>)this.getMapping(owner, this.getName().toString(), this.getDesc()));
        }
        
        public abstract M getMapping(final TypeHandle p0, final String p1, final String p2);
        
        public abstract void addMapping(final ObfuscationType p0, final IMapping<?> p1);
    }
    
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
}
