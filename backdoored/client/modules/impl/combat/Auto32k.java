package f.b.o.g.combat;

import f.b.o.g.*;
import f.b.q.*;
import f.b.q.c.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

@c$b(name = "Auto32k", description = "Instantly places shulker and hopper and grabs a 32k sword", category = f.b.o.c.c.COMBAT)
public class Auto32k extends c
{
    private boolean qw;
    private f.b.f.c qu;
    
    public Auto32k() {
        super();
        this.qw = false;
        this.qu = new f.b.f.c("SecretClose", this, false);
    }
    
    public void bv() {
        if (Auto32k.mc.objectMouseOver == null || Auto32k.mc.objectMouseOver.sideHit == null) {
            return;
        }
        if (!this.gi()) {
            this.a(false);
        }
    }
    
    private boolean gi() {
        BlockPos v = null;
        if (Auto32k.mc.objectMouseOver == null || Auto32k.mc.objectMouseOver.sideHit == null) {
            return false;
        }
        v = Auto32k.mc.objectMouseOver.getBlockPos().offset(Auto32k.mc.objectMouseOver.sideHit);
        if (n.b(Item.getItemById(154)) == -1) {
            o.i("A hopper was not found in your hotbar!", "red");
            this.a(false);
            return false;
        }
        for (int v2 = 219; v2 <= 234 && n.b(Item.getItemById(v2)) == -1; ++v2) {
            if (v2 == 234) {
                o.i("A shulker was not found in your hotbar!", "red");
                this.a(false);
                return false;
            }
        }
        Auto32k.mc.player.inventory.currentItem = n.b(Item.getItemById(154));
        n.c(v);
        for (int v2 = 219; v2 <= 234; ++v2) {
            if (n.b(Item.getItemById(v2)) != -1) {
                Auto32k.mc.player.inventory.currentItem = n.b(Item.getItemById(v2));
                break;
            }
        }
        n.c(new BlockPos(v.getX(), v.getY() + 1, v.getZ()));
        if (this.qu.cq()) {
            c.a("Secret Close", false);
            c.a("Secret Close", true);
        }
        Auto32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(v, EnumFacing.UP, EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
        return this.qw = true;
    }
    
    public void bh() {
        if (!this.qw || !this.bu() || this.a(Auto32k.mc.player.inventory.getCurrentItem())) {
            return;
        }
        for (int v = 0; v < Auto32k.mc.player.openContainer.inventorySlots.size(); ++v) {
            if (this.a(Auto32k.mc.player.openContainer.inventorySlots.get(v).getStack())) {
                Auto32k.mc.playerController.windowClick(Auto32k.mc.player.openContainer.windowId, v, 0, ClickType.QUICK_MOVE, (EntityPlayer)Auto32k.mc.player);
                for (int v2 = 0; v2 < 9; ++v2) {
                    if (this.a(Auto32k.mc.player.inventory.getStackInSlot(v2))) {
                        Auto32k.mc.player.inventory.currentItem = v2;
                        break;
                    }
                }
                if (this.qu.cq()) {
                    Auto32k.mc.player.closeScreen();
                }
                this.a(this.qw = false);
                return;
            }
        }
    }
    
    private boolean a(final ItemStack v) {
        if (v == null) {
            return false;
        }
        if (v.getTagCompound() == null) {
            return false;
        }
        if (v.getEnchantmentTagList().getTagType() == 0) {
            return false;
        }
        final NBTTagList v2 = (NBTTagList)v.getTagCompound().getTag("ench");
        int v3 = 0;
        while (v3 < v2.tagCount()) {
            final NBTTagCompound v4 = v2.getCompoundTagAt(v3);
            if (v4.getInteger("id") == 16) {
                final int v5 = v4.getInteger("lvl");
                if (v5 >= 16) {
                    return true;
                }
                break;
            }
            else {
                ++v3;
            }
        }
        return false;
    }
}
