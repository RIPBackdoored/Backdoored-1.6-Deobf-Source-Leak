package f.b.o.g.render;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "No Fireworks", description = "Stop people from lagging you out", category = f.b.o.c.c.RENDER)
public class NoFireworks extends c
{
    public NoFireworks() {
        super();
    }
    
    @SubscribeEvent
    public void a(final p v) {
        if (this.bu()) {
            v.setCanceled(true);
        }
    }
}
