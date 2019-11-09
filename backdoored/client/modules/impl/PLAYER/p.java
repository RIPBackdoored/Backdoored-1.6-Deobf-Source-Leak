package f.b.o.g.t;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.*;
import f.b.q.c.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

@c$b(name = "Fake Item", description = "Always be holding your first item", category = f.b.o.c.c.PLAYER)
public class p extends c
{
    private int xf;
    private int xq;
    
    public p() {
        super();
        this.xq = 0;
    }
    
    @SubscribeEvent
    public void a(final q v) {
        if (v.packet instanceof CPacketHeldItemChange && this.bu()) {
            final CPacketHeldItemChange v2 = (CPacketHeldItemChange)v.packet;
            this.xq = this.gw();
            try {
                ObfuscationReflectionHelper.setPrivateValue((Class)CPacketHeldItemChange.class, (Object)v2, (Object)this.xq, new String[] { "slotId", "field_149615_a" });
            }
            catch (Exception v3) {
                o.bn("Disabled fake item due to error: " + v3.getMessage());
                this.a(false);
                v3.printStackTrace();
            }
        }
    }
    
    public void bv() {
        p.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(0));
    }
    
    public void bd() {
        p.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(p.mc.player.inventory.currentItem));
    }
    
    private int gw() {
        for (int v = 0; v < 9; ++v) {
            final ItemStack v2 = (ItemStack)p.mc.player.inventory.mainInventory.get(v);
            if (v2.getItem() == Items.AIR) {
                return v;
            }
        }
        return 0;
    }
}
