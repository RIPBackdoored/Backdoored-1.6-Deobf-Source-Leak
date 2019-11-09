package f.b.g.b;

import net.minecraft.launchwrapper.*;
import java.util.*;
import java.net.*;

public class o
{
    public final LaunchClassLoader launchClassLoader;
    
    public o() {
        super();
        this.launchClassLoader = Launch.classLoader;
    }
    
    public List<Class> a(final String v, final String v, final String... v) throws MalformedURLException, ClassNotFoundException {
        final URL v2 = new URL(v + "/" + v);
        this.launchClassLoader.addURL(v2);
        final List<Class> v3 = new ArrayList<Class>();
        for (final String v4 : v) {
            v3.add(this.launchClassLoader.loadClass(v4));
        }
        return v3;
    }
}
