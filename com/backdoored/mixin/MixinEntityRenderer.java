package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.math.*;
import \u0000f.\u0000b.\u0000o.\u0000g.\u0000a.*;
import net.minecraft.client.renderer.*;

@Mixin(value = { EntityRenderer.class }, priority = 999999999)
public class MixinEntityRenderer
{
    public MixinEntityRenderer() {
        super();
    }
    
    @Inject(method = { "setupFog" }, at = { @At("HEAD") }, cancellable = true)
    public void setupFog(final int startCoords, final float partialTicks, final CallbackInfo callbackInfo) {
    }
    
    @Redirect(method = { "setupFog" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ActiveRenderInfo;getBlockStateAtEntityViewpoint(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;F)Lnet/minecraft/block/state/IBlockState;"))
    public IBlockState getBlockStateAtEntityViewpoint(final World worldIn, final Entity entityIn, final float partialTicks) {
        return ActiveRenderInfo.func_186703_a(worldIn, entityIn, partialTicks);
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"))
    public RayTraceResult rayTraceBlocks(final WorldClient world, final Vec3d start, final Vec3d end) {
        if (\u0000f.bx()) {
            return null;
        }
        return world.func_147447_a(start, end, false, true, true);
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.translate(FFF)V", ordinal = 2))
    private void getViewDistance(final float x, final float y, final float z) {
        GlStateManager.func_179109_b(x, y, z - \u0000f.gc());
    }
}
