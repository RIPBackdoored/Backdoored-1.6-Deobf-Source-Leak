package f.b.q.c;

import f.b.*;
import java.util.*;
import net.minecraft.util.text.*;
import net.minecraftforge.common.*;
import f.b.a.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class o implements p
{
    public o() {
        super();
    }
    
    public static void bn(final String v) {
        b(v, true);
    }
    
    public static void b(final String v, final boolean v) {
        a(v, "white", v);
    }
    
    public static void i(final String v, final String v) {
        a(v, v, true);
    }
    
    public static void a(final String v, final String v, final boolean v) {
        try {
            a(Objects.requireNonNull(ITextComponent.Serializer.jsonToComponent("{\"text\":\"" + v + "\",\"color\":\"" + v + "\"}")), v);
        }
        catch (Exception v2) {
            v2.printStackTrace();
        }
    }
    
    public static void a(final ITextComponent v) {
        a(v, true);
    }
    
    public static void a(final ITextComponent v, final boolean v) {
        try {
            ITextComponent v2;
            if (v) {
                v2 = new TextComponentString("§a[§cBD§a]§r ").appendSibling(v);
            }
            else {
                v2 = v;
            }
            o.mc.ingameGUI.addChatMessage(ChatType.SYSTEM, v2);
            MinecraftForge.EVENT_BUS.post((Event)new c(v));
        }
        catch (Exception v3) {
            v3.printStackTrace();
        }
    }
}
