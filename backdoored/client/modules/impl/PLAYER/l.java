package f.b.o.g.t;

import f.b.o.g.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.*;
import f.b.q.c.*;

@c$b(name = "FastPlace", description = "Place blocks or use items faster", category = f.b.o.c.c.PLAYER)
public class l extends c
{
    private f.b.f.c xk;
    
    public l() {
        super();
        this.xk = new f.b.f.c("Whitelist", this, "All", new String[] { "All", "Exp Only", "Crystal Only", "Exp and Crystal only" });
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        final Item v = l.mc.player.inventory.getCurrentItem().getItem();
        final boolean v2 = v instanceof ItemExpBottle;
        final boolean v3 = v instanceof ItemEndCrystal;
        final String ci = this.xk.ci();
        switch (ci) {
            case "All": {
                this.gu();
                break;
            }
            case "Exp Only": {
                if (v2) {
                    this.gu();
                    break;
                }
                break;
            }
            case "Crystal Only": {
                if (v3) {
                    this.gu();
                    break;
                }
                break;
            }
            case "Exp and Crystal only": {
                if (v3 || v2) {
                    this.gu();
                    break;
                }
                break;
            }
        }
    }
    
    private void gu() {
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)Minecraft.class, (Object)l.mc, (Object)0, new String[] { "rightClickDelayTimer", "field_71467_ac" });
        }
        catch (Exception v) {
            v.printStackTrace();
            this.a(false);
            o.bn("Disabled fastplace due to error: " + v.toString());
        }
    }
}
