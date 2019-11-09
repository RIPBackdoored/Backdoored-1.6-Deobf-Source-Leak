package com.backdoored.mixin;

import net.minecraft.entity.player.*;
import com.mojang.authlib.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Mixin(value = { EntityPlayer.class }, priority = 9900)
public abstract class MixinEntityPlayer
{
    public MixinEntityPlayer() {
        super();
    }
    
    @Shadow
    public abstract GameProfile func_146103_bH();
    
    @ModifyConstant(method = { "attackTargetEntityWithCurrentItem" }, constant = { @Constant(doubleValue = 0.6) })
    private double decelerate(final double original) {
        return 1.0;
    }
    
    @Redirect(method = { "attackTargetEntityWithCurrentItem" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;setSprinting(Z)V"))
    private void dontSprintPlsThx(final EntityPlayer player, final boolean sprinting) {
    }
    
    @ModifyConstant(method = { "getPortalCooldown" }, constant = { @Constant(intValue = 10) })
    private int getModifiedPortalCooldown(final int original) {
        final \u0000n event = new \u0000n(original, (EntityPlayer)this);
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event.gm;
    }
}
