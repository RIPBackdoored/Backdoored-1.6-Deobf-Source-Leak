package f.b.o.g.y;

import f.b.o.g.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;

@c$b(name = "BoatFly", description = "Experimental boatfly", category = f.b.o.c.c.MOVEMENT)
public class b extends c
{
    public b() {
        super();
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        if (b.mc.player.isRiding()) {
            final boolean v = b.mc.gameSettings.keyBindForward.isKeyDown();
            final boolean v2 = b.mc.gameSettings.keyBindLeft.isKeyDown();
            final boolean v3 = b.mc.gameSettings.keyBindRight.isKeyDown();
            final boolean v4 = b.mc.gameSettings.keyBindBack.isKeyDown();
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
                final float v6 = b.mc.player.rotationYaw + v5;
                b.mc.player.getRidingEntity().motionX = this.b(v6) * 0.20000000298023224;
                b.mc.player.getRidingEntity().motionZ = this.g(v6) * 0.20000000298023224;
            }
            b.mc.player.motionY = 0.0;
            b.mc.player.getRidingEntity().motionY = 0.0;
            b.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(b.mc.player.getRidingEntity().posX + b.mc.player.getRidingEntity().motionX, b.mc.player.getRidingEntity().posY, b.mc.player.getRidingEntity().posZ + b.mc.player.getRidingEntity().motionZ, b.mc.player.rotationYaw, b.mc.player.rotationPitch, false));
            b.mc.player.getRidingEntity().motionY = 0.0;
            if (b.mc.gameSettings.keyBindJump.isKeyDown()) {
                b.mc.player.getRidingEntity().motionY = 0.3;
            }
            if (b.mc.gameSettings.keyBindSprint.isKeyDown()) {
                b.mc.player.getRidingEntity().motionY = -0.3;
            }
            b.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove());
            b.mc.player.connection.sendPacket((Packet)new CPacketSteerBoat(true, true));
            b.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(b.mc.player.getRidingEntity().posX + b.mc.player.getRidingEntity().motionX, b.mc.player.getRidingEntity().posY - 42069.0, b.mc.player.getRidingEntity().posZ + b.mc.player.getRidingEntity().motionZ, b.mc.player.rotationYaw, b.mc.player.rotationPitch, true));
            b.mc.player.getRidingEntity().posY -= 42069.0;
            b.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove());
            b.mc.player.connection.sendPacket((Packet)new CPacketSteerBoat(true, true));
        }
    }
    
    private double[] g(final int v) {
        return new double[] { b.mc.player.rotationYaw, v };
    }
    
    public double b(final float v) {
        return MathHelper.sin(-v * 0.017453292f);
    }
    
    public double g(final float v) {
        return MathHelper.cos(v * 0.017453292f);
    }
}
