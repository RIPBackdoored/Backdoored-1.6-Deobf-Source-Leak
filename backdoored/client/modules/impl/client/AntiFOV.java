package f.b.o.g.client;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Anti FOV", description = "Cap your FOV", category = f.b.o.c.c.CLIENT)
public class AntiFOV extends c
{
    private f.b.f.c ip;
    
    public AntiFOV() {
        super();
        this.ip = new f.b.f.c("Max FOV", this, 125, 0, 360);
    }
    
    @SubscribeEvent
    public void a(final EntityViewRenderEvent.FOVModifier v) {
        if (this.bu()) {
            v.setFOV(Math.min(v.getFOV(), (float)this.ip.cp()));
        }
    }
}
