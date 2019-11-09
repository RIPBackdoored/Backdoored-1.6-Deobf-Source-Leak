package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.layers.*;
import \u0000f.\u0000b.\u0000o.\u0000g.\u0000b.*;
import \u0000f.\u0000b.\u0000q.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { LayerArmorBase.class }, priority = 999999999)
public class MixinLayerArmorBase
{
    public MixinLayerArmorBase() {
        super();
    }
    
    @Redirect(method = { "renderEnchantedGlint" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.color(FFFF)V"))
    private static void renderEnchantedGlint(float red, float green, float blue, float alpha) {
        if (\u0000n.wb != null && \u0000n.wb.bu()) {
            final Color rainbow = \u0000b.nb();
            red = (float)rainbow.getRed();
            blue = (float)rainbow.getBlue();
            green = (float)rainbow.getGreen();
            alpha = (float)rainbow.getAlpha();
        }
        GlStateManager.func_179131_c(red, green, blue, alpha);
    }
}
