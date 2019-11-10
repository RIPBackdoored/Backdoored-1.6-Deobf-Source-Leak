package f.b.o.g.player;

import f.b.o.g.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import f.b.a.*;
import net.minecraftforge.fml.common.eventhandler.*;
import f.b.q.*;
import f.b.q.c.*;
import f.b.*;
import net.minecraft.util.*;

@c$b(name = "Auto Wither", description = "2 tick withers", category = f.b.o.c.c.PLAYER)
public class AutoWither extends c
{
    private Item item;
    private Item item;
    private BlockPos blockPos;
    private int ev;
    
    public AutoWither() {
        super();
        this.item = new ItemStack(Blocks.SOUL_SAND).getItem();
        this.item = new ItemStack((Block)Blocks.SKULL).getItem();
        this.blockPos = new BlockPos(0, 0, 0);
        this.ev = -1;
    }
    
    public void bv() {
        ++this.ev;
        this.gx();
        ++this.ev;
    }
    
    @SubscribeEvent
    public void a(final bj v) {
        if (!this.bu() || this.ev > 1) {
            this.ev = -1;
            this.a(false);
            return;
        }
        if (this.ev == 0) {
            this.gx();
        }
        if (this.ev == 1) {
            this.gz();
            this.ev = -1;
            this.a(false);
            return;
        }
        ++this.ev;
    }
    
    private boolean gx() {
        if (AutoWither.mc.objectMouseOver == null || AutoWither.mc.objectMouseOver.sideHit == null) {
            this.blockPos = AutoWither.mc.player.getPosition().add(2, 0, 0);
        }
        else {
            this.blockPos = AutoWither.mc.objectMouseOver.getBlockPos().offset(AutoWither.mc.objectMouseOver.sideHit);
        }
        final int v = n.b(this.item);
        final int v2 = this.go();
        if (v2 == -1 || v == -1) {
            final String v3 = (v2 == -1) ? "Wither Skull" : "Soul Sand";
            o.i(v3 + " was not found in your hotbar!", "red");
            this.a(false);
            return false;
        }
        AutoWither.mc.player.inventory.currentItem = n.b(this.item);
        n.c(this.blockPos);
        if (this.gt()) {
            n.c(this.blockPos.add(0, 1, 0));
            n.c(this.blockPos.add(1, 1, 0));
            n.c(this.blockPos.add(-1, 1, 0));
        }
        else {
            n.c(this.blockPos.add(0, 1, 0));
            n.c(this.blockPos.add(0, 1, 1));
            n.c(this.blockPos.add(0, 1, -1));
        }
        return true;
    }
    
    private boolean gz() {
        final int v = this.go();
        if (v != -1) {
            AutoWither.mc.player.inventory.currentItem = v;
            if (this.gt()) {
                n.c(this.blockPos.add(0, 2, 0));
                n.c(this.blockPos.add(1, 2, 0));
                n.c(this.blockPos.add(-1, 2, 0));
            }
            else {
                n.c(this.blockPos.add(0, 2, 0));
                n.c(this.blockPos.add(0, 2, 1));
                n.c(this.blockPos.add(0, 2, -1));
            }
            return true;
        }
        return false;
    }
    
    private int go() {
        for (int v = 0; v < 9; ++v) {
            final ItemStack v2 = p.mc.player.inventory.getStackInSlot(v);
            if (v2.getItem().getItemStackDisplayName(v2).equals("Wither Skeleton Skull")) {
                return v;
            }
        }
        return -1;
    }
    
    private boolean gt() {
        final EnumFacing v = AutoWither.mc.player.getHorizontalFacing();
        return v != EnumFacing.EAST && v != EnumFacing.WEST;
    }
}
