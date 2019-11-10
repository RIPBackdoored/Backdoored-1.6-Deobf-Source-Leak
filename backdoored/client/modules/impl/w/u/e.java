package f.b.o.g.w.u;

import net.minecraft.client.gui.*;
import f.b.*;
import java.awt.*;

public class e extends Gui
{
    private static ScaledResolution scaledResolution;
    
    public e() {
        super();
    }
    
    public static void rg() {
        e.scaledResolution = new ScaledResolution(p.mc);
        final int v = e.scaledResolution.getScaleFactor();
        drawRect(50 / v, 50 / v, 50 / v, 50 / v, Color.BLACK.getRGB());
    }
}
