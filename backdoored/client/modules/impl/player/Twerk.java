package f.b.o.g.player;

import f.b.o.g.*;
import javax.swing.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import f.b.q.c.*;

@c$b(name = "Twerk", description = "Twerk that ass", category = f.b.o.c.c.PLAYER)
public class Twerk extends c
{
    private f.b.f.c ze;
    private boolean zx;
    private Timer zz;
    private int zo;
    private boolean zt;
    
    public Twerk() {
        super();
        this.ze = new f.b.f.c("Chat log", this, false);
        this.zx = false;
        this.zo = 0;
        this.zt = false;
    }
    
    public void bh() {
        if (this.zo <= 0 && this.bu()) {
            if (this.zt) {
                Twerk.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Twerk.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                Twerk.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Twerk.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.zt = !this.zt;
            if (this.ze.cq()) {
                o.bn("Sent");
            }
            this.zo = 6;
        }
        --this.zo;
    }
    
    public void bd() {
        Twerk.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Twerk.mc.player, Twerk.mc.player.isSneaking() ? CPacketEntityAction.Action.START_SNEAKING : CPacketEntityAction.Action.STOP_SNEAKING));
    }
}
