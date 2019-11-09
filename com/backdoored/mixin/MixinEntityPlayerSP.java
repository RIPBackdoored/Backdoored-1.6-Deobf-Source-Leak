package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import \u0000f.\u0000b.\u0000o.\u0000g.\u0000y.*;
import \u0000f.\u0000b.\u0000o.\u0000g.*;

@Mixin({ EntityPlayerSP.class })
public class MixinEntityPlayerSP extends MixinEntityLivingBase
{
    private Minecraft mc;
    
    public MixinEntityPlayerSP() {
        super();
        this.mc = Minecraft.func_71410_x();
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") })
    private void preMotion(final CallbackInfo callbackInfo) {
        final \u0000bg.\u0000c event = new \u0000bg.\u0000c(this.mc.field_71439_g.field_70177_z, this.mc.field_71439_g.field_70125_A, this.mc.field_71439_g.field_70122_E);
        MinecraftForge.EVENT_BUS.post((Event)event);
        this.mc.field_71439_g.field_70177_z = event.gj;
        this.mc.field_71439_g.field_70125_A = event.ra;
        this.mc.field_71439_g.field_70122_E = event.rb;
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("RETURN") })
    private void postMotion(final CallbackInfo callbackInfo) {
        final \u0000bg.\u0000o event = new \u0000bg.\u0000o(this.mc.field_71439_g.field_70177_z, this.mc.field_71439_g.field_70125_A, this.mc.field_71439_g.field_70122_E);
        MinecraftForge.EVENT_BUS.post((Event)event);
        this.mc.field_71439_g.field_70177_z = event.gj;
        this.mc.field_71439_g.field_70125_A = event.ra;
        this.mc.field_71439_g.field_70122_E = event.rb;
    }
    
    @Override
    public void func_70664_aZ() {
        try {
            final double oldMotionX = ((EntityPlayerSP)this).field_70159_w;
            final double oldMotionZ = ((EntityPlayerSP)this).field_70179_y;
            super.func_70664_aZ();
            ((\u0000l)\u0000u.a((Class)\u0000l.class)).a(oldMotionX, oldMotionZ, (EntityPlayerSP)this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
