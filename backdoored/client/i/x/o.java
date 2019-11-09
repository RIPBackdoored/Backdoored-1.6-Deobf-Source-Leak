package f.b.i.x;

import org.lwjgl.input.*;
import f.b.i.*;
import f.b.o.g.*;
import java.util.*;

public class o extends j
{
    private f.b.o.c.o ni;
    
    public o() {
        super();
    }
    
    @Override
    public void bm() {
        if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
            for (final f.b.o.c.o v : f.b.o.c.o.be()) {
                if (b.nn > v.ne.cs && b.nl > v.ne.ce && b.nn < v.ne.cs + 100 && b.nl < v.ne.ce + 20) {
                    this.a(v);
                    break;
                }
                for (final c v2 : u.so) {
                    if (v2.lk != v) {
                        continue;
                    }
                    if (b.nn > v2.lm.cs && b.nl > v2.lm.ce && b.nn < v2.lm.cs + v2.lm.cx && b.nl < v2.lm.ce + v2.lm.cz) {
                        this.a(v);
                        break;
                    }
                    for (final f.b.f.c v3 : f.b.f.b.a(v2)) {
                        if (b.nn > v3.dp.cs && b.nl > v3.dp.ce && b.nn < v3.dp.cs + v3.dp.cx && b.nl < v3.dp.ce + v3.dp.cz) {
                            this.a(v);
                            break;
                        }
                    }
                }
            }
        }
        this.bs();
        for (final f.b.o.c.o v : f.b.o.c.o.be()) {
            if (Mouse.isButtonDown(0) && b.nn > v.ne.cs && b.nl > v.ne.ce && b.nn < v.ne.cs + 100 && b.nl < v.ne.ce + 20 && !v.ne.nb) {
                v.ne.ng = b.nn - v.ne.cs;
                v.ne.nr = b.nl - v.ne.ce;
                v.ne.nb = true;
            }
            if (v.ne.nb) {
                v.ne.cs = b.nn - v.ne.ng;
                v.ne.ce = b.nl - v.ne.nr;
            }
            if (!Mouse.isButtonDown(0)) {
                v.ne.nb = false;
            }
            if (v.ne.nb && Mouse.isButtonDown(0)) {
                break;
            }
        }
        for (final f.b.o.c.o v : f.b.o.c.o.be()) {
            if (!v.ne.cm) {
                for (final c v2 : u.so) {
                    if (v2.lk == v) {
                        v2.lm.cw = false;
                        for (final f.b.f.c v3 : f.b.f.b.a(v2)) {
                            v3.dp.cw = false;
                        }
                    }
                }
            }
            else {
                for (final c v2 : u.so) {
                    if (v2.lk == v) {
                        v2.lm.cw = true;
                    }
                }
            }
            f.b.i.c.o v4 = null;
            for (final c v5 : u.so) {
                if (v5.lk != v) {
                    continue;
                }
                v5.lm.cs = v.ne.cs;
                if (v4 != null) {
                    v5.lm.ce = v4.ce + v4.cz;
                }
                else {
                    v5.lm.ce = v.ne.ce + 20;
                }
                v4 = v5.lm;
            }
        }
    }
    
    private void a(final f.b.o.c.o v) {
        this.ni = v;
    }
    
    private void bs() {
        if (this.ni != null) {
            f.b.o.c.o.nx.remove(this.ni);
            f.b.o.c.o.nx.add(this.ni);
        }
    }
}
