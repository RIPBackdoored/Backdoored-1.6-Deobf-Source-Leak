package f.b.o.g.x;

import f.b.o.g.*;
import net.minecraft.init.*;
import f.b.q.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import f.b.o.g.b.*;
import java.util.*;
import net.minecraft.entity.*;

@c$b(name = "Web Aura", description = "Trap people camping in holes", category = f.b.o.c.c.COMBAT)
public class v extends c
{
    private f.b.f.c mq;
    private f.b.f.c mk;
    
    public v() {
        super();
        this.mq = new f.b.f.c("Range", this, 4.0, 1.0, 10.0);
        this.mk = new f.b.f.c("Only in hole", this, true);
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        final int v = n.b(Blocks.WEB);
        if (v == -1) {
            return;
        }
        final List<EntityPlayer> v2 = (List<EntityPlayer>)f.b.o.g.x.v.mc.world.playerEntities.stream().filter(this::b).collect(Collectors.toList());
        if (v2.size() > 0) {
            f.b.o.g.x.v.mc.player.inventory.currentItem = v;
        }
        for (final EntityPlayer v3 : v2) {
            final BlockPos v4 = new BlockPos((int)v3.posX, (int)v3.posY, (int)v3.posZ);
            if (this.mk.cq() && !i.b(v4)) {
                continue;
            }
            if (f.b.o.g.x.v.mc.world.getBlockState(v4).getMaterial().isReplaceable()) {
                n.c(v4);
            }
            if (this.mk.cq()) {
                continue;
            }
            if (f.b.o.g.x.v.mc.world.getBlockState(v4.add(1, 0, 0)).getMaterial().isReplaceable()) {
                n.c(v4.add(1, 0, 0));
            }
            if (f.b.o.g.x.v.mc.world.getBlockState(v4.add(0, 0, 1)).getMaterial().isReplaceable()) {
                n.c(v4.add(0, 0, 1));
            }
            if (f.b.o.g.x.v.mc.world.getBlockState(v4.add(0, 0, -1)).getMaterial().isReplaceable()) {
                n.c(v4.add(0, 0, -1));
            }
            if (!f.b.o.g.x.v.mc.world.getBlockState(v4.add(-1, 0, 0)).getMaterial().isReplaceable()) {
                continue;
            }
            n.c(v4.add(-1, 0, 0));
        }
    }
    
    private /* synthetic */ boolean b(final EntityPlayer v) {
        return v.mc.player.getDistance((Entity)v) <= this.mq.ck() && !v.mc.player.equals((Object)v);
    }
}
