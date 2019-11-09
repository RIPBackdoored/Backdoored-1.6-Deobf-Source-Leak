package f.b.o.g.x;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.client.gui.*;
import f.b.q.c.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Anti Death Screen", description = "Prevents the death screen from incorrectly coming up during combat", category = f.b.o.c.c.COMBAT)
public class b extends c
{
    public static b qx;
    
    public b() {
        super();
        b.qx = this;
    }
    
    @SubscribeEvent
    public void g(final GuiOpenEvent v) {
        if (v.getGui() instanceof GuiGameOver) {
            try {
                final ITextComponent v2 = (ITextComponent)ObfuscationReflectionHelper.getPrivateValue((Class)GuiGameOver.class, (Object)v.getGui(), new String[] { "causeOfDeath", "field_184871_f" });
                v.setGui((GuiScreen)new f.b.s.c(v2));
            }
            catch (Exception v3) {
                o.bn("Disabled Anti Death Screen due to an error: " + v3.toString());
                v3.printStackTrace();
                this.a(false);
            }
        }
    }
}
