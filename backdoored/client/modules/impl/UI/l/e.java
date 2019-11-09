package f.b.o.g.w.l;

import org.lwjgl.opengl.*;
import f.b.j.*;
import f.b.q.i.*;
import net.minecraft.client.gui.*;
import java.util.*;
import f.b.o.g.*;

public class e<T>
{
    private List<z<T>> ht;
    private String hw;
    
    public e(final String v) {
        super();
        this.ht = new ArrayList<z<T>>();
        this.hw = v;
    }
    
    public void b(final z<T> v) {
        this.ht.add(v);
    }
    
    public List<z<T>> ru() {
        return this.ht;
    }
    
    public void b(final int v, final int v, final int v) {
        GL11.glTranslated((double)v, (double)v, 0.0);
        final FontRenderer v2 = g.fontRenderer;
        final int v3 = (v2.FONT_HEIGHT + 3) * this.ht.size();
        int v4 = 0;
        for (final z<T> v5 : this.ht) {
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
        for (final z<T> v8 : this.ht) {
            if (y(v8.bo())) {
                j.a(7, 0, v6 - 2, v4, v6 + v2.FONT_HEIGHT + 3 - 1, n.hj.getRGB());
            }
            else if (v == v7) {
                j.a(7, 0, v6 - 2, v4, v6 + v2.FONT_HEIGHT + 3 - 1, n.hy.getRGB());
            }
            v2.drawString(v8.bo(), 2, v6, n.va.getRGB());
            v6 += v2.FONT_HEIGHT + 3;
            ++v7;
        }
        GL11.glTranslated((double)(-v), (double)(-v), 0.0);
    }
    
    public String bo() {
        return this.hw;
    }
    
    public void d(final String v) {
        this.hw = v;
    }
    
    private static boolean y(final String v) {
        for (final c v2 : u.so) {
            if (v2.lq.equals(v)) {
                return v2.bu();
            }
        }
        return false;
    }
}
