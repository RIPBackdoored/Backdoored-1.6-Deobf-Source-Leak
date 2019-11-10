package f.b.o.g.render;

import f.b.o.g.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import f.b.g.*;
import java.util.*;

@c$b(name = "Mob Owner", description = "Show you owners of mobs", category = f.b.o.c.c.RENDER)
public class MobOwner extends c
{
    public MobOwner() {
        super();
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        for (final Entity v : MobOwner.mc.world.loadedEntityList) {
            if (v instanceof EntityTameable) {
                final EntityTameable v2 = (EntityTameable)v;
                if (v2.isTamed() && v2.getOwner() != null) {
                    v2.setAlwaysRenderNameTag(true);
                    v2.setCustomNameTag("Owner: " + v2.getOwner().getDisplayName().getFormattedText());
                }
            }
            if (v instanceof AbstractHorse) {
                final AbstractHorse v3 = (AbstractHorse)v;
                if (!v3.isTame() || v3.getOwnerUniqueId() == null) {
                    continue;
                }
                v3.setAlwaysRenderNameTag(true);
                v3.setCustomNameTag("Owner: " + a.r(v3.getOwnerUniqueId().toString()));
            }
        }
    }
    
    public void bd() {
        for (final Entity v : MobOwner.mc.world.loadedEntityList) {
            if (!(v instanceof EntityTameable)) {
                if (!(v instanceof AbstractHorse)) {
                    continue;
                }
            }
            try {
                v.setAlwaysRenderNameTag(false);
            }
            catch (Exception ex) {}
        }
    }
}
