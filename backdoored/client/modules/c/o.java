package f.b.o.c;

import f.b.i.c.*;
import java.util.*;

public class o
{
    public c ne;
    public static ArrayList<o> nx;
    private static ArrayList<o> nz;
    
    o(final String v) {
        super();
        this.ne = new c(v);
        o.nx.add(this);
    }
    
    public static ArrayList<o> be() {
        o.nz.clear();
        for (int v = o.nx.size() - 1; v >= 0; --v) {
            o.nz.add(o.nx.get(v));
        }
        return o.nz;
    }
    
    static {
        o.nx = new ArrayList<o>();
        o.nz = new ArrayList<o>();
    }
}
