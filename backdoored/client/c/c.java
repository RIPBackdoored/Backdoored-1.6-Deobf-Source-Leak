package f.b.c;

import f.b.q.c.*;
import f.b.o.g.*;
import f.b.f.*;
import java.util.*;

public class c extends x
{
    public c() {
        super(new String[] { "bind", "keybind" });
    }
    
    @Override
    public boolean a(final String[] v) {
        if (v.length <= 0) {
            o.bn("Please specify a hack!");
            return false;
        }
        StringBuilder v2 = new StringBuilder();
        for (final String v3 : v) {
            v2.append(v3);
        }
        for (final f.b.o.g.c v4 : u.so) {
            if (v2.toString().equalsIgnoreCase(v4.lq.replace(" ", ""))) {
                for (final f.b.f.c v5 : b.a(v4)) {
                    if (v5.cw()) {
                        o.bn(v4.lq + ": " + v4);
                        return true;
                    }
                }
            }
        }
        v2 = new StringBuilder();
        for (final String v3 : Arrays.copyOf(v, v.length - 1)) {
            v2.append(v3);
        }
        for (final f.b.o.g.c v4 : u.so) {
            if (v2.toString().equalsIgnoreCase(v4.lq.replace(" ", ""))) {
                for (final f.b.f.c v5 : b.a(v4)) {
                    if (v5.cw()) {
                        v5.g(v[v.length - 1].toUpperCase());
                        o.bn("Set keybind of hack '" + v4.lq + "' to '" + v5.ci() + "'");
                        return true;
                    }
                }
            }
        }
        o.i(v2.toString() + " not found!", "red");
        return false;
    }
    
    @Override
    public String o() {
        return "-t <hackname>";
    }
}
