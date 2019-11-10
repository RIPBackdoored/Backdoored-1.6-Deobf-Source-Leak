package f.b.o.g.client;

import f.b.o.g.*;
import f.b.q.c.*;
import net.minecraftforge.fml.common.eventhandler.*;
import f.b.a.*;

@c$b(name = "MsgOnToggle", description = "Sends message to chat on module toggle", category = f.b.o.c.c.CLIENT, defaultOn = true)
public class MsgOnToggle extends c
{
    public MsgOnToggle() {
        super();
    }
    
    @SubscribeEvent
    public void a(final r v) {
        if (this.bu() && !v.go.lq.equalsIgnoreCase("clickgui")) {
            o.i(v.go.lq + " was enabled", "red");
        }
    }
    
    @SubscribeEvent
    public void a(final u v) {
        if (this.bu() && !v.gz.lq.equalsIgnoreCase("clickgui")) {
            o.i(v.gz.lq + " was disabled", "red");
        }
    }
}
