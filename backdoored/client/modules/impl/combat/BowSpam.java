package f.b.o.g.combat;

import f.b.o.g.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

@c$b(name = "Bow Spam", description = "Spam your bow", category = f.b.o.c.c.COMBAT)
public class BowSpam extends c
{
    private f.b.f.c pt;
    
    public BowSpam() {
        super();
        this.pt = new f.b.f.c("Delay", this, 3.0, 1.0, 25.0);
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        if (BowSpam.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && BowSpam.mc.player.isHandActive() && BowSpam.mc.player.getItemInUseMaxCount() >= this.pt.ck()) {
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, BowSpam.mc.player.getHorizontalFacing()));
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(BowSpam.mc.player.posX, BowSpam.mc.player.posY - 0.1, BowSpam.mc.player.posZ, BowSpam.mc.player.rotationYaw, BowSpam.mc.player.rotationPitch, false));
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(BowSpam.mc.player.posX, BowSpam.mc.player.posY - 999.0, BowSpam.mc.player.posZ, BowSpam.mc.player.rotationYaw, BowSpam.mc.player.rotationPitch, true));
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport());
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(BowSpam.mc.player.posX, BowSpam.mc.player.posY - 0.0824, BowSpam.mc.player.posZ, BowSpam.mc.player.rotationYaw, BowSpam.mc.player.rotationPitch, false));
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(BowSpam.mc.player.posX, BowSpam.mc.player.posY - 999.0, BowSpam.mc.player.posZ, BowSpam.mc.player.rotationYaw, BowSpam.mc.player.rotationPitch, true));
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport());
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(BowSpam.mc.player.posX, BowSpam.mc.player.posY - 0.0624, BowSpam.mc.player.posZ, BowSpam.mc.player.rotationYaw, BowSpam.mc.player.rotationPitch, false));
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(BowSpam.mc.player.posX, BowSpam.mc.player.posY - 999.0, BowSpam.mc.player.posZ, BowSpam.mc.player.rotationYaw, BowSpam.mc.player.rotationPitch, true));
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport());
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(BowSpam.mc.player.getActiveHand()));
            BowSpam.mc.player.stopActiveHand();
        }
    }
}
