package f.b.o.g.player;

import f.b.o.g.*;

@c$b(name = "Rotation Lock", description = "Lock your rotation", category = f.b.o.c.c.PLAYER)
public class RotationLock extends c
{
    private final f.b.f.c xy;
    
    public RotationLock() {
        super();
        this.xy = new f.b.f.c("Facing", this, "North", new String[] { "North", "East", "South", "West" });
    }
    
    public void bv() {
        RotationLock.mc.player.setRotationYawHead((float)this.gv());
    }
    
    public void bh() {
        if (this.bu()) {
            RotationLock.mc.player.rotationYaw = (float)this.gv();
        }
    }
    
    private int gv() {
        final String ci = this.xy.ci();
        int v = 0;
        switch (ci) {
            default: {
                v = 0;
                break;
            }
            case "East": {
                v = 90;
                break;
            }
            case "South": {
                v = 180;
                break;
            }
            case "West": {
                v = -90;
                break;
            }
        }
        return v;
    }
}
