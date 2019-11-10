package org.spongepowered.tools.obfuscation.mapping.mcp;

import com.google.common.io.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import org.spongepowered.asm.obfuscation.mapping.mcp.*;
import org.spongepowered.asm.mixin.throwables.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import java.io.*;

class MappingProviderSrg$1 implements LineProcessor<String> {
    final /* synthetic */ BiMap val$packageMap;
    final /* synthetic */ BiMap val$classMap;
    final /* synthetic */ BiMap val$fieldMap;
    final /* synthetic */ BiMap val$methodMap;
    final /* synthetic */ File val$input;
    final /* synthetic */ MappingProviderSrg this$0;
    
    MappingProviderSrg$1(final MappingProviderSrg this$0, final BiMap val$packageMap, final BiMap val$classMap, final BiMap val$fieldMap, final BiMap val$methodMap, final File val$input) {
        this.this$0 = this$0;
        this.val$packageMap = val$packageMap;
        this.val$classMap = val$classMap;
        this.val$fieldMap = val$fieldMap;
        this.val$methodMap = val$methodMap;
        this.val$input = val$input;
        super();
    }
    
    public String getResult() {
        return null;
    }
    
    public boolean processLine(final String line) throws IOException {
        if (Strings.isNullOrEmpty(line) || line.startsWith("#")) {
            return true;
        }
        final String type = line.substring(0, 2);
        final String[] args = line.substring(4).split(" ");
        if (type.equals("PK")) {
            this.val$packageMap.forcePut((Object)args[0], (Object)args[1]);
        }
        else if (type.equals("CL")) {
            this.val$classMap.forcePut((Object)args[0], (Object)args[1]);
        }
        else if (type.equals("FD")) {
            this.val$fieldMap.forcePut((Object)new MappingFieldSrg(args[0]).copy(), (Object)new MappingFieldSrg(args[1]).copy());
        }
        else {
            if (!type.equals("MD")) {
                throw new MixinException("Invalid SRG file: " + this.val$input);
            }
            this.val$methodMap.forcePut((Object)new MappingMethod(args[0], args[1]), (Object)new MappingMethod(args[2], args[3]));
        }
        return true;
    }
    
    public /* bridge */ Object getResult() {
        return this.getResult();
    }
}