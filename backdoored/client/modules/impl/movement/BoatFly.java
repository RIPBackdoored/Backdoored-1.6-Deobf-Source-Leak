package f.b.o.g.movement;

import f.b.o.g.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;

@c$b(name = "BoatFly", description = "Experimental boatfly", category = f.b.o.c.c.MOVEMENT)
public class BoatFly extends c
{
    public BoatFly() {
        super();
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        if (BoatFly.mc.player.isRiding()) {
            final boolean v = BoatFly.mc.gameSettings.keyBindForward.isKeyDown();
            final boolean v2 = BoatFly.mc.gameSettings.keyBindLeft.isKeyDown();
            final boolean v3 = BoatFly.mc.gameSettings.keyBindRight.isKeyDown();
            final boolean v4 = BoatFly.mc.gameSettings.keyBindBack.isKeyDown();
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
                final float v6 = BoatFly.mc.player.rotationYaw + v5;
                BoatFly.mc.player.getRidingEntity().motionX = this.b(v6) * 0.20000000298023224;
                BoatFly.mc.player.getRidingEntity().motionZ = this.g(v6) * 0.20000000298023224;
            }
            BoatFly.mc.player.motionY = 0.0;
            BoatFly.mc.player.getRidingEntity().motionY = 0.0;
            BoatFly.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(BoatFly.mc.player.getRidingEntity().posX + BoatFly.mc.player.getRidingEntity().motionX, BoatFly.mc.player.getRidingEntity().posY, BoatFly.mc.player.getRidingEntity().posZ + BoatFly.mc.player.getRidingEntity().motionZ, BoatFly.mc.player.rotationYaw, BoatFly.mc.player.rotationPitch, false));
            BoatFly.mc.player.getRidingEntity().motionY = 0.0;
            if (BoatFly.mc.gameSettings.keyBindJump.isKeyDown()) {
                BoatFly.mc.player.getRidingEntity().motionY = 0.3;
            }
            if (BoatFly.mc.gameSettings.keyBindSprint.isKeyDown()) {
                BoatFly.mc.player.getRidingEntity().motionY = -0.3;
            }
            BoatFly.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove());
            BoatFly.mc.player.connection.sendPacket((Packet)new CPacketSteerBoat(true, true));
            BoatFly.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(BoatFly.mc.player.getRidingEntity().posX + BoatFly.mc.player.getRidingEntity().motionX, BoatFly.mc.player.getRidingEntity().posY - 42069.0, BoatFly.mc.player.getRidingEntity().posZ + BoatFly.mc.player.getRidingEntity().motionZ, BoatFly.mc.player.rotationYaw, BoatFly.mc.player.rotationPitch, true));
            BoatFly.mc.player.getRidingEntity().posY -= 42069.0;
            BoatFly.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove());
            BoatFly.mc.player.connection.sendPacket((Packet)new CPacketSteerBoat(true, true));
        }
    }
    
    private double[] g(final int v) {
        return new double[] { BoatFly.mc.player.rotationYaw, v };
    }
    
    public double b(final float v) {
        return MathHelper.sin(-v * 0.017453292f);
    }
    
    public double g(final float v) {
        return MathHelper.cos(v * 0.017453292f);
    }
}
