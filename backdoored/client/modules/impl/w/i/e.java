package f.b.o.g.w.i;

import net.minecraft.client.gui.*;
import f.b.o.g.w.i.v.*;
import f.b.o.c.*;
import f.b.o.g.*;
import java.util.*;
import java.io.*;

public class e extends GuiScreen
{
    private final ArrayList<q> uf;
    
    public e() {
        super();
        float v = 4.0f;
        float v2 = 4.0f;
        this.uf = new ArrayList<q>();
        for (final o v3 : o.nx) {
            v2 = 18.0f;
            final ArrayList<f.b.o.g.w.i.z.q> v4 = new ArrayList<f.b.o.g.w.i.z.q>();
            for (final c v5 : u.so) {
                if (v5.lk == v3) {
                    v4.add(new f.b.o.g.w.i.z.z.o(v5, v + 2.0f, v2 + 2.0f, 96.0f, 12.0f));
                    v2 += 13.0f;
                }
            }
            this.uf.add(new q(v3, v4, v, 4.0f, 100.0f, 14.0f));
            v += 102.0f;
        }
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    protected void func_73864_a(final int v, final int v, final int v) throws IOException {
        super.mouseClicked(v, v, v);
        for (final q v2 : this.uf) {
            v2.a(v, v, v);
        }
    }
    
    public void func_73863_a(final int v, final int v, final float v) {
        super.drawScreen(v, v, v);
        for (final q v2 : this.uf) {
            v2.l(v, v);
        }
    }
    
    protected void func_73869_a(final char v, final int v) throws IOException {
        super.keyTyped(v, v);
        for (final q v2 : this.uf) {
            v2.r(v);
        }
    }
    
    public void func_146281_b() {
        n.ri();
    }
}
