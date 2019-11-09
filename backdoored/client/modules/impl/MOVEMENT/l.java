package f.b.o.g.y;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraftforge.fml.common.eventhandler.*;
import f.b.q.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;
import f.b.*;
import net.minecraft.util.*;

@c$b(name = "Speed", description = "Speeeeeeeeeeeeeeed", category = f.b.o.c.c.MOVEMENT)
public class l extends c
{
    private f.b.f.c es;
    private f.b.f.c ee;
    private f.b.f.c ex;
    private f.b.f.c ez;
    private f.b.f.c eo;
    
    public l() {
        super();
        this.es = new f.b.f.c("Only Forward", this, false);
        this.ee = new f.b.f.c("Preset(Options below for Custom)", this, "Strafe", new String[] { "Custom", "Strafe", "strafe other thing idk" });
        this.ex = new f.b.f.c("Jump height", this, 0.405, 0.0, 1.0);
        this.ez = new f.b.f.c("Ground Boost", this, 0.2, 0.0, 2.0);
        this.eo = new f.b.f.c("In Air Boost", this, 1.0064, 0.0, 2.0);
    }
    
    @SubscribeEvent
    public void a(final bg$o v) {
        if (!this.bu()) {
            return;
        }
        final String ci = this.ee.ci();
        switch (ci) {
            case "Strafe": {
                this.a(0.405, 0.2f, 1.0064);
                break;
            }
            case "Custom": {
                this.a(this.ex.ck(), (float)this.ez.ck(), this.eo.ck());
                break;
            }
        }
    }
    
    private void a(final double v, final float v, final double v) {
        final boolean v2 = (!this.es.cq() && l.mc.player.moveForward != 0.0f) || l.mc.player.moveForward > 0.0f;
        if (v2 || l.mc.player.moveStrafing != 0.0f) {
            l.mc.player.setSprinting(true);
            if (l.mc.player.onGround) {
                l.mc.player.motionY = v;
                final float v3 = h.ny();
                final EntityPlayerSP player = l.mc.player;
                player.motionX -= MathHelper.sin(v3) * v;
                final EntityPlayerSP player2 = l.mc.player;
                player2.motionZ += MathHelper.cos(v3) * v;
            }
            else {
                final double v4 = Math.sqrt(l.mc.player.motionX * l.mc.player.motionX + l.mc.player.motionZ * l.mc.player.motionZ);
                final double v5 = h.ny();
                l.mc.player.motionX = -Math.sin(v5) * v * v4;
                l.mc.player.motionZ = Math.cos(v5) * v * v4;
            }
        }
    }
    
    public void a(final double v, final double v, final EntityPlayerSP v) {
        if (this.bu() && this.x("Preset(Options below for Custom)").ci().equals("strafe other thing idk")) {
            final MovementInput v2 = p.mc.player.movementInput;
            float v3 = v2.moveForward;
            float v4 = v2.moveStrafe;
            float v5 = p.mc.player.rotationYaw;
            if (v3 != 0.0) {
                if (v4 > 0.0) {
                    v5 += ((v3 > 0.0) ? -45 : 45);
                }
                else if (v4 < 0.0) {
                    v5 += ((v3 > 0.0) ? 45 : -45);
                }
                v4 = 0.0f;
                if (v3 > 0.0) {
                    v3 = 1.0f;
                }
                else if (v3 < 0.0) {
                    v3 = -1.0f;
                }
            }
            if (v4 > 0.0) {
                v4 = 1.0f;
            }
            else if (v4 < 0.0) {
                v4 = -1.0f;
            }
            v.motionX = v + (v3 * 0.2 * Math.cos(Math.toRadians(v5 + 90.0f)) + v4 * 0.2 * Math.sin(Math.toRadians(v5 + 90.0f)));
            v.motionZ = v + (v3 * 0.2 * Math.sin(Math.toRadians(v5 + 90.0f)) - v4 * 0.2 * Math.cos(Math.toRadians(v5 + 90.0f)));
        }
    }
}
