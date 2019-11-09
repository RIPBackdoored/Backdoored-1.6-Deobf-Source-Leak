package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockLiquid.class })
public class MixinBlockLiquid
{
    public MixinBlockLiquid() {
        super();
    }
    
    @Inject(method = { "canCollideCheck" }, at = { @At("HEAD") }, cancellable = true)
    public void canCollideWithLiquid(final IBlockState state, final boolean hitIfLiquid, final CallbackInfoReturnable<Boolean> cir) {
        final \u0000g event = new \u0000g((CallbackInfoReturnable)cir);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}
