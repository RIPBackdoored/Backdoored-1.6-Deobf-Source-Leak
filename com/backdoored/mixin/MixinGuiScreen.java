package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;

@Mixin({ GuiScreen.class })
public class MixinGuiScreen
{
    public MixinGuiScreen() {
        super();
    }
}
