package f.b.f;

import java.util.*;

public class b
{
    static ArrayList<c> dm;
    
    public b() {
        super();
    }
    
    public static ArrayList<c> cu() {
        return b.dm;
    }
    
    public static ArrayList<c> a(final f.b.o.g.c v) {
        final ArrayList<c> v2 = new ArrayList<c>();
        for (final c v3 : cu()) {
            if (v3.di == v) {
                v2.add(v3);
            }
        }
        return v2;
    }
    
    public static c b(final f.b.o.g.c v, final String v) {
        for (final c v2 : cu()) {
            if (v2.rh().equalsIgnoreCase(v) && v2.di == v) {
                return v2;
            }
        }
        System.err.println("Error Setting NOT found: '" + v.lq + "-" + v + "'!");
        return null;
    }
    
    static {
        b.dm = new ArrayList<c>();
    }
}
