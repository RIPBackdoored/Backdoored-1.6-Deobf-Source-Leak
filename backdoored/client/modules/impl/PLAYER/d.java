package f.b.o.g.t;

import f.b.o.g.*;
import f.b.a.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.*;
import f.b.q.c.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Fake Rotation", description = "Fake your rotation", category = f.b.o.c.c.PLAYER)
public class d extends c
{
    public d() {
        super();
    }
    
    @SubscribeEvent
    public void b(final q v) {
        if (!this.bu()) {
            return;
        }
        if (v.packet instanceof CPacketPlayer) {
            try {
                ObfuscationReflectionHelper.setPrivateValue((Class)CPacketPlayer.class, (Object)v.packet, (Object)(-90), new String[] { "pitch", "field_149473_f" });
            }
            catch (Exception v2) {
                o.i("Disabled fake rotation due to error: " + v2.toString(), "red");
                v2.printStackTrace();
            }
        }
    }
}
