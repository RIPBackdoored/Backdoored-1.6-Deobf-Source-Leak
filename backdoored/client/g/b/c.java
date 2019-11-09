package f.b.g.b;

import java.util.*;
import net.minecraftforge.fml.common.*;

public class c
{
    private Class[] b;
    private Set<Class> g;
    
    public c() {
        super();
    }
    
    public c a(final Class[] v) {
        this.b = v;
        this.g = new HashSet<Class>(v.length);
        return this;
    }
    
    public c g() {
        for (final Class v : this.b) {
            try {
                v.newInstance();
            }
            catch (Exception v2) {
                this.g.add(v);
                FMLLog.log.info("Error initialising class " + v.getName());
                v2.printStackTrace();
            }
        }
        return this;
    }
    
    public Set<Class> r() {
        return this.g;
    }
}
