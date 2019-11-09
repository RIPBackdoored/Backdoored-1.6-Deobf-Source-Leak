package f.b.w;

import net.minecraftforge.fml.common.gameevent.*;

public class b
{
    private static long bm;
    
    public b() {
        super();
    }
    
    public void b(final TickEvent.ClientTickEvent v) {
        if (System.currentTimeMillis() >= b.bm + 30000L) {
            b.bm = System.currentTimeMillis();
            c.ba();
        }
    }
    
    static {
        b.bm = 0L;
    }
}
