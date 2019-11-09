package f.b.o.g.x;

import f.b.o.g.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import f.b.q.c.*;
import java.util.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;

@c$b(name = "Strength Pot Detect", description = "Detect when enemies strength pot", category = f.b.o.c.c.COMBAT)
public class q extends c
{
    private Set<EntityPlayer> mi;
    
    public q() {
        super();
        this.mi = Collections.newSetFromMap(new WeakHashMap<EntityPlayer, Boolean>());
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        for (final EntityPlayer v : q.mc.world.playerEntities) {
            if (v.equals((Object)q.mc.player)) {
                continue;
            }
            if (v.isPotionActive(MobEffects.STRENGTH) && !this.mi.contains(v)) {
                o.i("Player '" + v.getDisplayNameString() + "' has strenth potted", "yellow");
                this.mi.add(v);
                y();
            }
            if (!this.mi.contains(v) || v.isPotionActive(MobEffects.STRENGTH)) {
                continue;
            }
            o.i("Player '" + v.getDisplayNameString() + "' no longer has strength", "green");
            this.mi.remove(v);
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
