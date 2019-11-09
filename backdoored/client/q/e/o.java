package f.b.q.e;

import f.b.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;

public class o implements p
{
    public o() {
        super();
    }
    
    public double nf() {
        double v = 0.2873;
        final PotionEffect v2 = o.mc.player.getActivePotionEffect(MobEffects.SPEED);
        if (o.mc.player.isPotionActive(MobEffects.SPEED) && v2 != null) {
            final int v3 = v2.getAmplifier();
            v *= 1.0 + 0.2 * (v3 + 1);
        }
        return v;
    }
}
