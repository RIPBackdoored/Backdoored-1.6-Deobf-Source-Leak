package f.b.c;

import f.b.o.g.*;
import f.b.q.c.*;
import java.util.*;

public class d extends x
{
    public d() {
        super(new String[] { "toggle", "t" });
    }
    
    @Override
    public boolean a(final String[] v) {
        final StringBuilder v2 = new StringBuilder();
        for (final String v3 : v) {
            v2.append(v3);
        }
        for (final c v4 : u.so) {
            if (v2.toString().equalsIgnoreCase(v4.lq.replace(" ", ""))) {
                v4.a(!v4.bu());
                return true;
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
