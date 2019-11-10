package f.b.o.g.combat;

import f.b.o.g.*;
import net.minecraft.init.*;
import f.b.q.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

@c$b(name = "Surround", description = "Surrounds your feet with obsidian", category = f.b.o.c.c.COMBAT)
public class Surround extends c
{
    private BlockPos blockPos;
    
    public Surround() {
        super();
        this.blockPos = new BlockPos(0, -100, 0);
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        final int v = Surround.mc.player.inventory.currentItem;
        final int v2 = n.b(Blocks.OBSIDIAN);
        if (v2 != -1) {
            final BlockPos v3 = new BlockPos(Surround.mc.player.getPositionVector());
            if (v3.equals((Object)this.blockPos)) {
                final BlockPos[] array;
                final BlockPos[] v4 = array = new BlockPos[] { v3.add(0, -1, 1), v3.add(1, -1, 0), v3.add(0, -1, -1), v3.add(-1, -1, 0), v3.add(0, 0, 1), v3.add(1, 0, 0), v3.add(0, 0, -1), v3.add(-1, 0, 0) };
                for (final BlockPos v5 : array) {
                    if (Surround.mc.world.getBlockState(v5).getMaterial().isReplaceable() && Surround.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(v5)).isEmpty()) {
                        Surround.mc.player.inventory.currentItem = v2;
                        n.c(v5);
                    }
                }
                Surround.mc.player.inventory.currentItem = v;
            }
            else {
                this.a(false);
            }
        }
    }
    
    public void bv() {
        this.blockPos = new BlockPos(Surround.mc.player.getPositionVector());
    }
}
