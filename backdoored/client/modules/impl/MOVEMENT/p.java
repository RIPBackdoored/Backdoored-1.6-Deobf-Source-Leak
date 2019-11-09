package f.b.o.g.y;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Phase", description = "Phase through blocks(experimental)", category = f.b.o.c.c.MOVEMENT)
public class p extends c
{
    public p() {
        super();
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        p.mc.player.noClip = true;
    }
    
    @SubscribeEvent
    public void b(final q v) {
        if (!this.bu()) {
            return;
        }
        if (v.packet instanceof PlayerSPPushOutOfBlocksEvent) {
            v.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void a(final PlayerSPPushOutOfBlocksEvent v) {
        if (!this.bu()) {
            return;
        }
        v.setCanceled(true);
    }
}
