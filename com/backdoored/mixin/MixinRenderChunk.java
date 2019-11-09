package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.chunk.*;
import \u0000f.\u0000b.\u0000o.\u0000g.\u0000a.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderChunk.class })
public class MixinRenderChunk
{
    private \u0000d.\u0000e renderChunk;
    
    public MixinRenderChunk() {
        super();
        this.renderChunk = new \u0000d.\u0000e((RenderChunk)this);
    }
    
    @Inject(method = { "setPosition" }, at = { @At("HEAD") })
    public void setPosition(final int x, final int y, final int z, final CallbackInfo ci) {
        final \u0000x.\u0000c event = new \u0000x.\u0000c(this.renderChunk.fl, x, y, z);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}
