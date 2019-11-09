package f.b.o.g.x;

import f.b.o.g.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import net.minecraft.item.*;
import java.util.*;

@c$b(name = "Armor Durability Alert", description = "Get alerted when your armor is on low durability", category = f.b.o.c.c.COMBAT)
public class m extends c
{
    private f.b.f.c qz;
    private f.b.f.c qo;
    private f.b.f.c qt;
    
    public m() {
        super();
        this.qz = new f.b.f.c("Max Damage", this, 15, 0, 100);
        this.qo = new f.b.f.c("x", this, 50.0, 0.0, m.mc.displayWidth * 1.2);
        this.qt = new f.b.f.c("y", this, 50.0, 0.0, m.mc.displayHeight * 1.2);
    }
    
    public void bm() {
        if (this.bu() && this.gl()) {
            final ScaledResolution v = new ScaledResolution(m.mc);
            final String v2 = "Low Armor Durability!";
            m.mc.fontRenderer.drawStringWithShadow(v2, (float)(this.qo.cp() - m.mc.fontRenderer.getStringWidth(v2) / 2), (float)(this.qt.cp() - m.mc.fontRenderer.FONT_HEIGHT / 2), Color.RED.getRGB());
        }
    }
    
    private boolean gl() {
        for (final ItemStack v : m.mc.player.inventory.armorInventory) {
            if (v != null) {
                final int v2 = Item.getIdFromItem(v.getItem());
                if (v2 >= 298 && v2 <= 317 && v.getMaxDamage() - v.getItemDamage() < this.qz.cp()) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
}
