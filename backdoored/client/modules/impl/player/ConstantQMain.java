package f.b.o.g.player;

import f.b.o.g.*;
import f.b.q.c.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;

@c$b(name = "ConstantQMain", description = "Does \"/queue main\" once a minute to help you get through the 2b2t queue", category = f.b.o.c.c.PLAYER)
public class ConstantQMain extends c
{
    private static long xl;
    private f.b.f.c xi;
    
    public ConstantQMain() {
        super();
        this.xi = new f.b.f.c("Only in end", this, true);
    }
    
    public void bh() {
        if (System.currentTimeMillis() >= ConstantQMain.xl + 30000L && this.bu()) {
            if (ConstantQMain.mc.player == null) {
                return;
            }
            if (!this.xi.cq() || (ConstantQMain.mc.player.dimension != -1 && ConstantQMain.mc.player.dimension != 0)) {
                ConstantQMain.xl = System.currentTimeMillis();
                ConstantQMain.mc.player.sendChatMessage("/queue main");
                o.bn("/queue main");
                y();
            }
        }
    }
    
    public void bd() {
        ConstantQMain.xl = 0L;
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
        ConstantQMain.xl = 0L;
    }
}
