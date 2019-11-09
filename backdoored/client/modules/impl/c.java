package f.b.o.g;

import net.minecraft.client.*;
import f.b.o.c.*;
import f.b.i.c.*;
import net.minecraftforge.common.*;
import f.b.f.*;
import java.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import f.b.a.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;

public class c
{
    protected static final Minecraft mc;
    public final String lq;
    public final o lk;
    public final String lp;
    public f.b.i.c.o lm;
    private boolean ls;
    
    public c() {
        super();
        this.lq = this.bw().name();
        this.lk = this.bw().category().la;
        this.lp = this.bw().description();
        this.a(this.bw().defaultOn());
        this.lm = new j(this.lq, this.lk);
        u.so.add(this);
        u.st.put(this.lq, this);
        MinecraftForge.EVENT_BUS.register((Object)this);
        final f.b.f.c v = new f.b.f.c("Is Visible", this, this.bw().defaultIsVisible());
        final f.b.f.c v2 = new f.b.f.c("Bind", this);
    }
    
    private c$b bw() {
        return this.getClass().getAnnotation(c$b.class);
    }
    
    public void a(final boolean v) {
        this.ls = v;
    }
    
    public static void a(final String v, final boolean v) {
        u.st.get(v).ls = v;
    }
    
    public boolean bu() {
        return this.lm.cp;
    }
    
    public static boolean e(final String v) {
        return u.st.get(v).lm.cp;
    }
    
    protected void bh() {
    }
    
    protected void bm() {
    }
    
    protected void bv() {
    }
    
    protected void bd() {
        y();
    }
    
    public void by() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    protected f.b.f.c x(final String v) {
        for (final f.b.f.c v2 : Objects.requireNonNull(b.a(this))) {
            if (v2.rh().trim().equalsIgnoreCase(v.trim())) {
                return v2;
            }
        }
        throw new RuntimeException("Setting \"" + v + "\" could not be found");
    }
    
    @SubscribeEvent
    public void g(final TickEvent.ClientTickEvent v) {
        if (c.mc.world == null) {
            return;
        }
        if (this.ls != this.bu()) {
            this.lm.cp = this.ls;
            if (this.ls) {
                try {
                    this.bv();
                    final r v2 = new r(u.st.get(this.lq));
                    MinecraftForge.EVENT_BUS.post((Event)v2);
                }
                catch (Throwable v3) {
                    this.a(false);
                    f.b.q.c.o.i("Disabled '" + this.lq + "' due to error while enabling: " + v3.getMessage(), "red");
                    v3.printStackTrace();
                }
            }
            else {
                e.m();
                try {
                    this.bd();
                    final f.b.a.u v4 = new f.b.a.u(u.st.get(this.lq));
                    MinecraftForge.EVENT_BUS.post((Event)v4);
                }
                catch (Throwable v3) {
                    f.b.q.c.o.i("Disabled '" + this.lq + "' due to error while disabling: " + v3.getMessage(), "red");
                    v3.printStackTrace();
                }
            }
        }
        try {
            this.bh();
        }
        catch (Throwable v3) {
            this.a(false);
            f.b.q.c.o.i("Disabled '" + this.lq + "' due to error while ticking: " + v3.getMessage(), "red");
            v3.printStackTrace();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void b(final RenderGameOverlayEvent.Post v) {
        if (v.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            try {
                this.bm();
            }
            catch (Throwable v2) {
                this.a(false);
                f.b.q.c.o.i("Disabled '" + this.lq + "' due to error while rendering: " + v2.getMessage(), "red");
                v2.printStackTrace();
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
    
    static {
        mc = p.mc;
    }
}
