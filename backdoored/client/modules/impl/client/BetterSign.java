package f.b.o.g.client;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.tileentity.*;
import f.b.s.*;
import net.minecraft.client.gui.*;
import f.b.q.c.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;

@c$b(name = "Better Sign", description = "Better Sign GUI", category = f.b.o.c.c.CLIENT)
public class BetterSign extends c
{
    public BetterSign() {
        super();
    }
    
    @SubscribeEvent
    public void g(final GuiOpenEvent v) {
        if (v.getGui() instanceof GuiEditSign && this.bu()) {
            try {
                final TileEntitySign v2 = (TileEntitySign)ObfuscationReflectionHelper.getPrivateValue((Class)GuiEditSign.class, (Object)v.getGui(), new String[] { "tileSign", "field_146848_f" });
                v.setGui((GuiScreen)new b(v2));
            }
            catch (Exception v3) {
                o.bn("Disabled Secret Close due to an error: " + v3.toString());
                this.a(false);
            }
            y();
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
