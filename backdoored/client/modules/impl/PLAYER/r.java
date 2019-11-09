package f.b.o.g.t;

import f.b.o.g.*;
import net.minecraftforge.fml.common.eventhandler.*;
import f.b.a.*;

@c$b(name = "Instant Portal", description = "ez pz", category = f.b.o.c.c.PLAYER)
public class r extends c
{
    private f.b.f.c xe;
    private f.b.f.c xx;
    
    public r() {
        super();
        this.xe = new f.b.f.c("Cooldown", this, 2, 0, 10);
        this.xx = new f.b.f.c("Wait Time", this, 2, 0, 80);
    }
    
    @SubscribeEvent
    public void a(final n v) {
        if (this.bu() && (v.entityPlayer == null || v.entityPlayer.getUniqueID().equals(r.mc.player.getUniqueID()))) {
            v.gm = this.xe.cp();
        }
    }
    
    @SubscribeEvent
    public void a(final o v) {
        if (this.bu() && (v.entity == null || v.entity.getUniqueID().equals(r.mc.player.getUniqueID()))) {
            v.gk = this.xx.cp();
        }
    }
}
