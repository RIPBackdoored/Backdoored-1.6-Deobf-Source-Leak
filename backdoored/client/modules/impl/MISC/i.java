package f.b.o.g.g;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "GreenText", description = "Prepend text with >", category = f.b.o.c.c.MISC)
public class i extends c
{
    public i() {
        super();
    }
    
    @SubscribeEvent
    public void a(final ClientChatEvent v) {
        if (!this.bu() || v.getMessage().charAt(0) == '/' || v.getMessage().charAt(0) == '!') {
            return;
        }
        v.setMessage(">" + v.getMessage());
    }
}
