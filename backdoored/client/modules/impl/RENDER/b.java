package f.b.o.g.b;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Anti Overlay", description = "Prevents Overlay", category = f.b.o.c.c.RENDER)
public class b extends c
{
    private f.b.f.c zu;
    private f.b.f.c zh;
    private f.b.f.c zv;
    
    public b() {
        super();
        this.zu = new f.b.f.c("Fire", this, true);
        this.zh = new f.b.f.c("Blocks", this, true);
        this.zv = new f.b.f.c("Water", this, true);
    }
    
    @SubscribeEvent
    public void a(final RenderBlockOverlayEvent v) {
        if (!this.bu()) {
            return;
        }
        boolean v2 = false;
        switch (b$z.zw[v.getOverlayType().ordinal()]) {
            case 1: {
                if (this.zu.cq()) {
                    v2 = true;
                    break;
                }
                break;
            }
            case 2: {
                if (this.zh.cq()) {
                    v2 = true;
                    break;
                }
                break;
            }
            case 3: {
                if (this.zv.cq()) {
                    v2 = true;
                    break;
                }
                break;
            }
        }
        v.setCanceled(v2);
    }
}
