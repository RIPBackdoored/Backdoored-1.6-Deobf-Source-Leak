package f.b.o.g.combat;

import f.b.o.g.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import java.util.*;

@c$b(name = "Boat Aura", description = "Attack nearby boats", category = f.b.o.c.c.COMBAT)
public class BoatAura extends c
{
    private final f.b.f.c px;
    private final f.b.f.c pz;
    private final f.b.f.c po;
    
    public BoatAura() {
        super();
        this.px = new f.b.f.c("Range", this, 5.0, 0.0, 10.0);
        this.pz = new f.b.f.c("Boats", this, true);
        this.po = new f.b.f.c("Minecarts", this, true);
    }
    
    public void bh() {
        if (this.bu()) {
            for (final Entity v : BoatAura.mc.world.loadedEntityList) {
                if (!v.getUniqueID().equals(BoatAura.mc.player.getUniqueID()) && ((v instanceof EntityBoat && this.pz.cq()) || (v instanceof EntityMinecart && this.po.cq())) && BoatAura.mc.player.getDistance(v) <= this.px.ck()) {
                    BoatAura.mc.playerController.attackEntity((EntityPlayer)BoatAura.mc.player, v);
                }
            }
        }
    }
}
