package f.b.o.g.render;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "No Fog", description = "Remove fog", category = f.b.o.c.c.RENDER)
public class NoFog extends c
{
    public NoFog() {
        super();
    }
    
    @SubscribeEvent
    public void a(final EntityViewRenderEvent.FogDensity v) {
        if (this.bu()) {
            v.setDensity(0.0f);
            v.setCanceled(true);
        }
    }
}
