package f.b.o.g.a;

import f.b.o.g.*;

@c$b(name = "Camera Clip", description = "Clip through walls", category = f.b.o.c.c.CLIENT)
public class f extends c
{
    private static f iw;
    private final f.b.f.c iu;
    
    public f() {
        super();
        this.iu = new f.b.f.c("Distance", this, 3.5, 0.0, 10.0);
        f.iw = this;
    }
    
    public static float gc() {
        if (f.iw != null && f.iw.bu()) {
            return (float)f.iw.iu.ck();
        }
        return 0.0f;
    }
    
    public static boolean bx() {
        return f.iw == null || f.iw.bu();
    }
}
