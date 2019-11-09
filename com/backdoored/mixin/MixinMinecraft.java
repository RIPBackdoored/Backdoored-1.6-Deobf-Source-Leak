package com.backdoored.mixin;

import net.minecraft.client.*;
import net.minecraft.profiler.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.renderer.texture.*;

@Mixin({ Minecraft.class })
public class MixinMinecraft
{
    @Shadow
    @Final
    public Profiler field_71424_I;
    
    public MixinMinecraft() {
        super();
    }
    
    @Redirect(method = { "runTick" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 0))
    private void endStartGUISection(final Profiler profiler, final String name) {
        profiler.func_76318_c("gui");
    }
    
    @Redirect(method = { "runTick" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;tick()V", ordinal = 0))
    private void tickTextureManagerWithCorrectProfiler(final TextureManager textureManager) {
        this.field_71424_I.func_76318_c("textures");
        textureManager.func_110550_d();
        this.field_71424_I.func_76318_c("gui");
    }
}
