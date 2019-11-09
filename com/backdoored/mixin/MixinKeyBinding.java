package com.backdoored.mixin;

import net.minecraft.client.settings.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ KeyBinding.class })
public class MixinKeyBinding
{
    @Shadow
    private boolean field_74513_e;
    
    public MixinKeyBinding() {
        super();
    }
    
    @Inject(method = { "isKeyDown" }, at = { @At("RETURN") }, cancellable = true)
    private void isKeyDown(final CallbackInfoReturnable<Boolean> ci) {
        final \u0000m event = new \u0000m((boolean)ci.getReturnValue(), this.field_74513_e);
        MinecraftForge.EVENT_BUS.post((Event)event);
        ci.setReturnValue(event.gw);
    }
}
