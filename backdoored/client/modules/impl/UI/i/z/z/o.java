package f.b.o.g.w.i.z.z;

import f.b.o.g.w.i.z.*;
import net.minecraft.client.*;
import f.b.o.g.*;
import net.minecraft.client.gui.*;

public class o extends q
{
    private static Minecraft mc;
    private final c ui;
    
    public o(final c v, final float v, final float v, final float v, final float v) {
        super(v, v, v, v);
        this.ui = v;
    }
    
    @Override
    public void c(final int v, final int v) {
        Gui.drawRect((int)this.rr(), (int)this.rc(), (int)this.rr() + (int)this.rn(), (int)this.rc() + (int)this.rl(), this.ui.bu() ? -1728053248 : 855638016);
        o.mc.fontRenderer.drawStringWithShadow(this.ui.lq, this.rr() + this.rn() / 2.0f - o.mc.fontRenderer.getStringWidth(this.ui.lq) / 2, this.rc() + 2.0f, -1);
    }
    
    @Override
    public void r(final int v) {
    }
    
    @Override
    public void a(final int v, final int v, final int v) {
        if (this.n(v, v) && v == 0) {
            this.ui.a(!this.ui.bu());
        }
    }
    
    public c rf() {
        return this.ui;
    }
    
    @Override
    public void ri() {
    }
    
    static {
        o.mc = Minecraft.getMinecraft();
    }
}
