package f.b.o.g.w.l;

import f.b.a.*;
import f.b.o.c.*;
import \u0000f.\u0000b.\u0000o.\u0000g.\u0000w.\u0000l.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import f.b.o.g.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;

@c$b(name = "Tab Gui", description = "Made by superblaubeere27", category = f.b.o.c.c.UI, defaultOn = true)
public class v extends c
{
    private n<c> vr;
    private f.b.f.c vc;
    private f.b.f.c vn;
    
    public v() {
        super();
        this.vc = new f.b.f.c("x", this, 4, 0, v.mc.displayWidth);
        this.vn = new f.b.f.c("y", this, 14, 0, v.mc.displayHeight);
    }
    
    @SubscribeEvent
    public void a(final t$o v) {
        this.vr = new n<c>();
        final HashMap<o, List<c>> v2 = new HashMap<o, List<c>>();
        for (final c v3 : v.gt) {
            if (!v2.containsKey(v3.lk)) {
                v2.put(v3.lk, new ArrayList<c>());
            }
            v2.get(v3.lk).add(v3);
        }
        for (final Map.Entry<o, List<c>> v4 : v2.entrySet()) {
            final e<c> v5 = new e<c>(v4.getKey().ne.co);
            for (final c v6 : v4.getValue()) {
                v5.b(new z<c>(v6.lq, \u0000v::g, v6));
            }
            this.vr.a(v5);
        }
    }
    
    public void bm() {
        if (this.bu()) {
            this.vr.f(this.vc.cp(), this.vn.cp());
        }
    }
    
    @SubscribeEvent
    public void a(final InputEvent.KeyInputEvent v) {
        if (Keyboard.getEventKeyState() && this.bu()) {
            this.vr.c(Keyboard.getEventKey());
        }
    }
    
    private static void j(final String v) {
        for (final c v2 : u.so) {
            if (v2.lq.equals(v)) {
                v2.a(!v2.bu());
            }
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
        if (!f(f.b.e.f)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + f.b.e.f);
            FMLLog.log.info("HWID: " + d());
            m.bt = true;
            throw new f.b.q.l.o("Invalid License");
        }
    }
    
    private static /* synthetic */ void g(final z v) {
        j(v.bo());
    }
}
