package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.util.*;
import java.io.*;
import javax.annotation.*;
import net.minecraft.client.shader.*;
import net.minecraft.util.text.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ScreenShotHelper.class })
public class MixinScreenshotHelper
{
    public MixinScreenshotHelper() {
        super();
    }
    
    @Redirect(method = { "Lnet/minecraft/util/ScreenShotHelper;saveScreenshot(Ljava/io/File;IILnet/minecraft/client/shader/Framebuffer;)Lnet/minecraft/util/text/ITextComponent;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ScreenShotHelper;saveScreenshot(Ljava/io/File;Ljava/lang/String;IILnet/minecraft/client/shader/Framebuffer;)Lnet/minecraft/util/text/ITextComponent;"))
    private static ITextComponent saveScreenshot(final File gameDirectory, @Nullable final String screenshotName, final int width, final int height, final Framebuffer buffer) {
        final \u0000bh event = new \u0000bh(gameDirectory, screenshotName, width, height, buffer);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            return ScreenShotHelper.func_148259_a(gameDirectory, (String)null, width, height, buffer);
        }
        return event.cc;
    }
}
