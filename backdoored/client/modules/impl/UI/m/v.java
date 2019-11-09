package f.b.o.g.w.m;

import f.b.o.c.*;
import f.b.o.b.o.*;
import f.b.o.g.*;
import f.b.*;
import java.util.*;

public class v
{
    private String wk;
    private o wp;
    private int wm;
    private int ws;
    private int we;
    private int wx;
    private List<b> wz;
    
    public v(final o v) {
        super();
        this.wz = new ArrayList<b>();
        this.wk = v.ne.co;
        this.wp = v;
    }
    
    private void rb() {
        int v = 0;
        int v2 = 0;
        for (final c v3 : u.so) {
            if (v3.lk == this.wp) {
                final b v4 = new b(v3);
                v2 += v4.lc + 1;
                final int v5 = p.mc.fontRenderer.getStringWidth(v4.bo());
                if (v5 > v) {
                    v = v5;
                }
                this.wz.add(v4);
            }
        }
        this.wx = Math.min(n.scaledResolution.getScaledHeight(), v2);
        this.we = v + 15;
    }
    
    public static void rg() {
    }
}
