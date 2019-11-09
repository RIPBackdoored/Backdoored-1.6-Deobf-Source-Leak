package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.*;

@Mixin(value = { GuiPlayerTabOverlay.class }, priority = 999999999)
public class MixinGuiPlayerTabOverlay
{
    public MixinGuiPlayerTabOverlay() {
        super();
    }
    
    @Redirect(method = { "renderPlayerlist" }, at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 0))
    public int noMin(final int listSize, final int maxTabSize) {
        return listSize;
    }
    
    @ModifyConstant(method = { "renderPlayerlist" }, constant = { @Constant(intValue = 20, ordinal = 0) })
    public int getNumRows(final int rows) {
        return 30;
    }
    
    @Redirect(method = { "renderPlayerlist" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isIntegratedServerRunning()Z"))
    public boolean renderPlayerIcons(final Minecraft mc) {
        return true;
    }
}
