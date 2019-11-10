package org.spongepowered.tools.obfuscation;

import org.spongepowered.asm.util.*;

final class RemapperProxy implements ObfuscationUtil.IClassRemapper
{
    final /* synthetic */ ObfuscationEnvironment this$0;
    
    RemapperProxy(final ObfuscationEnvironment this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public String map(final String typeName) {
        if (this.this$0.mappingProvider == null) {
            return null;
        }
        return this.this$0.mappingProvider.getClassMapping(typeName);
    }
    
    @Override
    public String unmap(final String typeName) {
        if (this.this$0.mappingProvider == null) {
            return null;
        }
        return this.this$0.mappingProvider.getClassMapping(typeName);
    }
}
