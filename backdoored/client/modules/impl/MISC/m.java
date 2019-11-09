package f.b.o.g.g;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraft.network.play.client.*;
import f.b.q.c.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import f.b.*;
import net.minecraftforge.fml.common.*;

@c$b(name = "Chat Append", description = "Show off your new client", category = f.b.o.c.c.MISC, defaultOn = true)
public class m extends c
{
    public m() {
        super();
    }
    
    @SubscribeEvent
    public void a(final q v) {
        if (v.packet instanceof CPacketChatMessage && this.bu()) {
            final CPacketChatMessage v2 = (CPacketChatMessage)v.packet;
            final boolean v3 = v2.getMessage().startsWith("/") || v2.getMessage().startsWith("!");
            if (!v3) {
                final String v4 = v2.getMessage().concat(" » \u0299\u1d00\u1d04\u1d0b\u1d05\u1d0f\u1d0f\u0280\u1d07\u1d05");
                try {
                    ObfuscationReflectionHelper.setPrivateValue((Class)CPacketChatMessage.class, (Object)v2, (Object)v4, new String[] { "message", "field_149440_a" });
                }
                catch (Exception v5) {
                    o.bn("Disabled chat append due to error: " + v5.getMessage());
                    this.a(false);
                    v5.printStackTrace();
                }
                y();
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
            f.b.m.bt = true;
            throw new f.b.q.l.o("Invalid License");
        }
    }
}
