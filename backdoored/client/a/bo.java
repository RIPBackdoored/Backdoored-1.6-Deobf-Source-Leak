package f.b.a;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

@Cancelable
public class bo extends b
{
    public Entity entity;
    public float rd;
    public float ry;
    
    public bo(final Entity v, final float v, final float v) {
        super();
        this.rd = v;
        this.ry = v;
    }
}
