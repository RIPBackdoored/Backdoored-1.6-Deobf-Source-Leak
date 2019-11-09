package f.b.a;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;

@Cancelable
public class f extends b
{
    public Packet packet;
    
    public f(final Packet<?> v) {
        super();
        this.packet = v;
    }
    
    public boolean isCancelable() {
        return true;
    }
}
