package f.b.q;

import f.b.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class n
{
    public n() {
        super();
    }
    
    public static void c(final BlockPos v) {
        a(EnumHand.MAIN_HAND, v);
    }
    
    public static void a(final EnumHand v, final BlockPos v) {
        final Vec3d v2 = new Vec3d(p.mc.player.posX, p.mc.player.posY + p.mc.player.getEyeHeight(), p.mc.player.posZ);
        for (final EnumFacing v3 : EnumFacing.values()) {
            final BlockPos v4 = v.offset(v3);
            final EnumFacing v5 = v3.getOpposite();
            if (p.mc.world.getBlockState(v4).getBlock().canCollideCheck(p.mc.world.getBlockState(v4), false)) {
                final Vec3d v6 = new Vec3d((Vec3i)v4).add(0.5, 0.5, 0.5).add(new Vec3d(v5.getDirectionVec()).scale(0.5));
                if (v2.squareDistanceTo(v6) <= 18.0625) {
                    final double v7 = v6.x - v2.x;
                    final double v8 = v6.y - v2.y;
                    final double v9 = v6.z - v2.z;
                    final double v10 = Math.sqrt(v7 * v7 + v9 * v9);
                    final float v11 = (float)Math.toDegrees(Math.atan2(v9, v7)) - 90.0f;
                    final float v12 = (float)(-Math.toDegrees(Math.atan2(v8, v10)));
                    final float[] v13 = { p.mc.player.rotationYaw + MathHelper.wrapDegrees(v11 - p.mc.player.rotationYaw), p.mc.player.rotationPitch + MathHelper.wrapDegrees(v12 - p.mc.player.rotationPitch) };
                    p.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(v13[0], v13[1], p.mc.player.onGround));
                    p.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)p.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    p.mc.playerController.processRightClickBlock(p.mc.player, p.mc.world, v4, v5, v6, v);
                    p.mc.player.swingArm(v);
                    p.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)p.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    return;
                }
            }
        }
    }
    
    public static int b(final Block v) {
        return b(new ItemStack(v).getItem());
    }
    
    public static int b(final Item v) {
        try {
            for (int v2 = 0; v2 < 9; ++v2) {
                final ItemStack v3 = p.mc.player.inventory.getStackInSlot(v2);
                if (v == v3.getItem()) {
                    return v2;
                }
            }
        }
        catch (Exception ex) {}
        return -1;
    }
    
    public static double[] a(final double v, final double v, final double v, final EntityPlayer v) {
        double v2 = v.posX - v;
        double v3 = v.posY - v;
        double v4 = v.posZ - v;
        final double v5 = Math.sqrt(v2 * v2 + v3 * v3 + v4 * v4);
        v2 /= v5;
        v3 /= v5;
        v4 /= v5;
        double v6 = Math.asin(v3);
        double v7 = Math.atan2(v4, v2);
        v6 = v6 * 180.0 / 3.141592653589793;
        v7 = v7 * 180.0 / 3.141592653589793;
        v7 += 90.0;
        return new double[] { v7, v6 };
    }
    
    public static void a(final float v, final float v) {
        p.mc.player.rotationYaw = v;
        p.mc.player.rotationPitch = v;
    }
    
    public static void a(final double[] v) {
        p.mc.player.rotationYaw = (float)v[0];
        p.mc.player.rotationPitch = (float)v[1];
    }
    
    public static void n(final BlockPos v) {
        a(a(v.getX(), v.getY(), v.getZ(), (EntityPlayer)p.mc.player));
    }
    
    public static BlockPos a(final EntityPlayer v, final int v, final int v, final int v) {
        final int[] v2 = { (int)v.posX, (int)v.posY, (int)v.posZ };
        BlockPos v3;
        if (v.posX < 0.0 && v.posZ < 0.0) {
            v3 = new BlockPos(v2[0] + v - 1, v2[1] + v, v2[2] + v - 1);
        }
        else if (v.posX < 0.0 && v.posZ > 0.0) {
            v3 = new BlockPos(v2[0] + v - 1, v2[1] + v, v2[2] + v);
        }
        else if (v.posX > 0.0 && v.posZ < 0.0) {
            v3 = new BlockPos(v2[0] + v, v2[1] + v, v2[2] + v - 1);
        }
        else {
            v3 = new BlockPos(v2[0] + v, v2[1] + v, v2[2] + v);
        }
        return v3;
    }
    
    public static int nj() {
        for (int v = 0; v < 9; ++v) {
            if (p.mc.player.inventory.getStackInSlot(v) != ItemStack.EMPTY && p.mc.player.inventory.getStackInSlot(v).getItem() instanceof ItemBlock && Block.getBlockFromItem(p.mc.player.inventory.getStackInSlot(v).getItem()).getDefaultState().isFullBlock()) {
                return v;
            }
        }
        return -1;
    }
}
