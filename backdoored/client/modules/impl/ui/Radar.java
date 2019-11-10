package f.b.o.g.ui;

import f.b.o.g.*;
import f.b.j.*;
import net.minecraft.entity.player.*;
import java.awt.*;
import java.util.*;

@c$b(name = "Radar", description = "See nearby players", category = f.b.o.c.c.UI)
public class Radar extends c
{
    private f.b.f.c uv;
    private f.b.f.c ud;
    private f.b.f.c uy;
    private f.b.f.c uj;
    private f.b.f.c ha;
    private f.b.f.c hb;
    private f.b.f.c hg;
    
    public Radar() {
        super();
        this.uv = new f.b.f.c("x", this, 0, 0, Radar.mc.displayWidth + 50);
        this.ud = new f.b.f.c("y", this, 0, 0, Radar.mc.displayWidth + 50);
        this.uy = new f.b.f.c("Text Height", this, 20, 1, 50);
        this.uj = new f.b.f.c("r", this, 0, 0, 255);
        this.ha = new f.b.f.c("g", this, 0, 0, 255);
        this.hb = new f.b.f.c("b", this, 0, 0, 255);
        this.hg = new f.b.f.c("a", this, 0, 0, 255);
    }
    
    public void bm() {
        if (!this.bu()) {
            return;
        }
        int v = 0;
        final int v2 = g.fontRenderer.FONT_HEIGHT;
        g.fontRenderer.FONT_HEIGHT = this.uy.cp();
        for (final EntityPlayer v3 : Radar.mc.world.playerEntities) {
            if (!v3.equals((Object)Radar.mc.player)) {
                g.fontRenderer.drawString(v3.getDisplayNameString(), this.uv.cp(), this.ud.cp() + v, new Color(this.uj.cp(), this.ha.cp(), this.hb.cp(), this.hg.cp()).getRGB());
                v += g.fontRenderer.FONT_HEIGHT + 2;
            }
        }
        g.fontRenderer.FONT_HEIGHT = v2;
    }
}
