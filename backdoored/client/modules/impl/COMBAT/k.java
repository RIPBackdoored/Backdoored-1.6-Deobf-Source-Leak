package f.b.o.g.x;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Godmode Crystal Remover", description = "fixes crystals not removing when in god mode", category = f.b.o.c.c.COMBAT)
public class k extends c
{
    public k() {
        super();
    }
    
    @SubscribeEvent
    public void b(final v v) {
        if (this.bu() && v.packet instanceof SPacketSoundEffect) {
            final SPacketSoundEffect v2 = (SPacketSoundEffect)v.packet;
            if (v2.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (final Entity v3 : k.mc.world.loadedEntityList) {
                    if (v3 instanceof EntityEnderCrystal) {
                        final double v4 = v3.getDistance(v2.getX(), v2.getY(), v2.getZ());
                        if (v4 > 1.0) {
                            continue;
                        }
                        k.mc.world.removeEntity(v3);
                    }
                }
            }
        }
    }
}
