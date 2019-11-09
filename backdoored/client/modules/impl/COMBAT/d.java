package f.b.o.g.x;

import f.b.o.g.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import java.util.*;

@c$b(name = "Boat Aura", description = "Attack nearby boats", category = f.b.o.c.c.COMBAT)
public class d extends c
{
    private final f.b.f.c px;
    private final f.b.f.c pz;
    private final f.b.f.c po;
    
    public d() {
        super();
        this.px = new f.b.f.c("Range", this, 5.0, 0.0, 10.0);
        this.pz = new f.b.f.c("Boats", this, true);
        this.po = new f.b.f.c("Minecarts", this, true);
    }
    
    public void bh() {
        if (this.bu()) {
            for (final Entity v : d.mc.world.loadedEntityList) {
                if (!v.getUniqueID().equals(d.mc.player.getUniqueID()) && ((v instanceof EntityBoat && this.pz.cq()) || (v instanceof EntityMinecart && this.po.cq())) && d.mc.player.getDistance(v) <= this.px.ck()) {
                    d.mc.playerController.attackEntity((EntityPlayer)d.mc.player, v);
                }
            }
        }
    }
}
