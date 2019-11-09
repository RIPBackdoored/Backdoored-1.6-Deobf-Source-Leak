package com.backdoored.mixin;

import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import \u0000f.\u0000b.\u0000a.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Entity.class })
public abstract class MixinEntity
{
    public MixinEntity() {
        super();
    }
    
    @Shadow
    public abstract int func_82145_z();
    
    @Inject(method = { "turn" }, at = { @At("HEAD") }, cancellable = true)
    private void turn(float yaw, float pitch, final CallbackInfo ci) {
        final \u0000bo event = new \u0000bo((Entity)this, yaw, pitch);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            ci.cancel();
        }
        yaw = event.rd;
        pitch = event.ry;
    }
    
    @Redirect(method = { "onEntityUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getMaxInPortalTime()I"))
    private int getModifiedMaxInPortalTime(final Entity entity) {
        final \u0000o event = new \u0000o(entity, this.func_82145_z());
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event.gk;
    }
}
