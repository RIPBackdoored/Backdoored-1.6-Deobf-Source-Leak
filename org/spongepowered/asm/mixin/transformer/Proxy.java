package org.spongepowered.asm.mixin.transformer;

import net.minecraft.launchwrapper.*;
import org.spongepowered.asm.service.*;
import org.apache.logging.log4j.*;
import java.util.*;

public final class Proxy implements IClassTransformer, ILegacyClassTransformer
{
    private static List<Proxy> proxies;
    private static MixinTransformer transformer;
    private boolean isActive;
    
    public Proxy() {
        super();
        this.isActive = true;
        for (final Proxy hook : Proxy.proxies) {
            hook.isActive = false;
        }
        Proxy.proxies.add(this);
        LogManager.getLogger("mixin").debug("Adding new mixin transformer proxy #{}", new Object[] { Proxy.proxies.size() });
    }
    
    public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
        if (this.isActive) {
            return Proxy.transformer.transformClassBytes(name, transformedName, basicClass);
        }
        return basicClass;
    }
    
    public String getName() {
        return this.getClass().getName();
    }
    
    public boolean isDelegationExcluded() {
        return true;
    }
    
    public byte[] transformClassBytes(final String name, final String transformedName, final byte[] basicClass) {
        if (this.isActive) {
            return Proxy.transformer.transformClassBytes(name, transformedName, basicClass);
        }
        return basicClass;
    }
    
    static {
        Proxy.proxies = new ArrayList<Proxy>();
        Proxy.transformer = new MixinTransformer();
    }
}
