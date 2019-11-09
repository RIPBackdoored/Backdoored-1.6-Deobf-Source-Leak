package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import \u0000f.\u0000b.\u0000a.*;

@Mixin({ Block.class })
public class MixinBlock
{
    public MixinBlock() {
        super();
    }
    
    @Inject(method = { "shouldSideBeRendered" }, at = { @At("HEAD") }, cancellable = true)
    public void shouldSideBeRendered(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos, final EnumFacing side, final CallbackInfoReturnable<Boolean> callback) {
        final \u0000ba event = new \u0000ba(blockState, blockAccess, pos, side);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.getResult() == Event.Result.DENY) {
            callback.setReturnValue(false);
        }
        else if (event.getResult() == Event.Result.ALLOW) {
            callback.setReturnValue(true);
        }
    }
    
    @Inject(method = { "getAmbientOcclusionLightValue" }, at = { @At("RETURN") }, cancellable = true)
    private void getAmbientOcclusionLightValue(final CallbackInfoReturnable<Float> ci) {
        final \u0000i event = new \u0000i((float)ci.getReturnValue());
        MinecraftForge.EVENT_BUS.post((Event)event);
        ci.setReturnValue(event.gg);
    }
}
