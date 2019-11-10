package f.b.o.g.movement;

import f.b.o.g.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Pull Down", description = "Fast fall", category = f.b.o.c.c.MOVEMENT)
public class PullDown extends c
{
    private boolean ep;
    private f.b.f.c em;
    
    public PullDown() {
        super();
        this.ep = false;
        this.em = new f.b.f.c("Speed", this, 10.0, 0.0, 20.0);
    }
    
    public void bh() {
        if (this.ep && PullDown.mc.player.onGround) {
            this.ep = false;
        }
        if (!this.bu() || PullDown.mc.player.isElytraFlying() || PullDown.mc.player.capabilities.isFlying) {
            return;
        }
        final boolean v = !PullDown.mc.world.isAirBlock(PullDown.mc.player.getPosition().add(0, -1, 0)) || !PullDown.mc.world.isAirBlock(PullDown.mc.player.getPosition().add(0, -2, 0));
        if (!PullDown.mc.player.onGround && !v) {
            PullDown.mc.player.motionY = -this.em.cp();
        }
    }
    
    @SubscribeEvent
    public void a(final LivingEvent.LivingJumpEvent v) {
        if (v.getEntityLiving().equals((Object)PullDown.mc.player)) {
            this.ep = true;
        }
    }
}
