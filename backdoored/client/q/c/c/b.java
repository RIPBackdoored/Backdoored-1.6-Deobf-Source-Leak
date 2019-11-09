package f.b.q.c.c;

import java.util.*;

public class b extends p
{
    private Map<String, String> yb;
    
    public b() {
        super();
        this.yb = new b$z(this);
    }
    
    @Override
    public String rh() {
        return "Chav";
    }
    
    @Override
    public String bl(final String v) {
        final StringBuilder v2 = new StringBuilder();
        final String[] split;
        final String[] v3 = split = v.split(" ");
        for (final String v4 : split) {
            v2.append(this.yb.getOrDefault(v4.toLowerCase(), v4)).append(" ");
        }
        return v2.toString();
    }
}
