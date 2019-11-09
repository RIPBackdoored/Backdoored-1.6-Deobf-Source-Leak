package com.backdoored.mixin;

import net.minecraft.entity.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityItem.class })
public abstract class MixinEntityItem
{
    @Shadow
    private int field_145804_b;
    
    public MixinEntityItem() {
        super();
    }
    
    @Inject(method = { "setPickupDelay" }, at = { @At("RETURN") }, cancellable = true)
    public void setPickupDelayWrap(final int ticks, final CallbackInfo ci) {
        final \u0000h event = new \u0000h(this.field_145804_b);
        MinecraftForge.EVENT_BUS.post((Event)event);
        this.field_145804_b = event.gp;
    }
    
    @Inject(method = { "setDefaultPickupDelay" }, at = { @At("RETURN") }, cancellable = true)
    public void setDefaultPickupDelayWrap(final CallbackInfo ci) {
        final \u0000h event = new \u0000h(this.field_145804_b);
        MinecraftForge.EVENT_BUS.post((Event)event);
        this.field_145804_b = event.gp;
    }
    
    @Inject(method = { "setNoPickupDelay" }, at = { @At("RETURN") }, cancellable = true)
    public void setNoPickupDelayWrap(final CallbackInfo ci) {
        final \u0000h event = new \u0000h(this.field_145804_b);
        MinecraftForge.EVENT_BUS.post((Event)event);
        this.field_145804_b = event.gp;
    }
}
