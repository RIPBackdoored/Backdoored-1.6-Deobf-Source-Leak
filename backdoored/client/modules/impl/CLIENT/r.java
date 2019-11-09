package f.b.o.g.a;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "InfiniteChatLength", description = "Have infinite scrolling chat", category = f.b.o.c.c.CLIENT)
public class r extends c
{
    f.b.o.x.c<Enum> fm;
    f.b.o.x.c<Boolean> fs;
    f.b.o.x.c<String> fe;
    f.b.o.x.c<Integer> fx;
    f.b.o.x.c<Double> fz;
    f.b.o.x.c<Float> fo;
    
    public r() {
        super();
        this.fm = (f.b.o.x.c<Enum>)f.b.o.x.c.a("enum", this, r$z.fk);
        this.fs = (f.b.o.x.c<Boolean>)f.b.o.x.c.a("boolean", this, false);
        this.fe = (f.b.o.x.c<String>)f.b.o.x.c.a("String", this, "defaultValue");
        this.fx = (f.b.o.x.c<Integer>)f.b.o.x.c.a("int", this, 5, 0, 10);
        this.fz = (f.b.o.x.c<Double>)f.b.o.x.c.a("double", this, 5.0, 0.0, 10.0);
        this.fo = (f.b.o.x.c<Float>)f.b.o.x.c.a("float", this, 5.0f, 0.0f, 10.0f);
    }
    
    @SubscribeEvent
    public void a(final d v) {
        if (this.bu()) {
            v.setResult(Event.Result.ALLOW);
        }
        else {
            v.setResult(Event.Result.DENY);
        }
    }
}
