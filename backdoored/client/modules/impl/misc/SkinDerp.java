package f.b.o.g.misc;

import f.b.o.g.*;
import net.minecraft.entity.player.*;

@c$b(name = "Skin Derp", description = "Flickers your skin", category = f.b.o.c.c.MISC)
public class SkinDerp extends c
{
    private f.b.f.c sj;
    private static final EnumPlayerModelParts[] enumPlayerModelParts;
    
    public SkinDerp() {
        super();
        this.sj = new f.b.f.c("Speed", this, 2.0, 1.0, 5.0);
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        int v = (int)Math.round(SkinDerp.mc.player.ticksExisted / this.sj.ck() % ((SkinDerp.enumPlayerModelParts.length - 1) * 2));
        boolean v2 = false;
        if (v >= SkinDerp.enumPlayerModelParts.length) {
            v2 = true;
            v -= SkinDerp.enumPlayerModelParts.length;
        }
        SkinDerp.mc.gameSettings.setModelPartEnabled(SkinDerp.enumPlayerModelParts[v], v2);
    }
    
    public void bd() {
        for (final EnumPlayerModelParts v : SkinDerp.enumPlayerModelParts) {
            SkinDerp.mc.gameSettings.setModelPartEnabled(v, true);
        }
    }
    
    static {
        enumPlayerModelParts = new EnumPlayerModelParts[] { EnumPlayerModelParts.LEFT_SLEEVE, EnumPlayerModelParts.JACKET, EnumPlayerModelParts.HAT, EnumPlayerModelParts.LEFT_PANTS_LEG, EnumPlayerModelParts.RIGHT_PANTS_LEG, EnumPlayerModelParts.RIGHT_SLEEVE };
    }
}
