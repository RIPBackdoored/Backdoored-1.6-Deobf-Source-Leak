package f.b.o.g.t;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;

@c$b(name = "Pickup Tweaks", description = "Improve item picking up", category = f.b.o.c.c.PLAYER)
public class a extends c
{
    private final f.b.f.c xv;
    private final f.b.f.c xd;
    
    public a() {
        super();
        this.xv = new f.b.f.c("Pickup Delay", this, 5, 0, 40);
        this.xd = new f.b.f.c("No Pikcup", this, false);
    }
    
    @SubscribeEvent
    public void a(final h v) {
        if (this.bu()) {
            v.gp = this.xv.cp();
        }
    }
    
    @SubscribeEvent
    public void a(final EntityItemPickupEvent v) {
        if (this.bu() && this.xd.cq()) {
            v.setCanceled(true);
        }
    }
    
    public void bh() {
        if (this.bu()) {
            for (final Entity v : a.mc.world.loadedEntityList) {
                if (v instanceof EntityItem) {
                    final EntityItem v2 = (EntityItem)v;
                    v.setAlwaysRenderNameTag(true);
                    final int v3 = v2.getAge();
                    final int v4 = v2.lifespan;
                    double v5 = v3 / v4;
                    v5 *= 100.0;
                    v.setCustomNameTag(String.valueOf(v5));
                }
            }
        }
    }
}
