package com.backdoored.mixin;

import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;

@Mixin(value = { RenderGlobal.class }, priority = 999999999)
public class MixinRenderGlobal
{
    @Final
    @Shadow
    private Minecraft field_72777_q;
    
    public MixinRenderGlobal() {
        super();
    }
}
