package f.b.o.g.y;

import f.b.o.g.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;

@c$b(name = "ElytraFlight", description = "Rockets aren't needed", category = f.b.o.c.c.MOVEMENT)
public class m extends c
{
    private f.b.f.c ec;
    private f.b.f.c en;
    private f.b.f.c el;
    private f.b.f.c ei;
    
    public m() {
        super();
        this.ec = new f.b.f.c("Mode", this, "booost", new String[] { "booost", "control", "flight" });
        this.en = new f.b.f.c("Boost-Speed", this, 0.05, 0.01, 0.2);
        this.el = new f.b.f.c("Flight-Speed", this, 0.05, 0.01, 0.2);
        this.ei = new f.b.f.c("Control-Speed", this, 0.9, 0.01, 4.0);
    }
    
    public void bh() {
        if (!m.mc.player.isElytraFlying() || !this.bu()) {
            return;
        }
        if (this.ec.ci().equals("booost")) {
            if (m.mc.player.capabilities.isFlying) {
                m.mc.player.capabilities.isFlying = false;
            }
            if (m.mc.player.isInWater()) {
                m.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)m.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            }
            final float v = (float)Math.toRadians(m.mc.player.rotationYaw);
            if (m.mc.gameSettings.keyBindForward.isKeyDown()) {
                final EntityPlayerSP player = m.mc.player;
                player.motionX -= MathHelper.sin(v) * this.en.ck();
                final EntityPlayerSP player2 = m.mc.player;
                player2.motionZ += MathHelper.cos(v) * this.en.ck();
            }
            else if (m.mc.gameSettings.keyBindBack.isKeyDown()) {
                final EntityPlayerSP player3 = m.mc.player;
                player3.motionX += MathHelper.sin(v) * this.en.ck();
                final EntityPlayerSP player4 = m.mc.player;
                player4.motionZ -= MathHelper.cos(v) * this.en.ck();
            }
        }
        if (this.ec.ci().equals("flight")) {
            m.mc.player.capabilities.isFlying = true;
            m.mc.player.jumpMovementFactor = (float)this.el.ck();
            if (m.mc.gameSettings.keyBindJump.isKeyDown()) {
                final EntityPlayerSP player5 = m.mc.player;
                player5.motionY += this.el.ck();
            }
            if (m.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final EntityPlayerSP player6 = m.mc.player;
                player6.motionY -= this.el.ck();
            }
        }
        if (this.ec.ci().equals("control")) {
            m.mc.player.motionY = 0.0;
            m.mc.player.motionX = 0.0;
            m.mc.player.motionZ = 0.0;
            final float v = (float)Math.toRadians(m.mc.player.rotationYaw);
            final float v2 = (float)Math.toRadians(m.mc.player.rotationPitch);
            if (m.mc.gameSettings.keyBindForward.isKeyDown()) {
                m.mc.player.motionX = -(MathHelper.sin(v) * MathHelper.cos(v2) * this.ei.ck());
                m.mc.player.motionZ = MathHelper.cos(v) * MathHelper.cos(v2) * this.ei.ck();
                m.mc.player.motionY = -(MathHelper.sin(v2) * this.ei.ck());
            }
            else if (m.mc.gameSettings.keyBindBack.isKeyDown()) {
                m.mc.player.motionX = MathHelper.sin(v) * MathHelper.cos(v2) * this.ei.ck();
                m.mc.player.motionZ = -(MathHelper.cos(v) * MathHelper.cos(v2) * this.ei.ck());
                m.mc.player.motionY = MathHelper.sin(v2) * this.ei.ck();
            }
            if (m.mc.gameSettings.keyBindLeft.isKeyDown()) {
                m.mc.player.motionZ = MathHelper.sin(v) * MathHelper.cos(v2) * this.ei.ck();
                m.mc.player.motionX = MathHelper.cos(v) * MathHelper.cos(v2) * this.ei.ck();
            }
            else if (m.mc.gameSettings.keyBindRight.isKeyDown()) {
                m.mc.player.motionZ = -(MathHelper.sin(v) * this.ei.ck());
                m.mc.player.motionX = -(MathHelper.cos(v) * this.ei.ck());
            }
            if (m.mc.gameSettings.keyBindJump.isKeyDown()) {
                m.mc.player.motionY = this.ei.ck();
            }
            else if (m.mc.gameSettings.keyBindSneak.isKeyDown()) {
                m.mc.player.motionY = -this.ei.ck();
            }
        }
    }
    
    public void bv() {
        if (!this.ec.ci().equals("flight")) {
            return;
        }
        m.mc.player.capabilities.setFlySpeed((float)this.el.ck());
        m.mc.addScheduledTask(this::ge);
    }
    
    public void bd() {
        if (this.ec.ci().equals("flight")) {
            m.mc.player.capabilities.isFlying = false;
            m.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)m.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }
    
    private /* synthetic */ void ge() {
        if (m.mc.player != null && m.mc.player.isElytraFlying() && this.bu() && this.ec.ci().equals("flight")) {
            m.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)m.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }
}
