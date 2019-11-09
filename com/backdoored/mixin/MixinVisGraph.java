package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ VisGraph.class })
public class MixinVisGraph
{
    public MixinVisGraph() {
        super();
    }
    
    @Inject(method = { "setOpaqueCube" }, at = { @At("HEAD") }, cancellable = true)
    public void setOpaqueCube(final BlockPos pos, final CallbackInfo callback) {
        final \u0000k event = new \u0000k(pos);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.getResult() == Event.Result.DENY || event.isCanceled()) {
            callback.cancel();
        }
    }
}
