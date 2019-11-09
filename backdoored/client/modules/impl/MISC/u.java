package f.b.o.g.g;

import f.b.o.g.*;
import net.minecraft.entity.passive.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

@c$b(name = "Donkey Drop", description = "Drop all items in donkeys inv", category = f.b.o.c.c.MISC)
public class u extends c
{
    public u() {
        super();
    }
    
    public void bv() {
        final Entity v = u.mc.player.getRidingEntity();
        if (v != null && v instanceof AbstractHorse) {
            final AbstractHorse v2 = (AbstractHorse)v;
            final ContainerHorseChest v3 = (ContainerHorseChest)ObfuscationReflectionHelper.getPrivateValue((Class)AbstractHorse.class, (Object)v2, new String[] { "horseChest", "field_110296_bG" });
            final NonNullList<ItemStack> v4 = (NonNullList<ItemStack>)ObfuscationReflectionHelper.getPrivateValue((Class)InventoryBasic.class, (Object)v3, new String[] { "inventoryContents", "field_70482_c" });
            System.out.println(v4.toString());
            for (int v5 = 0; v5 < u.mc.player.inventory.getSizeInventory(); ++v5) {
                System.out.println("Dropping item in slot: " + v5);
                u.mc.playerController.windowClick(u.mc.player.openContainer.windowId, v5, 0, ClickType.THROW, (EntityPlayer)u.mc.player);
            }
        }
        this.a(false);
    }
}
