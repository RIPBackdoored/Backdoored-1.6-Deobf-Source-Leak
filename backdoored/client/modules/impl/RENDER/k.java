package f.b.o.g.b;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import f.b.a.*;

@c$b(name = "No Hands", description = "Dont render your hands", category = f.b.o.c.c.RENDER)
public class k extends c
{
    private f.b.f.c tb;
    private f.b.f.c tg;
    private f.b.f.c tr;
    
    public k() {
        super();
        this.tb = new f.b.f.c("Blacklist", this, "No Hands", new String[] { "No Hands", "No Left", "No Right", "All Hands" });
        this.tg = new f.b.f.c("Mainhand Offset", this, 1.0, 0.0, 2.0);
        this.tr = new f.b.f.c("Offhand Offset", this, 1.0, 0.0, 2.0);
    }
    
    @SubscribeEvent
    public void a(final RenderSpecificHandEvent v) {
        if (this.bu()) {
            if (this.tb.ci().equals("No Hands")) {
                v.setCanceled(true);
            }
            if (this.tb.ci().equals("No Left") && v.getHand() == EnumHand.OFF_HAND) {
                v.setCanceled(true);
            }
            if (this.tb.ci().equals("No Right") && v.getHand() == EnumHand.MAIN_HAND) {
                v.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void a(final be v) {
        if (this.bu()) {
            if (v.enumHand == EnumHand.MAIN_HAND) {
                v.rz = (float)this.tg.ck() - 1.0f;
            }
            if (v.enumHand == EnumHand.OFF_HAND) {
                v.rz = (float)this.tr.ck() - 1.0f;
            }
        }
    }
}
