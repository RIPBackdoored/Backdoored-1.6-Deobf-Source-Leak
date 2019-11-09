package f.b.c;

import f.b.q.c.*;
import f.b.o.g.*;
import f.b.f.*;
import java.util.*;

public class a extends x
{
    public a() {
        super(new String[] { "drawn", "shown", "visible" });
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
                    if (v6.rh().equals("Is Visible")) {
                        v6.g(!v6.cq());
                        String v7 = "not ";
                        if (v6.cq()) {
                            v7 = "";
                        }
                        o.bn("Hack '" + v4 + "' is now " + v7 + "drawn");
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
        return "-drawn Twerk";
    }
}
