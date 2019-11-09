package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.chunk.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ChunkRenderContainer.class })
public class MixinChunkRenderContainer
{
    public MixinChunkRenderContainer() {
        super();
    }
    
    @Inject(method = { "preRenderChunk" }, at = { @At("HEAD") })
    public void preRenderChunk(final RenderChunk renderChunkIn, final CallbackInfo ci) {
        final \u0000x.\u0000o event = new \u0000x.\u0000o(renderChunkIn);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}
