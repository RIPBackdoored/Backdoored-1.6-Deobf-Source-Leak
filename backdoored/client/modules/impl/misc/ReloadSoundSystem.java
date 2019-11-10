package f.b.o.g.misc;

import f.b.o.g.*;
import net.minecraft.client.audio.*;
import f.b.q.c.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;

@c$b(name = "ReloadSoundSystem", description = "Reloads the sound system", category = f.b.o.c.c.MISC)
public class ReloadSoundSystem extends c
{
    public ReloadSoundSystem() {
        super();
    }
    
    public void bv() {
        try {
            final SoundManager v = (SoundManager)ObfuscationReflectionHelper.getPrivateValue((Class)SoundHandler.class, (Object)ReloadSoundSystem.mc.getSoundHandler(), new String[] { "sndManager", "field_147694_f" });
            v.reloadSoundSystem();
        }
        catch (Exception v2) {
            System.out.println("Could not restart sound manager: " + v2.toString());
            v2.printStackTrace();
            o.i("Could not restart sound manager: " + v2.toString(), "red");
        }
        this.a(false);
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
            throw new f.b.q.l.o("Invalid License");
        }
    }
}
