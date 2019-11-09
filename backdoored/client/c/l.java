package f.b.c;

import f.b.q.x.*;
import f.b.q.c.*;

public class l extends x
{
    private String[] bb;
    
    public l() {
        super(new String[] { "friend", "friends" });
        this.bb = new String[] { "add", "del", "list" };
    }
    
    @Override
    public boolean a(final String[] v) {
        if (!this.a(v, this.bb, "name")) {
            return false;
        }
        if (v[0].equals("add") && !v[v.length - 1].equals("add")) {
            if (!c.bp(v[1])) {
                c.bq(v[1]);
                o.i("Added '" + v[1] + "' to your friends!", "green");
            }
            else {
                o.i("'" + v[1] + "' was already a friend", "red");
            }
            return true;
        }
        if (v[0].equals("del") && !v[v.length - 1].equals("del")) {
            if (c.bp(v[1])) {
                c.bk(v[1]);
                o.i("Removed '" + v[1] + "' from your friends!", "green");
            }
            else {
                o.i("'" + v[1] + "' wasnt a friend", "red");
            }
            return true;
        }
        if (v[0].equals("list")) {
            final StringBuilder v2 = new StringBuilder("Friends: ");
            for (int v3 = 0; v3 <= c.ni().size() - 1; ++v3) {
                if (v3 == c.ni().size() - 1) {
                    v2.append(c.ni().get(v3));
                    break;
                }
                v2.append(c.ni().get(v3)).append(", ");
            }
            o.i(v2.toString(), "red");
            return true;
        }
        return false;
    }
    
    @Override
    public String o() {
        return "-friend add cookiedragon234\n-friend del 2b2tnews\n-friend list";
    }
}
