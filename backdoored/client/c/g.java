package f.b.c;

import net.minecraftforge.client.event.*;
import f.b.q.c.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import f.b.a.*;
import net.minecraft.network.play.client.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;

public class g
{
    public static String v;
    private static g d;
    
    public g() {
        super();
    }
    
    public void v() {
        g.d = this;
    }
    
    public static void l(final String v) {
        if (v.startsWith(g.v)) {
            if (g.d == null) {
                g.d = new g();
            }
            g.d.a(new ClientChatEvent(v));
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void a(final ClientChatEvent v) {
        final String[] v2 = v.getMessage().split(" ");
        if (!v2[0].startsWith(g.v)) {
            return;
        }
        if (v2[0].startsWith(g.v)) {
            v2[0] = v2[0].replace(g.v, "");
        }
        for (final x v3 : x.y) {
            if (v3.j.contains(v2[0])) {
                y();
                final String[] v4 = new ArrayList(Arrays.asList(v2).subList(1, v2.length)).toArray(new String[v2.length - 1]);
                if (v4.length == 0) {
                    final String[] v5 = { "", "", "", "", "", "" };
                    v3.a(v5);
                    return;
                }
                v3.a(v4);
                v.setCanceled(true);
                return;
            }
        }
        o.i("Command not found! Type " + g.v + "help for a list of commands", "red");
    }
    
    @SubscribeEvent
    public void a(final q v) {
        if (v.packet instanceof CPacketChatMessage) {
            final CPacketChatMessage v2 = (CPacketChatMessage)v.packet;
            if (v2.getMessage().startsWith(g.v)) {
                v.setCanceled(true);
            }
        }
    }
    
    private void a(final x v, final String[] v) {
        if (!v.a(v)) {
            o.i("Usage:\n" + v.o(), "red");
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
    
    static {
        g.v = "-";
    }
}
