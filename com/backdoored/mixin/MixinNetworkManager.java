package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.channel.*;
import \u0000f.\u0000b.\u0000a.*;

@Mixin(value = { NetworkManager.class }, priority = 999999999)
public class MixinNetworkManager
{
    public MixinNetworkManager() {
        super();
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacket(final Packet<?> packet, final CallbackInfo callbackInfo) {
        final \u0000q event = new \u0000q((Packet)packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled() && callbackInfo.isCancellable()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void onChannelRead(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo callbackInfo) {
        final \u0000v event = new \u0000v((Packet)packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled() && callbackInfo.isCancellable()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "exceptionCaught" }, at = { @At("HEAD") }, cancellable = true)
    private void exceptionCaught(final ChannelHandlerContext p_exceptionCaught_1_, final Throwable p_exceptionCaught_2_, final CallbackInfo info) {
    }
}
