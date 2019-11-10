package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import java.util.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import javax.tools.*;
import javax.lang.model.type.*;

public abstract class MixinValidator implements IMixinValidator
{
    protected final ProcessingEnvironment processingEnv;
    protected final Messager messager;
    protected final IOptionProvider options;
    protected final ValidationPass pass;
    
    public MixinValidator(final IMixinAnnotationProcessor ap, final ValidationPass pass) {
        super();
        this.processingEnv = ap.getProcessingEnvironment();
        this.messager = ap;
        this.options = ap;
        this.pass = pass;
    }
    
    @Override
    public final boolean validate(final ValidationPass pass, final TypeElement mixin, final AnnotationHandle annotation, final Collection<TypeHandle> targets) {
        return pass != this.pass || this.validate(mixin, annotation, targets);
    }
    
    protected abstract boolean validate(final TypeElement p0, final AnnotationHandle p1, final Collection<TypeHandle> p2);
    
    protected final void note(final String note, final Element element) {
        this.messager.printMessage(Diagnostic.Kind.NOTE, note, element);
    }
    
    protected final void warning(final String warning, final Element element) {
        this.messager.printMessage(Diagnostic.Kind.WARNING, warning, element);
    }
    
    protected final void error(final String error, final Element element) {
        this.messager.printMessage(Diagnostic.Kind.ERROR, error, element);
    }
    
    protected final Collection<TypeMirror> getMixinsTargeting(final TypeMirror targetType) {
        return AnnotatedMixins.getMixinsForEnvironment(this.processingEnv).getMixinsTargeting(targetType);
    }
}
