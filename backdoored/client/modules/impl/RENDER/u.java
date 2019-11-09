package f.b.o.g.b;

import f.b.o.g.*;
import net.minecraftforge.fml.common.eventhandler.*;
import f.b.a.*;

@c$b(name = "Full Bright", description = "Big Brightness bois", category = f.b.o.c.c.RENDER)
public class u extends c
{
    public u() {
        super();
    }
    
    @SubscribeEvent
    public void a(final i v) {
        if (this.bu()) {
            v.gg = 1.0f;
        }
    }
    
    @SubscribeEvent
    public void a(final l v) {
        if (this.bu()) {
            v.gr = 1.0f;
        }
    }
}
