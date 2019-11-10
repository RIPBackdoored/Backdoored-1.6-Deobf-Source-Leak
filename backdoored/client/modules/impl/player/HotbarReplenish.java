package f.b.o.g.player;

import f.b.o.g.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.entity.player.*;

@c$b(name = "Hotbar Replenish", description = "Replenish items in your hotbar when they are used", category = f.b.o.c.c.PLAYER)
public class HotbarReplenish extends c
{
    private f.b.f.c xp;
    private f.b.f.c xm;
    private int xs;
    
    public HotbarReplenish() {
        super();
        this.xp = new f.b.f.c("Cooldown in ticks", this, 100, 0, 200);
        this.xm = new f.b.f.c("Min Stack Size (percent)", this, 20, 1, 99);
        this.xs = 0;
    }
    
    public void bh() {
        --this.xs;
        if (this.xs <= 0) {
            final List<ItemStack> v = gh();
            for (final ItemStack v2 : v) {
                if (v2 == null || b(v2) < this.xm.cp()) {}
            }
            this.xs = this.xp.cp();
        }
    }
    
    private static int b(final ItemStack v) {
        return (int)Math.ceil(v.getCount() * 100.0f / v.getMaxStackSize());
    }
    
    private static List<ItemStack> gh() {
        return (List<ItemStack>)HotbarReplenish.mc.player.inventory.mainInventory.subList(0, InventoryPlayer.getHotbarSize());
    }
}
