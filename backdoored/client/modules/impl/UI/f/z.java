package f.b.o.g.w.f;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Hud", description = "Hud Overlay", category = f.b.o.c.c.UI, defaultOn = true, defaultIsVisible = false)
public class z extends c
{
    public static z wu;
    private e wh;
    private f.b.f.c[] wv;
    
    public z() {
        super();
        this.wh = new e();
        this.wv = new f.b.f.c[] { new f.b.f.c("Icon", this, true), new f.b.f.c("Watermark", this, true), new f.b.f.c("Module List", this, true), new f.b.f.c("Mod Align", this, "Top right", new String[] { "Top right", "Custom", "Top left", "Bot right", "Bot left" }), new f.b.f.c("Custom-x", this, 2, 0, z.mc.displayWidth + 100), new f.b.f.c("Custom-y", this, 2, 0, z.mc.displayHeight + 100), new f.b.f.c("Mod Order", this, "Length", new String[] { "Length", "Alphabetical" }), new f.b.f.c("Rainbow Colour", this, false) };
        z.wu = this;
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @Override
    public void b(final RenderGameOverlayEvent.Post v) {
        if (v.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        if (this.bu()) {
            this.wh.a((RenderGameOverlayEvent)v, this.wv, this);
        }
    }
}
