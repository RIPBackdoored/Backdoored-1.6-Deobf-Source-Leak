package f.b.c;

import f.b.w.*;
import f.b.q.c.*;
import java.io.*;

public class r extends x
{
    public r() {
        super("save");
    }
    
    @Override
    public boolean a(final String[] v) {
        if (v.length <= 0) {
            c.ba();
            o.i("Saved config", "red");
            return true;
        }
        c.b(new File("Backdoored/config-" + v[0].toLowerCase() + ".json"));
        o.i("Saved new config under name: " + v[0].toLowerCase(), "red");
        return true;
    }
    
    @Override
    public String o() {
        return "-save <save name>";
    }
}
