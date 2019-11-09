package f.b.o.g;

import java.util.*;

public class u
{
    public static List<c> so;
    public static Map<String, c> st;
    public static Boolean sw;
    public static p su;
    public static o sh;
    
    public u() {
        super();
    }
    
    public static c v(final String v) {
        for (final c v2 : u.so) {
            if (v2.lq.equalsIgnoreCase(v)) {
                return v2;
            }
        }
        throw new RuntimeException(f.b.t.c.n(10) + v + f.b.t.c.n(11));
    }
    
    public static c a(final Class<? extends c> v) {
        for (final c v2 : u.so) {
            if (v2.getClass() == v) {
                return v2;
            }
        }
        throw new RuntimeException(f.b.t.c.n(12) + v.getName() + f.b.t.c.n(11));
    }
    
    public static HashMap<f.b.o.c.o, List<c>> gs() {
        final HashMap<f.b.o.c.o, List<c>> v = new HashMap<f.b.o.c.o, List<c>>();
        for (final c v2 : u.so) {
            if (v.containsKey(v2.lk)) {
                v.get(v2.lk).add(v2);
            }
            else {
                v.put(v2.lk, new ArrayList<c>(Arrays.asList(v2)));
            }
        }
        return v;
    }
    
    public static void b(final boolean v) {
        if (v) {
            if (u.sw == null || !u.sw) {
                u.so.sort((Comparator<? super c>)u.sh);
                u.sw = true;
            }
        }
        else if (u.sw == null || u.sw) {
            u.so.sort((Comparator<? super c>)u.su);
            u.sw = false;
        }
    }
    
    static {
        u.so = (List<c>)new u$b();
        u.st = new HashMap<String, c>();
        u.sw = null;
        u.su = new p();
        u.sh = new o();
    }
}
