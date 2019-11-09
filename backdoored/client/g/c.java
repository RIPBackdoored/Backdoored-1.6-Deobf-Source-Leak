package f.b.g;

import f.b.q.*;
import net.minecraft.client.*;
import f.b.*;
import net.minecraftforge.fml.common.*;
import com.mojang.authlib.exceptions.*;

public class c
{
    public c() {
        super();
    }
    
    public static boolean a(final String v, final String v) throws AuthenticationException {
        System.out.println(f.b.t.c.n(13) + v);
        final s v2 = new s(v, v);
        if (v2.la()) {
            try {
                ObfuscationReflectionHelper.setPrivateValue((Class)Minecraft.class, (Object)p.mc, (Object)v2.lb(), new String[] { f.b.t.c.n(14), f.b.t.c.n(15) });
            }
            catch (Exception v3) {
                v3.printStackTrace();
                return false;
            }
            System.out.println(f.b.t.c.n(16));
            System.out.println(f.b.t.c.n(17) + p.mc.getSession().getSessionID());
            System.out.println(f.b.t.c.n(18) + a());
            return true;
        }
        return false;
    }
    
    public static String a() {
        return p.mc.getSession().getUsername();
    }
    
    public static String b() {
        return p.mc.getSession().getProfile().getId().toString();
    }
}
