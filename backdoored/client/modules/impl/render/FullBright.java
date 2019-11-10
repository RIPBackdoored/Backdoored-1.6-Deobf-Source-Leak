package f.b.o.g.render;

import f.b.o.g.*;
import net.minecraftforge.fml.common.eventhandler.*;
import f.b.a.*;

@c$b(name = "Full Bright", description = "Big Brightness bois", category = f.b.o.c.c.RENDER)
public class FullBright extends c
{
    public FullBright() {
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
