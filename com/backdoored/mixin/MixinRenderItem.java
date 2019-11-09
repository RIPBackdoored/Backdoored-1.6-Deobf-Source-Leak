package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import \u0000f.\u0000b.\u0000o.\u0000g.\u0000b.*;
import \u0000f.\u0000b.\u0000q.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { RenderItem.class }, priority = 999999999)
public class MixinRenderItem
{
    public MixinRenderItem() {
        super();
    }
    
    @ModifyArg(method = { "renderEffect" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/RenderItem.renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;I)V"), index = 1)
    private int renderModel(final int oldColour) {
        if (\u0000n.wb != null && \u0000n.wb.bu()) {
            return \u0000b.nb().getRGB();
        }
        return oldColour;
    }
}
