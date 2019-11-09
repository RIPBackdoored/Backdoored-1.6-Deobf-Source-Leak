package f.b.c;

import java.io.*;
import f.b.w.*;
import f.b.q.c.*;

public class s extends x
{
    public s() {
        super("load");
    }
    
    @Override
    public boolean a(final String[] v) {
        if (v.length <= 0) {
            return false;
        }
        c.a(new File("Backdoored/config-" + v[0].toLowerCase() + ".json"));
        o.i("Loaded " + v[0].toLowerCase() + " config", "red");
        return true;
    }
    
    @Override
    public String o() {
        return "-load <config name>";
    }
}
