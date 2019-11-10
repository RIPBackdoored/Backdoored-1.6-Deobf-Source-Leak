package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.struct.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.asm.mixin.injection.*;
import javax.lang.model.element.*;
import java.lang.annotation.*;
import java.util.*;
import javax.tools.*;

static class AnnotatedElementInjector extends AnnotatedElement<ExecutableElement>
{
    private final InjectorRemap state;
    
    public AnnotatedElementInjector(final ExecutableElement element, final AnnotationHandle annotation, final InjectorRemap shouldRemap) {
        super(element, annotation);
        this.state = shouldRemap;
    }
    
    public boolean shouldRemap() {
        return this.state.shouldRemap();
    }
    
    public boolean hasCoerceArgument() {
        if (!this.annotation.toString().equals("@Inject")) {
            return false;
        }
        final Iterator<? extends VariableElement> iterator = ((ExecutableElement)this.element).getParameters().iterator();
        if (iterator.hasNext()) {
            final VariableElement param = (VariableElement)iterator.next();
            return AnnotationHandle.of(param, Coerce.class).exists();
        }
        return false;
    }
    
    public void addMessage(final Diagnostic.Kind kind, final CharSequence msg, final Element element, final AnnotationHandle annotation) {
        this.state.addMessage(kind, msg, element, annotation);
    }
    
    @Override
    public String toString() {
        return this.getAnnotation().toString();
    }
}
