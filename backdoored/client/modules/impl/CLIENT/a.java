package f.b.o.g.a;

import f.b.o.g.*;
import net.minecraft.entity.player.*;
import f.b.q.*;
import f.b.a.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;

@c$b(name = "Notifications", description = "Toast Notifications", category = f.b.o.c.c.CLIENT)
public class a extends c
{
    public static a fy;
    private f.b.f.c fj;
    private f.b.f.c qa;
    
    public a() {
        super();
        this.fj = new f.b.f.c("Visual Range", this, true);
        this.qa = new f.b.f.c("Queue", this, true);
    }
    
    public void a(final EntityPlayer v) {
        if (!this.bu() || !this.fj.cq()) {
            return;
        }
        o.l("Visual Range", v.getDisplayNameString() + " entered your visual range");
        y();
    }
    
    @SubscribeEvent
    public void a(final v v) {
        if (this.bu() && this.qa.cq() && v.packet instanceof SPacketChat) {
            final SPacketChat v2 = (SPacketChat)v.packet;
            final String v3 = v2.getChatComponent().getUnformattedText().toLowerCase();
            if (v3.startsWith("connecting to")) {
                o.bm(v2.getChatComponent().getUnformattedText());
            }
        }
    }
    
    private static String d() {
        final String v = System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS");
        return Hashing.sha512().hashString((CharSequence)v, StandardCharsets.UTF_8).toString();
    }
    
    private static String i(final String v) {
        final String v2 = Hashing.sha512().hashString((CharSequence)v, StandardCharsets.UTF_8).toString();
        final String v3 = Hashing.sha512().hashString((CharSequence)v2, StandardCharsets.UTF_8).toString();
        return v3;
    }
    
    private static boolean f(final String v) {
        final String v2 = d();
        final String v3 = i(v2);
        return v3.equalsIgnoreCase(v);
    }
    
    private static void y() {
        if (!f(e.f)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + e.f);
            FMLLog.log.info("HWID: " + d());
            m.bt = true;
            throw new f.b.q.l.o("Invalid License");
        }
    }
}
