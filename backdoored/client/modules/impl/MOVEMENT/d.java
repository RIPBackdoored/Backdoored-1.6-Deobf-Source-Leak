package f.b.o.g.y;

import f.b.o.g.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Pull Down", description = "Fast fall", category = f.b.o.c.c.MOVEMENT)
public class d extends c
{
    private boolean ep;
    private f.b.f.c em;
    
    public d() {
        super();
        this.ep = false;
        this.em = new f.b.f.c("Speed", this, 10.0, 0.0, 20.0);
    }
    
    public void bh() {
        if (this.ep && d.mc.player.onGround) {
            this.ep = false;
        }
        if (!this.bu() || d.mc.player.isElytraFlying() || d.mc.player.capabilities.isFlying) {
            return;
        }
        final boolean v = !d.mc.world.isAirBlock(d.mc.player.getPosition().add(0, -1, 0)) || !d.mc.world.isAirBlock(d.mc.player.getPosition().add(0, -2, 0));
        if (!d.mc.player.onGround && !v) {
            d.mc.player.motionY = -this.em.cp();
        }
    }
    
    @SubscribeEvent
    public void a(final LivingEvent.LivingJumpEvent v) {
        if (v.getEntityLiving().equals((Object)d.mc.player)) {
            this.ep = true;
        }
    }
}
