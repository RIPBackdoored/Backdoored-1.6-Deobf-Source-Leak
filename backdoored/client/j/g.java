package f.b.j;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import f.b.*;

public class g
{
    public static final FontRenderer fontRenderer;
    public static final Class<GlStateManager> cl;
    public static final b ci;
    
    public g() {
        super();
    }
    
    static {
        fontRenderer = p.mc.fontRenderer;
        cl = GlStateManager.class;
        ci = new b(p.mc);
    }
}
