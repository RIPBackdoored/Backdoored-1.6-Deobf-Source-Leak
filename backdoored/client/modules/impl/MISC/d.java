package f.b.o.g.g;

import f.b.o.g.*;
import net.minecraft.entity.player.*;

@c$b(name = "Skin Derp", description = "Flickers your skin", category = f.b.o.c.c.MISC)
public class d extends c
{
    private f.b.f.c sj;
    private static final EnumPlayerModelParts[] enumPlayerModelParts;
    
    public d() {
        super();
        this.sj = new f.b.f.c("Speed", this, 2.0, 1.0, 5.0);
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        int v = (int)Math.round(d.mc.player.ticksExisted / this.sj.ck() % ((d.enumPlayerModelParts.length - 1) * 2));
        boolean v2 = false;
        if (v >= d.enumPlayerModelParts.length) {
            v2 = true;
            v -= d.enumPlayerModelParts.length;
        }
        d.mc.gameSettings.setModelPartEnabled(d.enumPlayerModelParts[v], v2);
    }
    
    public void bd() {
        for (final EnumPlayerModelParts v : d.enumPlayerModelParts) {
            d.mc.gameSettings.setModelPartEnabled(v, true);
        }
    }
    
    static {
        enumPlayerModelParts = new EnumPlayerModelParts[] { EnumPlayerModelParts.LEFT_SLEEVE, EnumPlayerModelParts.JACKET, EnumPlayerModelParts.HAT, EnumPlayerModelParts.LEFT_PANTS_LEG, EnumPlayerModelParts.RIGHT_PANTS_LEG, EnumPlayerModelParts.RIGHT_SLEEVE };
    }
}
