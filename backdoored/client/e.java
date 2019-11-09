package f.b;

import net.minecraftforge.fml.common.*;
import java.io.*;
import f.b.q.l.*;
import net.minecraftforge.fml.common.event.*;
import f.b.q.x.*;
import net.minecraftforge.common.*;
import f.b.w.*;
import \u0000f.\u0000b.*;
import org.lwjgl.opengl.*;
import f.b.i.x.*;
import f.b.o.g.x.*;
import f.b.o.g.a.*;
import f.b.o.g.w.d.*;
import f.b.o.g.t.*;
import net.minecraft.world.border.*;
import java.util.*;
import f.b.c.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import f.b.s.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.play.server.*;
import f.b.a.*;
import \u0000f.\u0000b.\u0000o.\u0000g.*;

@Mod(modid = "backdoored", version = "1.6", clientSideOnly = true)
public class e
{
    public static final String l = "backdoored";
    public static final String i = "1.6";
    public static String f;
    private static Boolean q;
    public static String k;
    
    public e() {
        super();
    }
    
    public static boolean p() {
        if (e.q != null) {
            return e.q;
        }
        try {
            final BufferedReader v = new BufferedReader(new FileReader("Backdoored/options.txt"));
            String v2;
            while ((v2 = v.readLine()) != null) {
                if (v2.equals("dev.enable.debugger")) {
                    return true;
                }
            }
            v.close();
            return false;
        }
        catch (Exception v3) {
            return false;
        }
    }
    
    @Mod.EventHandler
    public void a(final FMLPreInitializationEvent v) {
        FMLLog.log.info("\n$$$$$$$\\                      $$\\             $$\\                                               $$\\ \n$$  __$$\\                     $$ |            $$ |                                              $$ |\n$$ |  $$ | $$$$$$\\   $$$$$$$\\ $$ |  $$\\  $$$$$$$ | $$$$$$\\   $$$$$$\\   $$$$$$\\   $$$$$$\\   $$$$$$$ |\n$$$$$$$\\ | \\____$$\\ $$  _____|$$ | $$  |$$  __$$ |$$  __$$\\ $$  __$$\\ $$  __$$\\ $$  __$$\\ $$  __$$ |\n$$  __$$\\  $$$$$$$ |$$ /      $$$$$$  / $$ /  $$ |$$ /  $$ |$$ /  $$ |$$ |  \\__|$$$$$$$$ |$$ /  $$ |\n$$ |  $$ |$$  __$$ |$$ |      $$  _$$<  $$ |  $$ |$$ |  $$ |$$ |  $$ |$$ |      $$   ____|$$ |  $$ |\n$$$$$$$  |\\$$$$$$$ |\\$$$$$$$\\ $$ | \\$$\\ \\$$$$$$$ |\\$$$$$$  |\\$$$$$$  |$$ |      \\$$$$$$$\\ \\$$$$$$$ |\n\\_______/  \\_______| \\_______|\\__|  \\__| \\_______| \\______/  \\______/ \\__|       \\_______| \\_______|\n");
        FMLLog.log.info("Loading backdoored...");
        r.bc();
        final File v2 = new File("Backdoored");
        if (!v2.exists()) {
            v2.mkdir();
        }
        if (!m.bf()) {
            throw new o("Couldnt load license, invalid drm");
        }
    }
    
    @Mod.EventHandler
    public void a(final FMLInitializationEvent v) {
        c.nl();
        MinecraftForge.EVENT_BUS.register((Object)new b());
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.s();
        this.e();
        this.x();
        f.b.w.c.j();
        Runtime.getRuntime().addShutdownHook(new Thread(\u0000e::z));
        new f.b.q.x.o();
        m();
    }
    
    public static void m() {
        Display.setTitle("Backdoored 1.6");
    }
    
    private void s() {
        MinecraftForge.EVENT_BUS.register((Object)new f.b.i.x.c());
        MinecraftForge.EVENT_BUS.register((Object)new f.b.i.x.o());
        MinecraftForge.EVENT_BUS.register((Object)new a());
    }
    
