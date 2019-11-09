package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.particle.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ WorldClient.class })
public class MixinWorldClient
{
    public MixinWorldClient() {
        super();
    }
    
    @Redirect(method = { "makeFireworks" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleManager;addEffect(Lnet/minecraft/client/particle/Particle;)V"))
    private void makeFireworkParticles(final ParticleManager particleManager, final Particle effect) {
        final \u0000p event = new \u0000p();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            particleManager.func_78873_a(effect);
        }
    }
    
    @ModifyVariable(method = { "showBarrierParticles(IIIILjava/util/Random;ZLnet/minecraft/util/math/BlockPos$MutableBlockPos;)V" }, at = @At("HEAD"))
    private boolean shouldShowBarriers(final boolean initial) {
        return true;
    }
}
