package f.b.o.g.misc;

import f.b.o.g.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.realms.*;
import net.minecraft.client.gui.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;
import f.b.q.l.*;

@c$b(name = "Disconenct", description = "Allows you to bind a key to disconnecting", category = f.b.o.c.c.MISC)
public class Disconenct extends c
{
    public Disconenct() {
        super();
    }
    
    public void bv() {
        this.a(false);
        Disconenct.mc.world.sendQuittingDisconnectingPacket();
        Disconenct.mc.loadWorld((WorldClient)null);
        if (Disconenct.mc.isIntegratedServerRunning()) {
            Disconenct.mc.displayGuiScreen((GuiScreen)new GuiMainMenu());
        }
        else if (Disconenct.mc.isConnectedToRealms()) {
            final RealmsBridge v = new RealmsBridge();
            v.switchToRealms((GuiScreen)new GuiMainMenu());
        }
        else {
            Disconenct.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()));
        }
        y();
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
            throw new o("Invalid License");
        }
    }
}
