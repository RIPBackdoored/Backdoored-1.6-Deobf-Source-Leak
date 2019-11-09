package f.b.o.g.a;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Extra Tab", description = "Display full tab menu", category = f.b.o.c.c.CLIENT)
public class k extends c
{
    public static k fq;
    
    public k() {
        super();
        k.fq = this;
    }
    
    @SubscribeEvent
    public void a(final bl v) {
        if (this.bu()) {
            v.rh = v.ru.size();
        }
    }
}
