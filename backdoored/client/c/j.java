package f.b.c;

import f.b.q.c.*;
import net.minecraft.world.border.*;

public class j extends x
{
    public j() {
        super(new String[] { "getworldborder", "worldborder", "border" });
    }
    
    @Override
    public boolean a(final String[] v) {
        final WorldBorder v2 = this.mc.world.getWorldBorder();
        final double v3 = v2.maxX();
        final double v4 = v2.maxZ();
        final double v5 = v2.minX();
        final double v6 = v2.minZ();
        o.i("World border is at:\nMinX: " + v5 + "\nMinZ: " + v6 + "\nMaxX: " + v3 + "\nMaxZ: " + v4 + "\n", "green");
        return true;
    }
    
    @Override
    public String o() {
        return "-worldborder";
    }
}
