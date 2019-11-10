package f.b.o.g.client;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Shulker Faker", description = "Reeee", category = f.b.o.c.c.CLIENT)
public class ShulkerFaker extends c
{
    public ShulkerFaker() {
        super();
    }
    
    @SubscribeEvent
    public void a(final s v) {
        if (this.bu()) {
            int v2 = 0;
            for (final ItemStack v3 : v.nonNullList) {
                v.nonNullList.set(v2, (Object)new ItemStack(Blocks.BEDROCK));
                ++v2;
            }
        }
    }
}
