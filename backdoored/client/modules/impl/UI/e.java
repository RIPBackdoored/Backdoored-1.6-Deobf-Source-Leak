package f.b.o.g.w;

import f.b.o.g.*;
import java.awt.*;
import net.minecraft.util.math.*;

@c$b(name = "Coordinates", description = "Show your coordinates", category = f.b.o.c.c.UI)
public class e extends c
{
    private f.b.f.c wo;
    private f.b.f.c wt;
    
    public e() {
        super();
        this.wo = new f.b.f.c("x", this, 50, 0, (int)Math.round(e.mc.displayWidth * 1.2));
        this.wt = new f.b.f.c("y", this, 50, 0, (int)Math.round(e.mc.displayHeight * 1.2));
    }
    
    public void bm() {
        if (this.bu()) {
            e.mc.fontRenderer.drawString(this.a(e.mc.player.getPositionVector()), this.wo.cp(), this.wt.cp(), Color.WHITE.getRGB());
        }
    }
    
    private String a(final Vec3d v) {
        return (int)Math.floor(v.x) + ", " + (int)Math.floor(v.y) + ", " + (int)Math.floor(v.z) + " (" + (int)Math.floor(v.x) / 8 + ", " + (int)Math.floor(v.z) / 8 + ")";
    }
}
