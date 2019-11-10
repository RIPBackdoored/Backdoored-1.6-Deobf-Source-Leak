package f.b.o.g.misc;

import f.b.o.g.*;
import net.minecraft.entity.player.*;
import f.b.q.*;
import f.b.o.g.a.*;
import java.util.*;
import f.b.q.c.*;

@c$b(name = "Visual Range", description = "Get notified when someone enters your render distance", category = f.b.o.c.c.MISC)
public class VisualRange extends c
{
    private List<EntityPlayer> eb;
    private f.b.f.c eg;
    private f.b.f.c er;
    
    public VisualRange() {
        super();
        this.eb = new ArrayList<EntityPlayer>();
        this.eg = new f.b.f.c("Color", this, "red", new String[] { "red", "blue", "green", "white" });
        this.er = new f.b.f.c("Mode", this, "private", new String[] { "private", "public" });
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        final List<EntityPlayer> v = new ArrayList<EntityPlayer>(VisualRange.mc.world.playerEntities);
        v.removeAll(this.eb);
        for (final EntityPlayer v2 : v) {
            if (VisualRange.mc.world.playerEntities.contains(v2)) {
                this.l("Player '" + v2.getDisplayNameString() + "' entered your render distance at " + h.a(v2.getPositionVector(), new boolean[0]), this.eg.ci());
                a.fy.a(v2);
            }
            else {
                if (!this.eb.contains(v2)) {
                    continue;
                }
                this.l("Player '" + v2.getDisplayNameString() + "' left your render distance at " + h.a(v2.getPositionVector(), new boolean[0]), this.eg.ci());
            }
        }
        this.eb = (List<EntityPlayer>)VisualRange.mc.world.playerEntities;
    }
    
    private void l(final String v, final String v) {
        if (this.er.ci().equalsIgnoreCase("private")) {
            o.i(v, v);
        }
        else {
            VisualRange.mc.player.sendChatMessage(v);
        }
    }
}
