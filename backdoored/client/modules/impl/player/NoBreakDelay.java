package f.b.o.g.player;

import f.b.o.g.*;
import net.minecraft.client.multiplayer.*;
import net.minecraftforge.fml.common.*;
import f.b.q.c.*;

@c$b(name = "No Break Delay", description = "like fast place but for breaking", category = f.b.o.c.c.PLAYER)
public class NoBreakDelay extends c
{
    public NoBreakDelay() {
        super();
    }
    
    public void bh() {
        if (!this.bu()) {
            return;
        }
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)PlayerControllerMP.class, (Object)NoBreakDelay.mc.playerController, (Object)0, new String[] { "blockHitDelay", "field_78781_i" });
        }
        catch (Exception v) {
            this.a(false);
            o.bn("Disabled fastplace due to error: " + v.getMessage());
            v.printStackTrace();
        }
    }
}
