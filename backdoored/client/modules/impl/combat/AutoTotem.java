package f.b.o.g.combat;

import f.b.o.g.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

@c$b(name = "Auto Totem", description = "Works in guis", category = f.b.o.c.c.COMBAT)
public class AutoTotem extends c
{
    private boolean pi;
    private f.b.f.c pf;
    private f.b.f.c pq;
    private f.b.f.c pk;
    private f.b.f.c pp;
    
    public AutoTotem() {
        super();
        this.pi = true;
        this.pf = new f.b.f.c("Always Holding", this, true);
        this.pq = new f.b.f.c("Min Health to Equip", this, 6, 0, 20);
        this.pk = new f.b.f.c("Refill Hotbar Slot", this, false);
        this.pp = new f.b.f.c("Hotbar Slot", this, 9, 0, 9);
    }
    
    public void bh() {
        if (this.bu()) {
            if (AutoTotem.mc.player.getHealth() <= this.pq.cp() && !this.pf.cq()) {
                this.pi = true;
            }
            if (this.pi && AutoTotem.mc.player.getHeldItemOffhand().isEmpty()) {
                final int v = this.a(Items.TOTEM_OF_UNDYING);
                if (v != -1) {
                    AutoTotem.mc.playerController.windowClick(0, v, 0, ClickType.PICKUP_ALL, (EntityPlayer)AutoTotem.mc.player);
                    AutoTotem.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP_ALL, (EntityPlayer)AutoTotem.mc.player);
                }
            }
            if (this.pf.cq()) {
                this.pi = true;
            }
            if (!this.pf.cq()) {
                this.pi = false;
            }
            if (this.pk.cq()) {
                final ItemStack v2 = AutoTotem.mc.player.inventory.getStackInSlot(this.pp.cp());
                if (v2.isEmpty()) {
                    final int v3 = this.a(Items.TOTEM_OF_UNDYING);
                    AutoTotem.mc.playerController.windowClick(0, v3, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                    AutoTotem.mc.playerController.windowClick(0, this.pp.cp(), 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                }
            }
        }
    }
    
    private int a(final Item v) {
        for (int v2 = 9; v2 <= 44; ++v2) {
            if (AutoTotem.mc.player.inventoryContainer.getSlot(v2).getStack().getItem() == v) {
                return v2;
            }
        }
        return -1;
    }
}
