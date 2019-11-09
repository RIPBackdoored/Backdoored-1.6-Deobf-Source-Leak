package f.b.o.g.x;

import f.b.o.g.*;
import net.minecraft.util.math.*;
import \u0000f.\u0000b.\u0000o.\u0000g.\u0000x.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.tileentity.*;

@c$b(name = "HopperAura", description = "Break nearby hoppers", category = f.b.o.c.c.COMBAT)
public class n extends c
{
    private Set<BlockPos> pv;
    private int[] pd;
    private f.b.f.c py;
    private f.b.f.c pj;
    private f.b.f.c ma;
    
    public n() {
        super();
        this.pv = new n$z(this);
        this.pd = new int[] { 278, 285, 274, 270, 257 };
        this.py = new f.b.f.c("Distance", this, 5.0, 1.0, 10.0);
        this.pj = new f.b.f.c("LockRotations", this, true);
        this.ma = new f.b.f.c("Break Own", this, false);
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        final List<TileEntity> v = (List<TileEntity>)n.mc.world.loadedTileEntityList.stream().filter(\u0000n::a).collect(Collectors.toList());
        if (v.size() > 0) {
            for (final TileEntity v2 : v) {
                final BlockPos v3 = v2.getPos();
                if (!this.ma.cq() && this.pv.contains(v3)) {
                    continue;
                }
                if (n.mc.player.getDistance((double)v3.getX(), (double)v3.getY(), (double)v3.getZ()) > this.py.ck()) {
                    continue;
                }
                for (final int v4 : this.pd) {
                    final int v5 = f.b.q.n.b(Item.getItemById(v4));
                    if (v5 != -1) {
                        n.mc.player.inventory.currentItem = v5;
                        if (this.pj.cq()) {
                            f.b.q.n.n(v3);
                        }
                        n.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, v2.getPos(), EnumFacing.UP));
                        n.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, v2.getPos(), EnumFacing.UP));
                        return;
                    }
                }
            }
        }
    }
    
    public void bd() {
        this.pv.clear();
    }
    
    @SubscribeEvent
    public void b(final PlayerInteractEvent.RightClickBlock v) {
        if (n.mc.player.inventory.getStackInSlot(n.mc.player.inventory.currentItem).getItem().equals(Item.getItemById(154))) {
            this.pv.add(n.mc.objectMouseOver.getBlockPos().offset(n.mc.objectMouseOver.sideHit));
        }
    }
    
    private static /* synthetic */ boolean a(final TileEntity v) {
        return v instanceof TileEntityHopper;
    }
}
