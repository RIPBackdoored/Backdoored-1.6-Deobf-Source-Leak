package com.backdoored.mixin;

import net.minecraft.client.renderer.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { ItemRenderer.class }, priority = 999999999)
public abstract class MixinItemRenderer
{
    public MixinItemRenderer() {
        super();
    }
    
    @Shadow
    public abstract void func_187457_a(final AbstractClientPlayer p0, final float p1, final float p2, final EnumHand p3, final float p4, final ItemStack p5, final float p6);
    
    @Inject(method = { "renderWaterOverlayTexture" }, at = { @At("HEAD") }, cancellable = true)
    private void renderWaterOverlayTexture(final float partialTicks, final CallbackInfo callbackInfo) {
    }
    
    @Redirect(method = { "renderItemInFirstPerson(F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V"))
    private void renderItemInFirstPerson(final ItemRenderer itemRenderer, final AbstractClientPlayer player, final float partialTicks, final float pitch, final EnumHand hand, final float swingProgress, final ItemStack stack, final float equipProgress) {
        final \u0000be event = new \u0000be(itemRenderer, player, partialTicks, pitch, hand, swingProgress, stack, equipProgress);
        MinecraftForge.EVENT_BUS.post((Event)event);
        event.rq.func_187457_a(event.rk, event.rp, event.rm, event.rs, event.re, event.rx, event.rz);
    }
}
