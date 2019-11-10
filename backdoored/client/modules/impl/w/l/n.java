package f.b.o.g.w.l;

import java.awt.*;
import org.lwjgl.opengl.*;
import f.b.j.*;
import f.b.q.i.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class n<T>
{
    private List<e<T>> hu;
    static final int hh = 3;
    static Color hv;
    static Color hd;
    static Color hy;
    static Color hj;
    static Color va;
    private int vb;
    private int vg;
    
    public n() {
        super();
        this.hu = new ArrayList<e<T>>();
        this.vb = 0;
        this.vg = -1;
    }
    
    public void a(final e<T> v) {
        this.hu.add(v);
    }
    
    public void f(final int v, final int v) {
        GL11.glTranslated((double)v, (double)v, 0.0);
        final FontRenderer v2 = g.fontRenderer;
        final int v3 = (v2.FONT_HEIGHT + 3) * this.hu.size();
        int v4 = 0;
        for (final e<T> v5 : this.hu) {
            if (v2.getStringWidth(v5.bo()) > v4) {
                v4 = v2.getStringWidth(v5.bo());
            }
        }
        v4 += 4;
        j.a(7, 0, 0, v4, v3, n.hv.getRGB());
        GL11.glLineWidth(1.0f);
        j.a(2, 0, 0, v4, v3, n.hd.getRGB());
        int v6 = 2;
        int v7 = 0;
        for (final e<T> v8 : this.hu) {
            if (this.vb == v7) {
                j.a(7, -1, v6 - 2, v4, v6 + v2.FONT_HEIGHT + 3 - 1, n.hy.getRGB());
                if (this.vg != -1) {
                    v8.b(v4, v6 - 2, this.vg);
                }
            }
            v2.drawString(v8.bo(), 2, v6, n.va.getRGB());
            v6 += v2.FONT_HEIGHT + 3;
            ++v7;
        }
        GL11.glTranslated((double)(-v), (double)(-v), 0.0);
    }
    
    public void c(final int v) {
        if (v == 208) {
            if (this.vg == -1) {
                ++this.vb;
                if (this.vb >= this.hu.size()) {
                    this.vb = 0;
                }
            }
            else {
                ++this.vg;
                if (this.vg >= this.hu.get(this.vb).ru().size()) {
                    this.vg = 0;
                }
            }
        }
        else if (v == 200) {
            if (this.vg == -1) {
                --this.vb;
                if (this.vb < 0) {
                    this.vb = this.hu.size() - 1;
                }
            }
            else {
                --this.vg;
                if (this.vg < 0) {
                    this.vg = this.hu.get(this.vb).ru().size() - 1;
                }
            }
        }
        else if (v == 203) {
            this.vg = -1;
        }
        else if (this.vg == -1 && (v == 28 || v == 205)) {
            this.vg = 0;
        }
        else if (v == 28 || v == 205) {
            this.hu.get(this.vb).ru().get(this.vg).rw();
        }
    }
    
    static {
        n.hv = new Color(0, 0, 0, 175);
        n.hd = new Color(0, 0, 0, 255);
        n.hy = new Color(38, 164, 78, 200);
        n.hj = new Color(164, 56, 55, 200);
        n.va = Color.white;
    }
}
