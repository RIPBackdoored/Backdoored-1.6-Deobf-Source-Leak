package f.b.o.g.client;

import f.b.o.g.*;
import f.b.o.g.a.*;
import f.b.a.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Extra Tab", description = "Display full tab menu", category = f.b.o.c.c.CLIENT)
public class ExtraTab extends c
{
    public static k fq;
    
    public ExtraTab() {
        super();
        ExtraTab.fq = (k)this;
    }
    
    @SubscribeEvent
    public void a(final bl v) {
        if (this.bu()) {
            v.rh = v.ru.size();
        }
    }
}
