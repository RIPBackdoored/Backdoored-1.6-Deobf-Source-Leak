package f.b.o.g.movement;

import f.b.o.g.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;

@c$b(name = "Packet Fly", description = "Experimental", category = f.b.o.c.c.MOVEMENT)
public class PacketFly extends c
{
    public PacketFly() {
        super();
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        final boolean v = PacketFly.mc.gameSettings.keyBindForward.isKeyDown();
        final boolean v2 = PacketFly.mc.gameSettings.keyBindLeft.isKeyDown();
        final boolean v3 = PacketFly.mc.gameSettings.keyBindRight.isKeyDown();
        final boolean v4 = PacketFly.mc.gameSettings.keyBindBack.isKeyDown();
        int v5;
        if (v2 && v3) {
            v5 = (v ? 0 : (v4 ? 180 : -1));
        }
        else if (v && v4) {
            v5 = (v2 ? -90 : (v3 ? 90 : -1));
        }
        else {
            v5 = (v2 ? -90 : (v3 ? 90 : 0));
            if (v) {
                v5 /= 2;
            }
            else if (v4) {
                v5 = 180 - v5 / 2;
            }
        }
        if (v5 != -1 && (v || v2 || v3 || v4)) {
            final float v6 = PacketFly.mc.player.rotationYaw + v5;
            PacketFly.mc.player.motionX = this.b(v6) * 0.20000000298023224;
            PacketFly.mc.player.motionZ = this.g(v6) * 0.20000000298023224;
        }
        PacketFly.mc.player.motionY = 0.0;
        PacketFly.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PacketFly.mc.player.posX + PacketFly.mc.player.motionX, PacketFly.mc.player.posY + (PacketFly.mc.gameSettings.keyBindJump.isKeyDown() ? 0.0622 : 0.0) - (PacketFly.mc.gameSettings.keyBindSneak.isKeyDown() ? 0.0622 : 0.0), PacketFly.mc.player.posZ + PacketFly.mc.player.motionZ, PacketFly.mc.player.rotationYaw, PacketFly.mc.player.rotationPitch, false));
        PacketFly.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PacketFly.mc.player.posX + PacketFly.mc.player.motionX, PacketFly.mc.player.posY - 42069.0, PacketFly.mc.player.posZ + PacketFly.mc.player.motionZ, PacketFly.mc.player.rotationYaw, PacketFly.mc.player.rotationPitch, true));
    }
    
    private double[] g(final int v) {
        return new double[] { PacketFly.mc.player.rotationYaw, v };
    }
    
    public double b(final float v) {
        return MathHelper.sin(-v * 0.017453292f);
    }
    
    public double g(final float v) {
        return MathHelper.cos(v * 0.017453292f);
    }
}
