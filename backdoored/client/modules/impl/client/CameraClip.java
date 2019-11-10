package f.b.o.g.client;

import f.b.o.g.*;
import f.b.o.g.a.*;

@c$b(name = "Camera Clip", description = "Clip through walls", category = f.b.o.c.c.CLIENT)
public class CameraClip extends c
{
    private static f iw;
    private final f.b.f.c iu;
    
    public CameraClip() {
        super();
        this.iu = new f.b.f.c("Distance", this, 3.5, 0.0, 10.0);
        CameraClip.iw = (f)this;
    }
    
    public static float gc() {
        if (CameraClip.iw != null && ((c)CameraClip.iw).bu()) {
            return (float)((CameraClip)CameraClip.iw).iu.ck();
        }
        return 0.0f;
    }
    
    public static boolean bx() {
        return CameraClip.iw == null || ((c)CameraClip.iw).bu();
    }
}
