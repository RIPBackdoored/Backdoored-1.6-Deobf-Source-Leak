package f.b.o.g.movement;

import f.b.o.g.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

@c$b(name = "Fast Ice", description = "Make ice very slippery", category = f.b.o.c.c.MOVEMENT)
public class FastIce extends c
{
    private static final Block[] block;
    private f.b.f.c eq;
    
    public FastIce() {
        super();
        this.eq = new f.b.f.c("Speed (0.6 is best)", this, 0.6, 0.0, 1.0);
    }
    
    public void bv() {
        final float v = (float)this.eq.ck();
        final float v2 = Math.abs(v - 1.0f);
        r(v2);
    }
    
    public void bd() {
        r(0.98f);
    }
    
    private static void r(final float v) {
        for (final Block v2 : FastIce.block) {
            v2.setDefaultSlipperiness(v2.slipperiness = v);
        }
    }
    
    static {
        block = new Block[] { Blocks.ICE, Blocks.PACKED_ICE, Blocks.FROSTED_ICE };
    }
}
