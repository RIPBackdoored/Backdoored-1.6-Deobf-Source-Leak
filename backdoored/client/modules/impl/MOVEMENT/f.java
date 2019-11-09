package f.b.o.g.y;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "No Slow", description = "Dont slow down boiiii", category = f.b.o.c.c.MOVEMENT)
public class f extends c
{
    private f.b.f.c ek;
    
    public f() {
        super();
        this.ek = new f.b.f.c("Water Speed", this, 1.0, 0.0, 1.0);
    }
    
    @SubscribeEvent
    public void a(final w v) {
        if (this.bu()) {
            v.gx = (float)(1 - this.ek.cp());
        }
    }
}
