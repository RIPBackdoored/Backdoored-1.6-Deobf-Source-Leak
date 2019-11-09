package f.b.i;

import f.b.i.c.*;
import f.b.o.g.*;
import java.util.*;
import f.b.f.*;

public class g
{
    public g() {
        super();
    }
    
    public static c a(final o v) {
        for (final c v2 : u.so) {
            if (v2.lq.equals(v.co)) {
                return v2;
            }
        }
        return null;
    }
    
    public static o g(final int v, final int v) {
        for (final o v2 : bp()) {
            if (!v2.cw) {
                continue;
            }
            if (v >= v2.cs && v <= v2.cs + v2.cx && v >= v2.ce && v <= v2.ce + v2.cz) {
                return v2;
            }
        }
        return null;
    }
    
    public static ArrayList<o> bp() {
        final ArrayList<o> v = new ArrayList<o>();
        for (final f.b.o.c.o v2 : f.b.o.c.o.be()) {
            v.add(v2.ne);
            for (final c v3 : u.so) {
                if (v3.lk != v2) {
                    continue;
                }
                v.add(v3.lm);
                for (final f.b.f.c v4 : b.a(v3)) {
                    v.add(v4.dp);
                }
            }
        }
        return v;
    }
}
