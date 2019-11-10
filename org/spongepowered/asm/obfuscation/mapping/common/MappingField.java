package org.spongepowered.asm.obfuscation.mapping.common;

import org.spongepowered.asm.obfuscation.mapping.*;
import com.google.common.base.*;

public class MappingField implements IMapping<MappingField>
{
    private final String owner;
    private final String name;
    private final String desc;
    
    public MappingField(final String owner, final String name) {
        this(owner, name, null);
    }
    
    public MappingField(final String owner, final String name, final String desc) {
        super();
        this.owner = owner;
        this.name = name;
        this.desc = desc;
    }
    
    @Override
    public Type getType() {
        return Type.FIELD;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public final String getSimpleName() {
        return this.name;
    }
    
    @Override
    public final String getOwner() {
        return this.owner;
    }
    
    @Override
    public final String getDesc() {
        return this.desc;
    }
    
    @Override
    public MappingField getSuper() {
        return null;
    }
    
    @Override
    public MappingField move(final String newOwner) {
        return new MappingField(newOwner, this.getName(), this.getDesc());
    }
    
    @Override
    public MappingField remap(final String newName) {
        return new MappingField(this.getOwner(), newName, this.getDesc());
    }
    
    @Override
    public MappingField transform(final String newDesc) {
        return new MappingField(this.getOwner(), this.getName(), newDesc);
    }
    
    @Override
    public MappingField copy() {
        return new MappingField(this.getOwner(), this.getName(), this.getDesc());
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.toString());
    }
    
    @Override
    public boolean equals(final Object obj) {
        return this == obj || (obj instanceof MappingField && Objects.equal(this.toString(), ((MappingField)obj).toString()));
    }
    
    @Override
    public String serialise() {
        return this.toString();
    }
    
    @Override
    public String toString() {
        return String.format("L%s;%s:%s", this.getOwner(), this.getName(), Strings.nullToEmpty(this.getDesc()));
    }
    
    @Override
    public /* bridge */ Object getSuper() {
        return this.getSuper();
    }
    
    @Override
    public /* bridge */ Object copy() {
        return this.copy();
    }
    
    @Override
    public /* bridge */ Object transform(final String newDesc) {
        return this.transform(newDesc);
    }
    
    @Override
    public /* bridge */ Object remap(final String newName) {
        return this.remap(newName);
    }
    
    @Override
    public /* bridge */ Object move(final String newOwner) {
        return this.move(newOwner);
    }
}