    private void e() {
        MinecraftForge.EVENT_BUS.post((Event)new t$c());
        final Class[] v = { f.b.o.g.j.b.class, f.b.o.g.t.b.class, f.b.o.g.x.b.class, f.b.o.g.a.b.class, f.b.o.g.b.b.class, f.b.o.g.d.b.class, f.b.o.g.x.e.class, u.class, f.class, f.b.o.g.g.b.class, f.b.o.g.x.m.class, i.class, p.class, f.b.o.g.t.e.class, f.b.o.g.a.m.class, f.b.o.g.a.e.class, f.b.o.g.a.u.class, f.b.o.g.y.b.class, d.class, l.class, f.b.o.g.a.f.class, f.b.o.g.g.m.class, f.b.o.g.j.e.class, f.b.o.g.a.i.class, f.b.o.g.t.u.class, f.b.o.g.a.p.class, f.b.o.g.b.m.class, f.b.o.g.t.i.class, f.b.o.g.w.e.class, f.b.o.g.d.m.class, f.b.o.g.a.l.class, f.b.o.g.g.e.class, f.b.o.g.y.m.class, k.class, f.b.o.g.d.e.class, f.b.o.g.d.f.class, f.b.o.g.t.p.class, f.b.o.g.t.d.class, f.b.o.g.y.e.class, f.b.o.g.t.l.class, f.b.o.g.b.u.class, f.b.o.g.d.i.class, f.b.o.g.g.f.class, f.b.o.g.g.i.class, f.b.o.g.y.u.class, f.b.o.g.d.p.class, f.b.o.g.d.d.class, f.b.o.g.b.f.class, f.b.o.g.x.r.class, f.b.o.g.b.i.class, n.class, f.b.o.g.a.r.class, f.b.o.g.t.n.class, g.class, f.b.o.g.t.r.class, w.class, f.b.o.g.w.i.n.class, f.b.o.g.t.g.class, f.b.o.g.a.n.class, f.b.o.g.a.g.class, f.b.o.g.x.a.class, f.b.o.g.b.p.class, f.b.o.g.a.w.class, f.b.o.g.t.w.class, f.b.o.g.b.l.class, f.b.o.g.b.d.class, f.b.o.g.b.k.class, f.b.o.g.b.r.class, f.b.o.g.d.k.class, f.b.o.g.y.f.class, f.b.o.g.a.a.class, f.b.o.g.d.r.class, f.b.o.g.y.i.class, f.b.o.g.d.n.class, f.b.o.g.y.p.class, f.b.o.g.d.w.class, f.b.o.g.y.d.class, f.b.o.g.t.a.class, f.b.o.g.d.g.class, f.b.o.g.b.n.class, f.b.o.g.g.p.class, q.class, h.class, f.b.o.g.d.a.class, f.b.o.g.d.h.class, f.b.o.g.a.h.class, v.class, f.b.o.g.g.d.class, f.b.o.g.d.v.class, f.b.o.g.t.v.class, f.b.o.g.y.l.class, f.b.o.g.x.q.class, f.b.o.g.x.h.class, z.class, f.b.o.g.w.k.class, s.class, f.b.o.g.d.s.class, f.b.o.g.g.l.class, f.b.o.g.x.v.class, WorldBorder.class, f.b.o.g.w.f.z.class, f.b.o.g.w.b.class, f.b.o.g.w.l.v.class };
        final Set<Class> v2 = new f.b.g.b.c().a(v).g().r();
        FMLLog.log.info("Backdoored tried to load " + v.length + " hack, out of which " + v2.size() + " failed");
        FMLLog.log.info("Failed hack: " + v2.toString());
        MinecraftForge.EVENT_BUS.post((Event)new t$o(f.b.o.g.u.so));
        FMLLog.log.info("Backdoored startup finished ");
    }
    
    private void x() {
        final Class[] v = { f.b.c.e.class, f.b.c.l.class, f.b.c.o.class, f.b.c.u.class, t.class, f.b.c.d.class, f.b.c.r.class, f.b.c.w.class, f.b.c.s.class, f.b.c.c.class, f.b.c.i.class, f.b.c.n.class, f.b.c.k.class, f.b.c.m.class, f.b.c.a.class, f.b.c.b.class };
        final Set<Class> v2 = new f.b.g.b.c().a(v).g().r();
        FMLLog.log.info("Backdoored tried to load " + v.length + " commands, out of which " + v2.size() + " failed");
        FMLLog.log.info("Failed commands: " + v2.toString());
        final f.b.c.g v3 = new f.b.c.g();
        MinecraftForge.EVENT_BUS.register((Object)v3);
    }
    
    @SubscribeEvent
    public void a(final ClientChatReceivedEvent v) {
        e.k = v.getMessage().getUnformattedText();
    }
    
    @SubscribeEvent
    public void a(final GuiOpenEvent v) {
        if (v.getGui() instanceof GuiMainMenu) {
            v.setGui((GuiScreen)new x());
        }
    }
    
    @SubscribeEvent
    public void a(final f.b.a.v v) {
        if (v.packet instanceof SPacketTimeUpdate) {
            MinecraftForge.EVENT_BUS.post((Event)new bj());
        }
    }
    
    private static /* synthetic */ void z() {
        f.b.o.g.u.so.forEach(\u0000c::by);
        c.nn();
        f.b.w.c.ba();
    }
    
    static {
        e.f = "";
        e.q = null;
        e.k = "";
    }
}
