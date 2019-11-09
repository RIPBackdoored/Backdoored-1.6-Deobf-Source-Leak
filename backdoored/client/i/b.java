package f.b.i;

import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import f.b.o.c.*;
import f.b.o.g.*;
import f.b.*;
import org.lwjgl.opengl.*;
import java.util.*;
import java.io.*;

public class b extends GuiScreen
{
    private static final ResourceLocation resourceLocation;
    public static int nn;
    public static int nl;
    
    public b() {
        super();
    }
    
    public void func_73866_w_() {
        super.initGui();
        this.allowUserInput = true;
    }
    
    public void func_73863_a(final int v, final int v, final float v) {
        b.nn = v;
        b.nl = v;
        for (final o v2 : o.nx) {
            v2.ne.a(v, v);
            for (final c v3 : u.so) {
                if (v3.lk == v2 && v3.lm.cw) {
                    v3.lm.a(v, v);
                    for (final f.b.f.c v4 : f.b.f.b.a(v3)) {
                        if (v4.dp.cw) {
                            v4.dp.a(v, v);
                        }
                        if (v4.ct() && v4.dp.cw) {
                            p.mc.renderEngine.bindTexture(b.resourceLocation);
                            GL11.glPushAttrib(1048575);
                            GL11.glPushMatrix();
                            GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                            this.drawTexturedModalRect(v4.dp.cs + 1, v4.dp.ce + v4.dp.cz - 2, 0, 0, (int)((v4.dp.cx - 2) * (v4.cp() / (v4.cs() - v4.cm()))), 1);
                            GL11.glPopMatrix();
                            GL11.glPopAttrib();
                        }
                    }
                }
            }
        }
        this.b(v, v);
        super.drawScreen(v, v, v);
    }
    
    private void b(final int v, final int v) {
        final f.b.i.c.o v2 = g.g(v, v);
        if (v2 != null) {
            final c v3 = g.a(v2);
            if (v3 != null) {
                p.mc.renderEngine.bindTexture(new ResourceLocation("backdoored", "textures/white.png"));
                GL11.glPushAttrib(1048575);
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                GL11.glColor4f(255.0f, 255.0f, 255.0f, 1.0f);
                final int v4 = this.fontRenderer.getStringWidth(v3.lp) + 1;
                final int v5 = this.fontRenderer.FONT_HEIGHT + 1;
                this.drawTexturedModalRect(v + 5, v + 5, v4, v5, v4, v5);
                GL11.glPopMatrix();
                GL11.glPopAttrib();
                this.fontRenderer.drawString(v3.lp, v + 6, v + 6, 0);
            }
        }
    }
    
    protected void func_73864_a(final int v, final int v, final int v) throws IOException {
        for (final f.b.i.c.o v2 : g.bp()) {
            if (!v2.cw) {
                continue;
            }
            if (v >= v2.cs && v <= v2.cs + v2.cx && v >= v2.ce && v <= v2.ce + v2.cz && v == 0) {
                for (final c v3 : u.so) {
                    if (v3.lq.equals(v2.co)) {
                        v3.a(!v3.bu());
                        return;
                    }
                }
                v2.cp = !v2.cp;
                return;
            }
            if (v >= v2.cs && v <= v2.cs + v2.cx && v >= v2.ce && v <= v2.ce + v2.cz && v == 1) {
                v2.cm = !v2.cm;
                return;
            }
        }
        super.mouseClicked(v, v, v);
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    static {
        resourceLocation = new ResourceLocation("backdoored", "textures/white.png");
        b.nn = 0;
        b.nl = 0;
    }
}
