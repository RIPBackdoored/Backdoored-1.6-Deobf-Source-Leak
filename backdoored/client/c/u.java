package f.b.c;

import f.b.q.c.*;

public class u extends x
{
    public u() {
        super("prefix");
    }
    
    @Override
    public boolean a(final String[] v) {
        if (v.length > 0) {
            g.v = v[0];
            o.i("Set cmd prefix to " + g.v, "red");
            return true;
        }
        return false;
    }
    
    @Override
    public String o() {
        return "Usage: .prefix <new prefix character>";
    }
}
