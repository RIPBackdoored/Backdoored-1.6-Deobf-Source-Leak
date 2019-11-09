package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ItemStack.class })
public class MixinItemStack
{
    @Shadow
    int field_77991_e;
    
    public MixinItemStack() {
        super();
    }
    
    @Inject(method = { "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/item/Item;IILnet/minecraft/nbt/NBTTagCompound;)V" }, at = { @At("RETURN") })
    private void postInit(final Item itemIn, final int amount, final int meta, final NBTTagCompound capNBT, final CallbackInfo ci) {
        this.field_77991_e = meta;
    }
    
    @Redirect(method = { "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/nbt/NBTTagCompound;)V" }, at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"))
    private int max(final int a, final int b) {
        return b;
    }
}
