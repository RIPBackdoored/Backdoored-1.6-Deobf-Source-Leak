package f.b.c;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import f.b.q.c.*;
import java.util.*;

public class k extends x
{
    public k() {
        super(new String[] { "viewinv", "inventory", "inventoryview" });
    }
    
    @Override
    public boolean a(final String[] v) {
        if (v.length < 1) {
            this.mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)this.mc.player));
            return true;
        }
        for (final EntityPlayer v2 : this.mc.world.playerEntities) {
            if (v2.getDisplayNameString().equalsIgnoreCase(v[0])) {
                this.mc.displayGuiScreen((GuiScreen)new GuiInventory(v2));
                return true;
            }
        }
        o.bn("Could not find player " + v[0]);
        return false;
    }
    
    @Override
    public String o() {
        return "-viewinv FitMC";
    }
}
