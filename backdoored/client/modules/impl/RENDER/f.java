package f.b.o.g.b;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import f.b.q.i.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.math.*;

@c$b(name = "Hitbox ESP", description = "See outlines of players through walls", category = f.b.o.c.c.RENDER, defaultOn = true, defaultIsVisible = false)
public class f extends c
{
    private f.b.f.c ou;
    private f.b.f.c oh;
    
    public f() {
        super();
        this.ou = new f.b.f.c("Show friends hitbox", this, true);
        this.oh = new f.b.f.c("Show others hitbox", this, false);
    }
    
    @SubscribeEvent
    public void a(final RenderWorldLastEvent v) {
        if (!this.bu() || f.mc.world.playerEntities.size() <= 0) {
            return;
        }
        j.a(0.0f, 0.0f, 0.0f, 1.0f);
        this.ra();
        GL11.glColor4f(0.0f, 255.0f, 0.0f, 1.0f);
        this.gj();
        j.nv();
    }
    
    private void gj() {
        if (this.ou.cq()) {
            for (final EntityPlayer v : f.mc.world.playerEntities) {
                if (f.b.q.x.c.g(v) && !v.getUniqueID().equals(f.mc.player.getUniqueID())) {
                    final AxisAlignedBB v2 = v.getEntityBoundingBox();
                    j.g(v2);
                }
            }
        }
    }
    
    private void ra() {
        if (this.oh.cq()) {
            for (final EntityPlayer v : f.mc.world.playerEntities) {
                if (!f.b.q.x.c.g(v) && !v.getUniqueID().equals(f.mc.player.getUniqueID())) {
                    final AxisAlignedBB v2 = v.getEntityBoundingBox();
                    j.g(v2);
                }
            }
        }
    }
}
