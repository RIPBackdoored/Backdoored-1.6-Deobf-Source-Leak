package org.spongepowered.tools.obfuscation.mirror;

import org.spongepowered.asm.obfuscation.mapping.common.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.mapping.*;
import com.google.common.base.*;
import org.spongepowered.asm.obfuscation.mapping.*;

public class MethodHandle extends MemberHandle<MappingMethod>
{
    private final ExecutableElement element;
    private final TypeHandle ownerHandle;
    
    public MethodHandle(final TypeHandle owner, final ExecutableElement element) {
        this(owner, element, TypeUtils.getName(element), TypeUtils.getDescriptor(element));
    }
    
    public MethodHandle(final TypeHandle owner, final String name, final String desc) {
        this(owner, null, name, desc);
    }
    
    private MethodHandle(final TypeHandle owner, final ExecutableElement element, final String name, final String desc) {
        super((owner != null) ? owner.getName() : null, name, desc);
        this.element = element;
        this.ownerHandle = owner;
    }
    
    public boolean isImaginary() {
        return this.element == null;
    }
    
    public ExecutableElement getElement() {
        return this.element;
    }
    
    @Override
    public Visibility getVisibility() {
        return TypeUtils.getVisibility(this.element);
    }
    
    @Override
    public MappingMethod asMapping(final boolean includeOwner) {
        if (!includeOwner) {
            return new MappingMethod(null, this.getName(), this.getDesc());
        }
        if (this.ownerHandle != null) {
            return new ResolvableMappingMethod(this.ownerHandle, this.getName(), this.getDesc());
        }
        return new MappingMethod(this.getOwner(), this.getName(), this.getDesc());
    }
    
    @Override
    public String toString() {
        final String owner = (this.getOwner() != null) ? ("L" + this.getOwner() + ";") : "";
        final String name = Strings.nullToEmpty(this.getName());
        final String desc = Strings.nullToEmpty(this.getDesc());
        return String.format("%s%s%s", owner, name, desc);
    }
    
    @Override
    public /* bridge */ IMapping asMapping(final boolean includeOwner) {
        return this.asMapping(includeOwner);
    }
}
