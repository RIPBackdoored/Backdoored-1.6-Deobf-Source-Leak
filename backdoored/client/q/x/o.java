package f.b.q.x;

import java.util.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import java.net.*;
import java.io.*;
import f.b.a.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class o
{
    private static HashMap<String, o$m> yp;
    private static ResourceLocation resourceLocation;
    private static ResourceLocation resourceLocation;
    private static ResourceLocation resourceLocation;
    
    public o() {
        super();
        MinecraftForge.EVENT_BUS.register((Object)this);
        o.yp = new HashMap<String, o$m>();
        this.ng();
        this.nr();
        this.nc();
    }
    
    private boolean ng() {
        return this.a(o.yp, "http://pastebin.com/raw/g4wjzg5U", o$m.yi);
    }
    
    private boolean nr() {
        return this.a(o.yp, "http://pastebin.com/raw/ZMZcF3nJ", o$m.yf);
    }
    
    private boolean nc() {
        return this.a(o.yp, "http://pastebin.com/raw/drFrFW5r", o$m.yq);
    }
    
    private boolean a(final HashMap<String, o$m> v, final String v, final o$m v) {
        try {
            final URL v2 = new URL(v);
            final BufferedReader v3 = new BufferedReader(new InputStreamReader(v2.openStream()));
            String v4;
            while ((v4 = v3.readLine()) != null) {
                if (!v4.trim().isEmpty()) {
                    v.put(v4.trim(), v);
                }
            }
            v3.close();
            System.out.println("Gave " + v.name() + " capes to: " + v.toString());
            return true;
        }
        catch (Exception v5) {
            v5.printStackTrace();
            return false;
        }
    }
    
    public static o$m bf(final String v) {
        if (o.yp == null) {
            new o();
        }
        return o.yp.getOrDefault(v, o$m.yk);
    }
    
    @SubscribeEvent
    public void a(final e v) {
        switch (o$b.yl[bf(v.networkPlayerInfo.getGameProfile().getName()).ordinal()]) {
            case 1: {
                v.resourceLocation = o.resourceLocation;
                break;
            }
            case 2: {
                v.resourceLocation = o.resourceLocation;
                break;
            }
            case 3: {
                v.resourceLocation = o.resourceLocation;
                break;
            }
        }
    }
    
    static {
        o.yp = null;
        o.resourceLocation = new ResourceLocation("backdoored", "textures/cape_backdoored_dev.png");
        o.resourceLocation = new ResourceLocation("backdoored", "textures/cape_backdoored.png");
        o.resourceLocation = new ResourceLocation("backdoored", "textures/popbob.png");
    }
}
