package f.b.c;

import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import f.b.q.*;
import f.b.q.c.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class w extends x
{
    boolean bg;
    int br;
    boolean bc;
    int bn;
    BlockPos[] blockPos;
    
    public w() {
        super(new String[] { "nomadbase", "fitbase", "autonomadbase" });
        this.bg = false;
        this.br = 0;
        this.bc = false;
        this.bn = 0;
    }
    
    @Override
    public boolean a(final String[] v) {
        if (v.length == 0) {
            this.bn = 0;
            this.bg = true;
        }
        if ((v.length > 1 && v[0].equalsIgnoreCase("delay")) || v[0].equalsIgnoreCase("setdelay")) {
            this.br = Integer.valueOf(v[1]);
            if (this.br == 0) {
                this.bc = false;
            }
            else {
                this.bc = true;
            }
        }
        return true;
    }
    
    @SubscribeEvent
    public void a(final TickEvent.ClientTickEvent v) {
        if (!this.bg) {
            return;
        }
        if (this.bc && this.bn % this.br != 0) {
            ++this.bn;
            return;
        }
        final BlockPos[] v2 = { new BlockPos((Vec3i)this.mc.player.getPosition().add(1, -1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, -1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, -1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, -1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, -1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, -1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, -1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, -1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, -1, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, -1, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, -1, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, -1, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, -1, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, -1, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, -1, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, -1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, -1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, -1, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, -1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, -1, -1)) };
        final BlockPos[] v3 = { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 1, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 1, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 1, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 1, -2)) };
        final BlockPos[] v4 = { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 2, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 2, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 2, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 2, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 2, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 2, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 2, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 2, -2)) };
        final BlockPos[] v5 = { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 2, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 2, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 2, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 2, -2)) };
        final int v6 = MathHelper.floor(this.mc.player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        BlockPos[] v7;
        BlockPos[] v8;
        if (v6 == 0) {
            v7 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, -2)) };
            v8 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 1)) };
        }
        else if (v6 == 1) {
            v7 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, -2)) };
            v8 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, -1)) };
        }
        else if (v6 == 2) {
            v7 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, -2)) };
            v8 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, -1)) };
        }
        else {
            v7 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, -2)) };
            v8 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, -1)) };
        }
        final int v9 = this.mc.player.inventory.currentItem;
        int v10 = -1;
        for (int v11 = 0; v11 < 9; ++v11) {
            if (this.mc.player.inventory.getStackInSlot(v11) != ItemStack.EMPTY && this.mc.player.inventory.getStackInSlot(v11).getItem() instanceof ItemBlock && Block.getBlockFromItem(this.mc.player.inventory.getStackInSlot(v11).getItem()).getDefaultState().isFullBlock()) {
                v10 = v11;
                break;
            }
        }
        if (v10 != -1) {
            this.mc.player.inventory.currentItem = v10;
            for (final BlockPos v12 : v2) {
                if (this.mc.world.getBlockState(v12).getMaterial().isReplaceable()) {
                    n.c(v12);
                    if (this.bc) {
                        ++this.bn;
                        return;
                    }
                }
            }
            for (final BlockPos v12 : v7) {
                if (this.mc.world.getBlockState(v12).getMaterial().isReplaceable()) {
                    n.c(v12);
                    if (this.bc) {
                        ++this.bn;
                        return;
                    }
                }
            }
            for (final BlockPos v12 : v3) {
                if (this.mc.world.getBlockState(v12).getMaterial().isReplaceable()) {
                    n.c(v12);
                    if (this.bc) {
                        ++this.bn;
                        return;
                    }
                }
            }
            for (final BlockPos v12 : v4) {
                if (this.mc.world.getBlockState(v12).getMaterial().isReplaceable()) {
                    n.c(v12);
                    if (this.bc) {
                        ++this.bn;
                        return;
                    }
                }
            }
            for (final BlockPos v12 : v5) {
                if (this.mc.world.getBlockState(v12).getMaterial().isReplaceable()) {
                    n.c(v12);
                    if (this.bc) {
                        ++this.bn;
                        return;
                    }
                }
            }
            for (final BlockPos v12 : v8) {
                if (this.mc.world.getBlockState(v12).getMaterial().isReplaceable()) {
                    n.c(v12);
                    if (this.bc) {
                        ++this.bn;
                        return;
                    }
                }
            }
            this.mc.player.inventory.currentItem = v9;
            this.bg = false;
            return;
        }
        o.i("No blocks found in hotbar!", "red");
        this.bg = false;
    }
    
    @Override
    public String o() {
        return "-nomadbase or -nomadbase setdelay <0/1/2/..> (6 is the best)";
    }
}
