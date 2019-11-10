package f.b.o.g.client;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import java.util.regex.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.eventhandler.*;
import f.b.o.g.j.*;
import org.apache.logging.log4j.*;
import f.b.q.c.*;
import java.util.*;

@c$b(name = "Chat Filter", description = "Filter your chat", category = f.b.o.c.c.CLIENT)
public class ChatFilter extends c
{
    private f.b.f.c ih;
    private f.b.f.c iv;
    private f.b.f.c id;
    private f iy;
    
    public ChatFilter() {
        super();
        this.ih = new f.b.f.c("Allow Whispers", this, true);
        this.iv = new f.b.f.c("Allow Mentions", this, true);
        this.id = new f.b.f.c("Allow Game Info", this, true);
    }
    
    @SubscribeEvent
    public void b(final ClientChatReceivedEvent v) {
        if (this.bu()) {
            v.setCanceled(true);
            final String v2 = v.getMessage().getUnformattedText().toLowerCase();
            if (this.ih.cq()) {
                final String[] v3 = v2.split(Pattern.quote(" "));
                if (v3.length >= 3 && v3[1].equals("whispers:")) {
                    v.setCanceled(false);
                }
            }
            if (this.iv.cq() && v2.contains(ChatFilter.mc.player.getName().toLowerCase())) {
                v.setCanceled(false);
            }
            if (this.id.cq() && v.getType() == ChatType.GAME_INFO) {
                v.setCanceled(false);
            }
            if (!v.isCanceled()) {
                v.setCanceled(this.h(v.getMessage().getUnformattedText()));
            }
        }
    }
    
    public boolean h(final String v) {
        if (this.iy == null) {
            try {
                this.iy = new f().u(u.w("Backdoored/chatfilter.js")).a(LogManager.getLogger("BackdooredChatFilter"));
            }
            catch (Exception v2) {
                this.a(false);
                o.i("Failed to initialise Chat Filter script: " + v2.getMessage(), "red");
                v2.printStackTrace();
                return false;
            }
        }
        try {
            final Object v3 = this.iy.b("isExcluded", v);
            return Objects.requireNonNull(v3);
        }
        catch (Exception v2) {
            v2.printStackTrace();
            return false;
        }
    }
}
