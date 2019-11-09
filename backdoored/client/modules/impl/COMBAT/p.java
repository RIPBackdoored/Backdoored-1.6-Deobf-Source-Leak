package f.b.o.g.x;

import f.b.o.g.*;
import java.time.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import f.b.q.*;
import f.b.q.c.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import java.util.*;

@c$b(name = "Auto Trap", description = "Trap nearby players", category = f.b.o.c.c.COMBAT)
public class p extends c
{
    private f.b.f.c pm;
    private f.b.f.c ps;
    private Instant pe;
    
    public p() {
        super();
        this.pm = new f.b.f.c("Range", this, 8.0, 0.0, 15.0);
        this.ps = new f.b.f.c("Millisecond delay", this, 1000, 100, 1500);
        this.pe = Instant.EPOCH;
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        final Instant v = Instant.now();
        if (!j.b(this.pe, v, new Long(this.ps.cp()))) {
            return;
        }
        final int v2 = p.mc.player.inventory.currentItem;
        final int v3 = n.b(Item.getItemFromBlock(Blocks.OBSIDIAN));
        if (v3 == -1) {
            this.a(false);
            o.i("Obsidian was not found in your hotbar!", "red");
            return;
        }
        p.mc.player.inventory.currentItem = v3;
        for (final EntityPlayer v4 : p.mc.world.playerEntities) {
            if (f.b.q.x.c.g(v4)) {
                continue;
            }
            if (p.mc.player.getDistance((Entity)v4) > this.pm.ck() || v4 == p.mc.player || f.b.q.x.c.g(v4)) {
                continue;
            }
            final BlockPos[] array;
            final BlockPos[] v5 = array = new BlockPos[] { n.a(v4, 1, 0, 1), n.a(v4, -1, 0, -1), n.a(v4, 1, 0, -1), n.a(v4, -1, 0, 1), n.a(v4, 1, 1, 1), n.a(v4, -1, 1, -1), n.a(v4, 1, 1, -1), n.a(v4, -1, 1, 1), n.a(v4, 1, 1, 0), n.a(v4, -1, 1, 0), n.a(v4, 0, 1, 1), n.a(v4, 0, 1, -1), n.a(v4, 0, 2, 1), n.a(v4, 0, 2, 0) };
            for (final BlockPos v6 : array) {
                if (p.mc.world.getBlockState(v6).getMaterial().isReplaceable() && p.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(v6)).isEmpty()) {
                    n.c(v6);
                    this.pe = v;
                    return;
                }
            }
        }
        p.mc.player.inventory.currentItem = v2;
    }
}
