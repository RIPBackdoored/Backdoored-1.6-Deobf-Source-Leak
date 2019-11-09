package f.b.o.g.x;

import f.b.o.g.*;
import net.minecraftforge.event.entity.*;
import net.minecraft.init.*;
import f.b.q.*;
import f.b.q.c.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "InvisDetect", description = "Can help locate people in entity god mode", category = f.b.o.c.c.COMBAT)
public class g extends c
{
    public g() {
        super();
    }
    
    @SubscribeEvent
    public void a(final PlaySoundAtEntityEvent v) {
        if (v.getEntity() == null) {
            return;
        }
        if (v.getSound().equals(SoundEvents.ENTITY_PIG_STEP) || v.getSound().equals(SoundEvents.ENTITY_HORSE_STEP) || v.getSound().equals(SoundEvents.ENTITY_HORSE_STEP_WOOD) || v.getSound().equals(SoundEvents.ENTITY_LLAMA_STEP)) {
            final Vec3d v2 = v.getEntity().getPositionVector();
            o.bn("Invis Player at: " + h.a(v2, new boolean[0]));
        }
    }
}
