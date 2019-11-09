package f.b.c;

import java.util.*;

public class o extends x
{
    public o() {
        super(new String[] { "help", "commands" });
    }
    
    @Override
    public boolean a(final String[] v) {
        f.b.q.c.o.bn("Commands:\n");
        for (final x v2 : x.y) {
            f.b.q.c.o.bn(v2.j.get(0) + "\n");
        }
        return true;
    }
    
    @Override
    public String o() {
        return "-help";
    }
}
