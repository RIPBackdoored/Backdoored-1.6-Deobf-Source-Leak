package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ World.class })
public class MixinWorld
{
    public MixinWorld() {
        super();
    }
    
    @Inject(method = { "getSunBrightnessFactor" }, at = { @At("RETURN") }, cancellable = true, remap = false)
    private void getBrightnessOfSun(final float partialTicks, final CallbackInfoReturnable<Float> cir) {
        final \u0000l event = new \u0000l((float)cir.getReturnValue());
        MinecraftForge.EVENT_BUS.post((Event)event);
        cir.setReturnValue(event.gr);
    }
    
    @Inject(method = { "getSunBrightnessBody" }, at = { @At("RETURN") }, cancellable = true, remap = false)
    private void getBrightnessBodyOfSun(final float partialTicks, final CallbackInfoReturnable<Float> cir) {
        final \u0000l event = new \u0000l((float)cir.getReturnValue());
        MinecraftForge.EVENT_BUS.post((Event)event);
        cir.setReturnValue(event.gr);
    }
}
