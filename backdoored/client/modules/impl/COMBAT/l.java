package f.b.o.g.x;

import f.b.o.g.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

@c$b(name = "Bow Spam", description = "Spam your bow", category = f.b.o.c.c.COMBAT)
public class l extends c
{
    private f.b.f.c pt;
    
    public l() {
        super();
        this.pt = new f.b.f.c("Delay", this, 3.0, 1.0, 25.0);
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        if (l.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && l.mc.player.isHandActive() && l.mc.player.getItemInUseMaxCount() >= this.pt.ck()) {
            l.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, l.mc.player.getHorizontalFacing()));
            l.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(l.mc.player.posX, l.mc.player.posY - 0.1, l.mc.player.posZ, l.mc.player.rotationYaw, l.mc.player.rotationPitch, false));
            l.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(l.mc.player.posX, l.mc.player.posY - 999.0, l.mc.player.posZ, l.mc.player.rotationYaw, l.mc.player.rotationPitch, true));
            l.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport());
            l.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(l.mc.player.posX, l.mc.player.posY - 0.0824, l.mc.player.posZ, l.mc.player.rotationYaw, l.mc.player.rotationPitch, false));
            l.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(l.mc.player.posX, l.mc.player.posY - 999.0, l.mc.player.posZ, l.mc.player.rotationYaw, l.mc.player.rotationPitch, true));
            l.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport());
            l.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(l.mc.player.posX, l.mc.player.posY - 0.0624, l.mc.player.posZ, l.mc.player.rotationYaw, l.mc.player.rotationPitch, false));
            l.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(l.mc.player.posX, l.mc.player.posY - 999.0, l.mc.player.posZ, l.mc.player.rotationYaw, l.mc.player.rotationPitch, true));
            l.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport());
            l.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(l.mc.player.getActiveHand()));
            l.mc.player.stopActiveHand();
        }
    }
}
