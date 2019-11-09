package f.b.o.g.w;

import f.b.o.g.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;

@c$b(name = "ClickGui", description = "backdoored's main gui", category = f.b.o.c.c.UI, defaultIsVisible = false)
public class b extends c
{
    public b() {
        super();
        if (this.x("Bind").ci().equals("NONE")) {
            this.x("Bind").g(Keyboard.getKeyName(39));
            f.b.f.b.a(this).get(1).dp.co = Keyboard.getKeyName(39);
        }
    }
    
    public void bv() {
        b.mc.displayGuiScreen((GuiScreen)new f.b.i.b());
        this.a(false);
    }
}
