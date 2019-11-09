package f.b.o.g.b;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "No Fireworks", description = "Stop people from lagging you out", category = f.b.o.c.c.RENDER)
public class d extends c
{
    public d() {
        super();
    }
    
    @SubscribeEvent
    public void a(final p v) {
        if (this.bu()) {
            v.setCanceled(true);
        }
    }
}
