package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ WorldProviderHell.class })
public class MixinWorldProviderHell
{
    public MixinWorldProviderHell() {
        super();
    }
    
    @ModifyConstant(method = { "generateLightBrightnessTable" }, constant = { @Constant(floatValue = 0.9f) })
    private float getBrightness(final float initial) {
        final \u0000l event = new \u0000l(initial);
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event.gr;
    }
}
