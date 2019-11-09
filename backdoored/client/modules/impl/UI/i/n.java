package f.b.o.g.w.i;

import f.b.o.g.*;
import net.minecraft.client.gui.*;

@c$b(name = "LateMattGui", description = "Thanks latematt", category = f.b.o.c.c.UI)
public class n extends c
{
    private static n uq;
    
    public n() {
        super();
        n.uq = this;
    }
    
    public void bv() {
        n.mc.displayGuiScreen((GuiScreen)new e());
    }
    
    public void bd() {
        n.mc.displayGuiScreen((GuiScreen)null);
    }
    
    public static void ri() {
        if (n.uq != null) {
            n.uq.a(false);
        }
    }
}
