package f.b.o.g.misc;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Auto Reply", description = "Tell those scrubs whos boss", category = f.b.o.c.c.MISC)
public class AutoReply extends c
{
    private final String sv = "Ebic/autoreplies.txt";
    private String[] sd;
    private f.b.f.c sy;
    
    public AutoReply() {
        super();
        this.sd = new String[0];
        this.sy = new f.b.f.c("Text", this, "Shut up scrub", new String[] { "Shut up scrub" });
    }
    
    @SubscribeEvent
    public void g(final ClientChatReceivedEvent v) {
        if (!this.bu() || !v.getType().equals((Object)ChatType.CHAT)) {
            return;
        }
        System.out.println("Message recieved: " + v.getMessage().getUnformattedText());
        final String[] v2 = v.getMessage().getUnformattedText().split(" whispers: ");
        AutoReply.mc.player.sendChatMessage("/r " + v2[0] + " " + this.sy.ci());
    }
}
