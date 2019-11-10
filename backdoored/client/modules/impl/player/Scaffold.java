package f.b.o.g.player;

import f.b.o.g.*;
import f.b.q.*;
import f.b.q.c.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.entity.*;

@c$b(name = "Scaffold", description = "Automatically bridges for you", category = f.b.o.c.c.PLAYER)
public class Scaffold extends c
{
    private f.b.f.c xj;
    private f.b.f.c za;
    private f.b.f.c zb;
    
    public Scaffold() {
        super();
        this.xj = new f.b.f.c("Radius", this, 0, 0, 2);
        this.za = new f.b.f.c("Down", this, true);
        this.zb = new f.b.f.c("Tower", this, true);
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        final int v = Scaffold.mc.player.inventory.currentItem;
        final int v2 = n.nj();
        if (v2 == -1) {
            o.i("No blocks found in hotbar!", "red");
            this.a(false);
            return;
        }
        Scaffold.mc.player.inventory.currentItem = v2;
        if (this.xj.cp() != 0 && this.za.cq()) {
            this.xj.g(0);
        }
        if (Scaffold.mc.gameSettings.keyBindSprint.isKeyDown() && this.za.cq()) {
            final float v3 = (float)Math.toRadians(Scaffold.mc.player.rotationYaw);
            if (Scaffold.mc.gameSettings.keyBindForward.isKeyDown()) {
                Scaffold.mc.player.motionX = -MathHelper.sin(v3) * 0.03f;
                Scaffold.mc.player.motionZ = MathHelper.cos(v3) * 0.03f;
            }
            if (Scaffold.mc.gameSettings.keyBindBack.isKeyDown()) {
                Scaffold.mc.player.motionX = MathHelper.sin(v3) * 0.03f;
                Scaffold.mc.player.motionZ = -MathHelper.cos(v3) * 0.03f;
            }
            if (Scaffold.mc.gameSettings.keyBindLeft.isKeyDown()) {
                Scaffold.mc.player.motionX = MathHelper.cos(v3) * 0.03f;
                Scaffold.mc.player.motionZ = MathHelper.sin(v3) * 0.03f;
            }
            if (Scaffold.mc.gameSettings.keyBindRight.isKeyDown()) {
                Scaffold.mc.player.motionX = -MathHelper.cos(v3) * 0.03f;
                Scaffold.mc.player.motionZ = -MathHelper.sin(v3) * 0.03f;
            }
            final BlockPos v4 = new BlockPos(Scaffold.mc.player.posX, Scaffold.mc.player.posY - 2.0, Scaffold.mc.player.posZ);
            if (Scaffold.mc.world.getBlockState(v4).getMaterial().isReplaceable()) {
                n.c(v4);
            }
            if (Math.abs(Scaffold.mc.player.motionX) > 0.03 && Scaffold.mc.world.getBlockState(new BlockPos(v4.getX() + Scaffold.mc.player.motionX / Math.abs(Scaffold.mc.player.motionX), (double)(v4.getY() - 1), (double)v4.getZ())).getMaterial().isReplaceable()) {
                n.c(new BlockPos(v4.getX() + Scaffold.mc.player.motionX / Math.abs(Scaffold.mc.player.motionX), (double)(v4.getY() - 1), (double)v4.getZ()));
            }
            else if (Math.abs(Scaffold.mc.player.motionZ) > 0.03 && Scaffold.mc.world.getBlockState(new BlockPos((double)v4.getX(), (double)(v4.getY() - 1), v4.getZ() + Scaffold.mc.player.motionZ / Math.abs(Scaffold.mc.player.motionZ))).getMaterial().isReplaceable()) {
                n.c(new BlockPos((double)v4.getX(), (double)(v4.getY() - 1), v4.getZ() + Scaffold.mc.player.motionZ / Math.abs(Scaffold.mc.player.motionZ)));
            }
            Scaffold.mc.player.inventory.currentItem = v;
            return;
        }
        if (this.xj.cp() == 0) {
            final BlockPos v5 = new BlockPos(Scaffold.mc.player.posX, Scaffold.mc.player.posY - 1.0, Scaffold.mc.player.posZ);
            if (Scaffold.mc.world.getBlockState(v5).getMaterial().isReplaceable()) {
                n.c(v5);
            }
            if (Math.abs(Scaffold.mc.player.motionX) > 0.033 && Scaffold.mc.world.getBlockState(new BlockPos(v5.getX() + Scaffold.mc.player.motionX / Math.abs(Scaffold.mc.player.motionX), (double)v5.getY(), (double)v5.getZ())).getMaterial().isReplaceable()) {
                n.c(new BlockPos(v5.getX() + Scaffold.mc.player.motionX / Math.abs(Scaffold.mc.player.motionX), (double)v5.getY(), (double)v5.getZ()));
            }
            else if (Math.abs(Scaffold.mc.player.motionZ) > 0.033 && Scaffold.mc.world.getBlockState(new BlockPos((double)v5.getX(), (double)v5.getY(), v5.getZ() + Scaffold.mc.player.motionZ / Math.abs(Scaffold.mc.player.motionZ))).getMaterial().isReplaceable()) {
                n.c(new BlockPos((double)v5.getX(), (double)v5.getY(), v5.getZ() + Scaffold.mc.player.motionZ / Math.abs(Scaffold.mc.player.motionZ)));
            }
            Scaffold.mc.player.inventory.currentItem = v;
            return;
        }
        final ArrayList<BlockPos> v6 = new ArrayList<BlockPos>();
        for (int v7 = -this.xj.cp(); v7 <= this.xj.cp(); ++v7) {
            for (int v8 = -this.xj.cp(); v8 <= this.xj.cp(); ++v8) {
                v6.add(new BlockPos(Scaffold.mc.player.posX + v7, Scaffold.mc.player.posY - 1.0, Scaffold.mc.player.posZ + v8));
            }
        }
        for (final BlockPos v9 : v6) {
            if (Scaffold.mc.world.getBlockState(v9).getMaterial().isReplaceable()) {
                n.c(v9);
            }
        }
        Scaffold.mc.player.inventory.currentItem = v;
    }
    
    @SubscribeEvent
    public void a(final LivingEvent.LivingUpdateEvent v) {
        if (!this.bu() || !this.za.cq()) {
            return;
        }
        if (v.getEntityLiving() instanceof EntityPlayer) {
            final EntityPlayer v2 = (EntityPlayer)v.getEntityLiving();
            if (v2.isSneaking()) {
                if (Math.abs(Scaffold.mc.player.posX) - (int)Math.abs(Scaffold.mc.player.posX) < 0.1 || Math.abs(Scaffold.mc.player.posX) - (int)Math.abs(Scaffold.mc.player.posX) > 0.9) {
                    Scaffold.mc.player.posX = (double)Math.round(Math.abs(Scaffold.mc.player.posX) - (int)Math.abs(Scaffold.mc.player.posX));
                }
                if (Math.abs(Scaffold.mc.player.posZ) - (int)Math.abs(Scaffold.mc.player.posZ) < 0.1 || Math.abs(Scaffold.mc.player.posZ) - (int)Math.abs(Scaffold.mc.player.posZ) > 0.9) {
                    Scaffold.mc.player.posZ = (double)Math.round(Math.abs(Scaffold.mc.player.posZ) - (int)Math.abs(Scaffold.mc.player.posZ));
                }
            }
        }
    }
    
    @SubscribeEvent
    public void a(final LivingEvent.LivingJumpEvent v) {
        if (this.bu() && this.zb.cq()) {
            final EntityPlayerSP player = Scaffold.mc.player;
            player.motionY += 0.1;
        }
    }
}
