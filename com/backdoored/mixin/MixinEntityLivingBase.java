package com.backdoored.mixin;

import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityLivingBase.class })
public class MixinEntityLivingBase
{
    public MixinEntityLivingBase() {
        super();
    }
    
    @Shadow
    public void func_70664_aZ() {
    }
    
    @ModifyConstant(method = { "getWaterSlowDown" }, constant = { @Constant(floatValue = 0.8f) })
    public float getWaterSlowDownWrapper(final float initial) {
        final \u0000w event = new \u0000w(initial);
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event.gx;
    }
    
    @ModifyConstant(method = { "handleJumpWater" }, constant = { @Constant(doubleValue = 0.03999999910593033) })
    public double handleJumpWaterWrap(final double initial) {
        return 5.0;
    }
}
