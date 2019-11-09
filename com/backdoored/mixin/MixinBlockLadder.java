package com.backdoored.mixin;

import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.math.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockLadder.class })
public class MixinBlockLadder
{
    @Shadow
    @Final
    public static PropertyDirection field_176382_a;
    
    public MixinBlockLadder() {
        super();
    }
    
    @Inject(method = { "getBoundingBox" }, at = { @At("HEAD") }, cancellable = true)
    public void getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos, final CallbackInfoReturnable<AxisAlignedBB> cir) {
        final \u0000j event = new \u0000j(state, source, pos, MixinBlockLadder.field_176382_a, (CallbackInfoReturnable)cir);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}
