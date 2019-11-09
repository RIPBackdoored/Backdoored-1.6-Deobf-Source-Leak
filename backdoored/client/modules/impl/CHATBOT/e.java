package f.b.o.g.j;

import f.b.o.g.*;
import f.b.q.c.*;
import f.b.a.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Chat Bot", description = "Scriptable chat bot", category = f.b.o.c.c.CHATBOT)
public class e extends c
{
    private u in;
    private long il;
    
    public e() {
        super();
        this.il = 0L;
    }
    
    public void bv() {
        try {
            this.in = new u();
        }
        catch (Exception v) {
            this.a(false);
            o.i("Failed to initialise chatbot script: " + v.getMessage(), "red");
            v.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void b(final v v) {
        if (this.bu() && v.packet instanceof SPacketChat && System.currentTimeMillis() - this.il > 5000L) {
            final SPacketChat v2 = (SPacketChat)v.packet;
            this.c(v2.getChatComponent().getUnformattedText(), v2.getType().name());
            this.il = System.currentTimeMillis();
        }
    }
    
    private void c(final String v, final String v) {
        if (e.mc.player == null || v.startsWith("<" + e.mc.player.getName()) || v.startsWith("<" + e.mc.player.getDisplayNameString())) {
            return;
        }
        try {
            if (this.in == null) {
                this.in = new u();
            }
            final String v2 = this.in.n(v, v);
            if (v2 != null) {
                e.mc.player.sendChatMessage(v2);
            }
        }
        catch (Exception v3) {
            this.a(false);
            o.i("Failure while invoking chatbot script: " + v3.getMessage(), "red");
            v3.printStackTrace();
        }
    }
}
