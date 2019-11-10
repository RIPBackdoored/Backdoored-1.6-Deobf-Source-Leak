package f.b.o.g.movement;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Phase", description = "Phase through blocks(experimental)", category = f.b.o.c.c.MOVEMENT)
public class Phase extends c
{
    public Phase() {
        super();
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        Phase.mc.player.noClip = true;
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
