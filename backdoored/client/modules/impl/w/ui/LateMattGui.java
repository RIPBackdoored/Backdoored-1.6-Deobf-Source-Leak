package f.b.o.g.w.ui;

import f.b.o.g.*;
import f.b.o.g.w.i.*;
import net.minecraft.client.gui.*;

@c$b(name = "LateMattGui", description = "Thanks latematt", category = f.b.o.c.c.UI)
public class LateMattGui extends c
{
    private static n uq;
    
    public LateMattGui() {
        super();
        LateMattGui.uq = (n)this;
    }
    
    public void bv() {
        LateMattGui.mc.displayGuiScreen((GuiScreen)new e());
    }
    
    public void bd() {
        LateMattGui.mc.displayGuiScreen((GuiScreen)null);
    }
    
    public static void ri() {
        if (LateMattGui.uq != null) {
            ((c)LateMattGui.uq).a(false);
        }
    }
}
