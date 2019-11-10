package f.b.o.g.ui;

import f.b.o.g.*;
import org.lwjgl.input.*;
import f.b.f.*;
import net.minecraft.client.gui.*;

@c$b(name = "ClickGui", description = "backdoored's main gui", category = f.b.o.c.c.UI, defaultIsVisible = false)
public class ClickGui extends c
{
    public ClickGui() {
        super();
        if (this.x("Bind").ci().equals("NONE")) {
            this.x("Bind").g(Keyboard.getKeyName(39));
            b.a(this).get(1).dp.co = Keyboard.getKeyName(39);
        }
    }
    
    public void bv() {
        ClickGui.mc.displayGuiScreen((GuiScreen)new f.b.i.b());
        this.a(false);
    }
}
