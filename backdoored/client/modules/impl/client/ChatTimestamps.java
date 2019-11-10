package f.b.o.g.client;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import f.b.q.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.text.*;
import java.util.*;

@c$b(name = "Chat Timestamps", description = "Timestamps on chat messages", category = f.b.o.c.c.CLIENT)
public class ChatTimestamps extends c
{
    private f.b.f.c ij;
    private f.b.f.c fa;
    private f.b.f.c fb;
    private f.b.f.c fg;
    private f.b.f.c fr;
    
    public ChatTimestamps() {
        super();
        this.ij = new f.b.f.c("Seconds", this, false);
        this.fa = new f.b.f.c("Milliseconds", this, false);
        this.fb = new f.b.f.c("Hour format", this, "12", new String[] { "12", "24" });
        this.fg = new f.b.f.c("Colour", this, "Light Purple", new String[] { "Dark Red", "Red", "Gold", "Yellow", "Dark Green", "Green", "Aqua", "Dark Aqua", "Dark Blue", "Blue", "Light Purple", "Dark Purple", "White", "Gray", "Dark Gray", "Black" });
        this.fr = new f.b.f.c("Brackets", this, true);
    }
    
    @SubscribeEvent
    public void g(final ClientChatReceivedEvent v) {
        if (this.bu()) {
            final String v2 = b.bi(this.fg.ci());
            final String[] v3 = this.fr.cq() ? new String[] { "<", ">" } : new String[] { "", "" };
            final String v4 = v2 + v3[0] + this.gn() + v3[1] + "§r " + v.getMessage().getFormattedText();
            v.setMessage((ITextComponent)new TextComponentString(v4));
        }
    }
    
    private String gn() {
        final StringBuilder v = new StringBuilder();
        if (this.fb.ci().equals("12")) {
            v.append("hh");
        }
        else {
            v.append("HH");
        }
        v.append(":mm");
        if (this.ij.cq()) {
            v.append(":ss");
        }
        if (this.fa.cq()) {
            v.append(".SSS");
        }
        SimpleDateFormat v2;
        if (this.fa.cq()) {
            v2 = new SimpleDateFormat(v.toString());
        }
        else {
            v2 = new SimpleDateFormat(v.toString());
        }
        return v2.format(new Date());
    }
}
