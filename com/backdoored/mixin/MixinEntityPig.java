package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityPig.class })
public class MixinEntityPig
{
    public MixinEntityPig() {
        super();
    }
    
    @ModifyArgs(method = { "travel" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/EntityAnimal;travel(FFF)V"))
    private void travel(final Args args, final float strafe, final float vertical, final float forward) {
        final \u0000a event = new \u0000a();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.getResult() == Event.Result.ALLOW || event.getResult() == Event.Result.DEFAULT) {
            args.setAll(strafe, vertical, forward);
        }
        else {
            args.setAll(strafe, vertical, 0);
        }
    }
}
