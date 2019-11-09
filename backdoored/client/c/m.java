package f.b.c;

import f.b.q.c.*;
import f.b.o.g.*;
import f.b.f.*;
import java.util.*;

public class m extends x
{
    public m() {
        super("unbind");
    }
    
    @Override
    public boolean a(final String[] v) {
        if (v.length < 1) {
            o.bn("Invalid args!");
            return false;
        }
        final StringBuilder v2 = new StringBuilder();
        for (final String v3 : v) {
            v2.append(v3);
        }
        final String v4 = v2.toString();
        for (final c v5 : u.so) {
            if (v4.equalsIgnoreCase(v5.lq.replace(" ", ""))) {
                for (final f.b.f.c v6 : b.a(v5)) {
                    if (v6.cw()) {
                        v6.g("NONE");
                        o.bn("Bound " + v4 + " to " + v6.ci());
                        return true;
                    }
                }
            }
        }
        o.bn("Could not find hack '" + v4 + "'");
        return false;
    }
    
    @Override
    public String o() {
        return "-unbind Twerk";
    }
}
