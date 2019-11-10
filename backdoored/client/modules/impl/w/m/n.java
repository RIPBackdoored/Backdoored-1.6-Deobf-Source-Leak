package f.b.o.g.w.m;

import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.math.*;
import java.io.*;
import f.b.o.g.*;
import org.lwjgl.opengl.*;
import f.b.o.c.*;
import f.b.q.i.*;
import java.util.*;
import f.b.*;

public class n extends GuiScreen
{
    public static final Color wg;
    public static final Color wr;
    public static final Color wc;
    public static final Color wn;
    private c wl;
    private static Minecraft mc;
    public static ScaledResolution scaledResolution;
    private Vec2f vec2f;
    
    n(final c v) {
        super();
        this.vec2f = null;
        this.wl = v;
    }
    
    protected void func_73864_a(final int v, final int v, final int v) throws IOException {
        if (v == 0) {
            this.vec2f = new Vec2f((float)v, (float)v);
        }
    }
    
    public void func_73863_a(final int v, final int v, final float v) {
        final HashMap<o, List<c>> v2 = u.gs();
        GL11.glLineWidth(1.0f);
        int v3 = 0;
        for (final o v4 : v2.keySet()) {
            int v5 = n.mc.fontRenderer.getStringWidth(v4.ne.co) + 4;
            if (v5 > v3) {
                v3 = v5;
            }
            for (final c v6 : v2.get(v4)) {
                v5 = n.mc.fontRenderer.getStringWidth(v6.lq) + 4;
                if (v5 > v3) {
                    v3 = v5;
                }
            }
        }
        for (final Map.Entry<o, List<c>> v7 : v2.entrySet()) {
            final o v8 = v7.getKey();
            final int v9 = v8.ne.cs;
            final int v10 = v8.ne.ce;
            j.a(7, v9, v10, v3 + 6 + v9, n.mc.fontRenderer.FONT_HEIGHT + 6 + v10, n.wg.getRGB());
            j.a(2, v9, v10, v3 + 6 + v9, n.mc.fontRenderer.FONT_HEIGHT + 6 + v10, n.wn.getRGB());
            n.mc.fontRenderer.drawString(v7.getKey().ne.co, v9 + 3, v10 + 3, Color.WHITE.getRGB());
            int v11 = 6 + n.mc.fontRenderer.FONT_HEIGHT;
            if (!v8.ne.cw) {
                return;
            }
            for (final c v12 : v7.getValue()) {
                final int v13 = v10 + v11;
                Color v14 = n.wr;
                if (v12.bu()) {
                    v14 = n.wc;
                }
                j.a(7, v9, v13, v3 + 6 + v9, n.mc.fontRenderer.FONT_HEIGHT + 6 + v13, v14.getRGB());
                j.a(2, v9, v13, v3 + 6 + v9, n.mc.fontRenderer.FONT_HEIGHT + 6 + v13, n.wn.getRGB());
                n.mc.fontRenderer.drawString(v12.lq, v9 + 3, v13 + 3, Color.WHITE.getRGB());
                if (this.vec2f != null && this.vec2f.x > v9 && this.vec2f.x < v3 + 6 + v9 && this.vec2f.y > v13 && this.vec2f.y < n.mc.fontRenderer.FONT_HEIGHT + 6 + v13) {
                    v12.a(!v12.bu());
                    this.vec2f = null;
                }
                v11 += 6 + n.mc.fontRenderer.FONT_HEIGHT;
            }
        }
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    public void func_146281_b() {
        this.wl.a(false);
    }
    
    static {
        wg = new Color(164, 56, 55);
        wr = new Color(128, 128, 128, 127);
        wc = new Color(38, 164, 78, 255);
        wn = new Color(0, 0, 0, 255);
        n.mc = p.mc;
        n.scaledResolution = new ScaledResolution(n.mc);
    }
}
