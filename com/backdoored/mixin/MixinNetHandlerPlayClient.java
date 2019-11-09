package com.backdoored.mixin;

import net.minecraft.client.network.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import \u0000f.\u0000b.*;
import net.minecraft.entity.player.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.world.*;
import net.minecraft.scoreboard.*;
import org.spongepowered.asm.mixin.*;

@Mixin(value = { NetHandlerPlayClient.class }, priority = 999999999)
public class MixinNetHandlerPlayClient
{
    @Shadow
    private boolean field_147309_h;
    @Shadow
    private WorldClient field_147300_g;
    @Shadow
    private Minecraft field_147299_f;
    
    public MixinNetHandlerPlayClient() {
        super();
    }
    
    @Inject(method = { "handleChunkData" }, at = { @At("RETURN") })
    private void postHandleChunkData(final SPacketChunkData packet, final CallbackInfo ci) {
    }
    
    @Inject(method = { "handleCombatEvent" }, at = { @At("INVOKE") })
    private void onPlayerDeath(final SPacketCombatEvent packet, final CallbackInfo ci) {
        if (packet.field_179776_a == SPacketCombatEvent.Event.ENTITY_DIED) {
            System.out.println("A player died! " + packet.field_179775_c);
            final Entity e = \u0000p.cf.field_71441_e.func_73045_a(packet.field_179775_c);
            if (e instanceof EntityPlayer) {
                final \u0000z event = new \u0000z((EntityPlayer)e);
                MinecraftForge.EVENT_BUS.post((Event)event);
            }
        }
    }
    
    @Overwrite
    public void func_147280_a(final SPacketRespawn packetIn) {
        PacketThreadUtil.func_180031_a((Packet)packetIn, (INetHandler)this, (IThreadListener)this.field_147299_f);
        if (packetIn.func_149082_c() != this.field_147299_f.field_71439_g.field_71093_bK) {
            this.field_147309_h = false;
            this.field_147299_f.func_147108_a((GuiScreen)null);
            final Scoreboard scoreboard = this.field_147300_g.func_96441_U();
            (this.field_147300_g = new WorldClient((NetHandlerPlayClient)this, new WorldSettings(0L, packetIn.func_149083_e(), false, this.field_147299_f.field_71441_e.func_72912_H().func_76093_s(), packetIn.func_149080_f()), packetIn.func_149082_c(), packetIn.func_149081_d(), this.field_147299_f.field_71424_I)).func_96443_a(scoreboard);
            this.field_147299_f.func_71403_a(this.field_147300_g);
            this.field_147299_f.field_71439_g.field_71093_bK = packetIn.func_149082_c();
        }
        this.field_147299_f.func_71354_a(packetIn.func_149082_c());
        this.field_147299_f.field_71442_b.func_78746_a(packetIn.func_149083_e());
    }
}
