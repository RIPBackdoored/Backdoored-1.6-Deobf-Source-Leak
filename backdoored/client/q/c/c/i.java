package f.b.q.c.c;

import java.util.*;

public class i extends p
{
    private Map<String, String> yc;
    
    public i() {
        super();
        this.yc = new i$z(this);
    }
    
    @Override
    public String rh() {
        return "L33t";
    }
    
    @Override
    public String bl(final String v) {
        final StringBuilder v2 = new StringBuilder();
        for (final String v3 : v.split("")) {
            v2.append(this.yc.getOrDefault(v3.toLowerCase(), v3));
        }
        return v2.toString();
    }
}
