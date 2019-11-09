package f.b.o.g.t;

import f.b.o.g.*;
import net.minecraft.block.material.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Liquid Interact", description = "Allows raytracing to hit liquids", category = f.b.o.c.c.PLAYER)
public class g extends c
{
    public g() {
        super();
    }
    
    public void bv() {
        this.g(true);
    }
    
    public void bd() {
        this.g(false);
    }
    
    private void g(final boolean v) {
        final Material[] array;
        final Material[] v2 = array = new Material[] { Material.WATER, Material.LAVA };
        for (final Material v3 : array) {
            ObfuscationReflectionHelper.setPrivateValue((Class)Material.class, (Object)v3, (Object)v, new String[] { "replaceable", "field_76239_H" });
        }
    }
    
    @SubscribeEvent
    public void a(final f.b.a.g v) {
        if (this.bu()) {
            v.bu.setReturnValue(Boolean.TRUE);
        }
    }
}
