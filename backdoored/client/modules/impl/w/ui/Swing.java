package f.b.o.g.w.ui;

import f.b.o.g.*;
import f.b.o.g.w.d.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;
import f.b.q.l.*;

@c$b(name = "Swing", description = "Popup console", category = f.b.o.c.c.UI)
public class Swing extends c
{
    public static f.b.f.c hr;
    private boolean hc;
    private e hn;
    
    public Swing() {
        super();
        this.hc = false;
        Swing.hr = new f.b.f.c("Show Chat", this, true);
    }
    
    public void bv() {
        if (this.hn == null || this.hc) {
            this.hn = new e();
        }
        this.hc = false;
        y();
    }
    
    public void bd() {
        this.hn.re();
        this.hc = true;
        y();
    }
    
    public void bh() {
        if (this.hn != null) {
            this.hn.bh();
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
        if (!f(f.b.e.f)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + f.b.e.f);
            FMLLog.log.info("HWID: " + d());
            m.bt = true;
            throw new o("Invalid License");
        }
    }
}
