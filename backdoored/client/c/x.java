package f.b.c;

import net.minecraft.client.*;
import java.util.*;
import net.minecraftforge.common.*;
import f.b.q.c.*;

public abstract class x implements h
{
    public static ArrayList<x> y;
    protected List<String> j;
    public Minecraft mc;
    
    x(final String v) {
        this(new String[] { v });
    }
    
    x(final String... v) {
        this(Arrays.asList(v));
    }
    
    x(final List<String> v) {
        super();
        this.mc = Minecraft.getMinecraft();
        this.j = v;
        x.y.add(this);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public abstract boolean a(final String[] p0);
    
    @Override
    public void a(final String[] v, final String v) {
        o.i("Usage: " + this.o(), "yellow");
    }
    
    @Override
    public boolean a(final String[] v, final String[] v, final String v) {
        if (v[0].equals("")) {
            this.a(v, v);
            return false;
        }
        for (int v2 = 0; v2 <= v.length; ++v2) {
            if (v[v2].equals(v[0])) {
                return true;
            }
            if (!v[v2].equals(v[0]) && v2 == v.length - 1) {
                this.a(v, v);
                return false;
            }
        }
        return true;
    }
    
    @Override
    public abstract String o();
    
    static {
        x.y = new ArrayList<x>();
    }
}
