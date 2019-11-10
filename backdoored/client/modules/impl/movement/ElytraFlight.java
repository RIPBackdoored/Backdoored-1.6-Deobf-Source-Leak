package f.b.o.g.movement;

import f.b.o.g.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;

@c$b(name = "ElytraFlight", description = "Rockets aren't needed", category = f.b.o.c.c.MOVEMENT)
public class ElytraFlight extends c
{
    private f.b.f.c ec;
    private f.b.f.c en;
    private f.b.f.c el;
    private f.b.f.c ei;
    
    public ElytraFlight() {
        super();
        this.ec = new f.b.f.c("Mode", this, "booost", new String[] { "booost", "control", "flight" });
        this.en = new f.b.f.c("Boost-Speed", this, 0.05, 0.01, 0.2);
        this.el = new f.b.f.c("Flight-Speed", this, 0.05, 0.01, 0.2);
        this.ei = new f.b.f.c("Control-Speed", this, 0.9, 0.01, 4.0);
    }
    
    public void bh() {
        if (!ElytraFlight.mc.player.isElytraFlying() || !this.bu()) {
            return;
        }
        if (this.ec.ci().equals("booost")) {
            if (ElytraFlight.mc.player.capabilities.isFlying) {
                ElytraFlight.mc.player.capabilities.isFlying = false;
            }
            if (ElytraFlight.mc.player.isInWater()) {
                ElytraFlight.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            }
            final float v = (float)Math.toRadians(ElytraFlight.mc.player.rotationYaw);
            if (ElytraFlight.mc.gameSettings.keyBindForward.isKeyDown()) {
                final EntityPlayerSP player = ElytraFlight.mc.player;
                player.motionX -= MathHelper.sin(v) * this.en.ck();
                final EntityPlayerSP player2 = ElytraFlight.mc.player;
                player2.motionZ += MathHelper.cos(v) * this.en.ck();
            }
            else if (ElytraFlight.mc.gameSettings.keyBindBack.isKeyDown()) {
                final EntityPlayerSP player3 = ElytraFlight.mc.player;
                player3.motionX += MathHelper.sin(v) * this.en.ck();
                final EntityPlayerSP player4 = ElytraFlight.mc.player;
                player4.motionZ -= MathHelper.cos(v) * this.en.ck();
            }
        }
        if (this.ec.ci().equals("flight")) {
            ElytraFlight.mc.player.capabilities.isFlying = true;
            ElytraFlight.mc.player.jumpMovementFactor = (float)this.el.ck();
            if (ElytraFlight.mc.gameSettings.keyBindJump.isKeyDown()) {
                final EntityPlayerSP player5 = ElytraFlight.mc.player;
                player5.motionY += this.el.ck();
            }
            if (ElytraFlight.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final EntityPlayerSP player6 = ElytraFlight.mc.player;
                player6.motionY -= this.el.ck();
            }
        }
        if (this.ec.ci().equals("control")) {
            ElytraFlight.mc.player.motionY = 0.0;
            ElytraFlight.mc.player.motionX = 0.0;
            ElytraFlight.mc.player.motionZ = 0.0;
            final float v = (float)Math.toRadians(ElytraFlight.mc.player.rotationYaw);
            final float v2 = (float)Math.toRadians(ElytraFlight.mc.player.rotationPitch);
            if (ElytraFlight.mc.gameSettings.keyBindForward.isKeyDown()) {
                ElytraFlight.mc.player.motionX = -(MathHelper.sin(v) * MathHelper.cos(v2) * this.ei.ck());
                ElytraFlight.mc.player.motionZ = MathHelper.cos(v) * MathHelper.cos(v2) * this.ei.ck();
                ElytraFlight.mc.player.motionY = -(MathHelper.sin(v2) * this.ei.ck());
            }
            else if (ElytraFlight.mc.gameSettings.keyBindBack.isKeyDown()) {
                ElytraFlight.mc.player.motionX = MathHelper.sin(v) * MathHelper.cos(v2) * this.ei.ck();
                ElytraFlight.mc.player.motionZ = -(MathHelper.cos(v) * MathHelper.cos(v2) * this.ei.ck());
                ElytraFlight.mc.player.motionY = MathHelper.sin(v2) * this.ei.ck();
            }
            if (ElytraFlight.mc.gameSettings.keyBindLeft.isKeyDown()) {
                ElytraFlight.mc.player.motionZ = MathHelper.sin(v) * MathHelper.cos(v2) * this.ei.ck();
                ElytraFlight.mc.player.motionX = MathHelper.cos(v) * MathHelper.cos(v2) * this.ei.ck();
            }
            else if (ElytraFlight.mc.gameSettings.keyBindRight.isKeyDown()) {
                ElytraFlight.mc.player.motionZ = -(MathHelper.sin(v) * this.ei.ck());
                ElytraFlight.mc.player.motionX = -(MathHelper.cos(v) * this.ei.ck());
            }
            if (ElytraFlight.mc.gameSettings.keyBindJump.isKeyDown()) {
                ElytraFlight.mc.player.motionY = this.ei.ck();
            }
            else if (ElytraFlight.mc.gameSettings.keyBindSneak.isKeyDown()) {
                ElytraFlight.mc.player.motionY = -this.ei.ck();
            }
        }
    }
    
    public void bv() {
        if (!this.ec.ci().equals("flight")) {
            return;
        }
        ElytraFlight.mc.player.capabilities.setFlySpeed((float)this.el.ck());
        ElytraFlight.mc.addScheduledTask(this::ge);
    }
    
    public void bd() {
        if (this.ec.ci().equals("flight")) {
            ElytraFlight.mc.player.capabilities.isFlying = false;
            ElytraFlight.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }
    
    private /* synthetic */ void ge() {
        if (ElytraFlight.mc.player != null && ElytraFlight.mc.player.isElytraFlying() && this.bu() && this.ec.ci().equals("flight")) {
            ElytraFlight.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }
}
