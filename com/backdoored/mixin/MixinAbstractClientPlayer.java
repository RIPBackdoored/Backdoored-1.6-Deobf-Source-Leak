package com.backdoored.mixin;

import net.minecraft.client.entity.*;
import net.minecraft.client.network.*;
import javax.annotation.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import \u0000f.\u0000b.\u0000a.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { AbstractClientPlayer.class }, priority = 999999999)
public abstract class MixinAbstractClientPlayer extends MixinEntityPlayer
{
    public MixinAbstractClientPlayer() {
        super();
    }
    
    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo func_175155_b();
    
    @Overwrite
    @Nullable
    public ResourceLocation func_110303_q() {
        final NetworkPlayerInfo networkplayerinfo = this.func_175155_b();
        final \u0000e event = new \u0000e(networkplayerinfo);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.gb != null) {
            return event.gb;
        }
        return (networkplayerinfo == null) ? null : networkplayerinfo.func_178861_h();
    }
    
    @Inject(method = { "hasSkin" }, at = { @At("RETURN") }, cancellable = true)
    public void hasSkin(final CallbackInfoReturnable<Boolean> cir) {
        final \u0000bx.\u0000c event = new \u0000bx.\u0000c(this.func_175155_b(), (boolean)cir.getReturnValue());
        MinecraftForge.EVENT_BUS.post((Event)event);
        cir.setReturnValue(event.rr);
    }
    
    @Inject(method = { "getLocationSkin()Lnet/minecraft/util/ResourceLocation;" }, at = { @At("RETURN") }, cancellable = true)
    public void getSkin(final CallbackInfoReturnable<ResourceLocation> cir) {
        final \u0000bx.\u0000o event = new \u0000bx.\u0000o(this.func_175155_b(), (ResourceLocation)cir.getReturnValue());
        MinecraftForge.EVENT_BUS.post((Event)event);
        cir.setReturnValue(event.rg);
    }
}
