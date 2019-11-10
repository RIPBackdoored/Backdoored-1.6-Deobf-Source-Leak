package f.b.o.g.render;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import f.b.o.g.b.*;

@c$b(name = "No Render", description = "Dont render things", category = f.b.o.c.c.RENDER)
public class NoRender extends c
{
    private f.b.f.c tn;
    private f.b.f.c tl;
    private f.b.f.c ti;
    private f.b.f.c tf;
    private f.b.f.c tq;
    private f.b.f.c tk;
    private f.b.f.c tp;
    private f.b.f.c tm;
    private f.b.f.c ts;
    private f.b.f.c te;
    private f.b.f.c tx;
    private f.b.f.c tz;
    private f.b.f.c to;
    private f.b.f.c tt;
    private f.b.f.c tw;
    private f.b.f.c tu;
    private f.b.f.c th;
    private f.b.f.c tv;
    private f.b.f.c td;
    private f.b.f.c ty;
    private f.b.f.c tj;
    private f.b.f.c wa;
    
    public NoRender() {
        super();
        this.tn = new f.b.f.c("Stop Explosions", this, true);
        this.tl = new f.b.f.c("Stop Particles", this, true);
        this.ti = new f.b.f.c("helmet", this, false);
        this.tf = new f.b.f.c("portal", this, false);
        this.tq = new f.b.f.c("crosshair", this, false);
        this.tk = new f.b.f.c("bosshealth", this, false);
        this.tp = new f.b.f.c("bossinfo", this, false);
        this.tm = new f.b.f.c("armor", this, false);
        this.ts = new f.b.f.c("health", this, false);
        this.te = new f.b.f.c("food", this, false);
        this.tx = new f.b.f.c("air", this, false);
        this.tz = new f.b.f.c("hotbar", this, false);
        this.to = new f.b.f.c("experience", this, false);
        this.tt = new f.b.f.c("text", this, false);
        this.tw = new f.b.f.c("horse health", this, false);
        this.tu = new f.b.f.c("horse jump", this, false);
        this.th = new f.b.f.c("chat", this, false);
        this.tv = new f.b.f.c("playerlist", this, false);
        this.td = new f.b.f.c("potion icon", this, false);
        this.ty = new f.b.f.c("subtitles", this, false);
        this.tj = new f.b.f.c("fps graph", this, false);
        this.wa = new f.b.f.c("vignette", this, false);
    }
    
    @SubscribeEvent
    public void a(final v v) {
        if (this.bu() && this.tn.cq() && v.packet instanceof SPacketExplosion) {
            v.setCanceled(true);
        }
        if (this.bu() && this.tl.cq() && v.packet instanceof SPacketParticles) {
            v.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void b(final RenderGameOverlayEvent v) {
        switch (r$z.tc[v.getType().ordinal()]) {
            case 1: {
                if (this.ti.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 2: {
                if (this.tf.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 3: {
                if (this.tq.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 4: {
                if (this.tk.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 5: {
                if (this.tp.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 6: {
                if (this.tm.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 7: {
                if (this.ts.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 8: {
                if (this.te.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 9: {
                if (this.tx.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 10: {
                if (this.tz.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 11: {
                if (this.to.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 12: {
                if (this.tt.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 13: {
                if (this.tw.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 14: {
                if (this.tu.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 15: {
                if (this.th.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 16: {
                if (this.tv.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 17: {
                if (this.td.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 18: {
                if (this.ty.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 19: {
                if (this.tj.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
            case 20: {
                if (this.wa.cq()) {
                    v.setCanceled(true);
                    break;
                }
                break;
            }
        }
    }
}
