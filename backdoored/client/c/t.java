package f.b.c;

import net.minecraft.entity.*;
import f.b.q.c.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class t extends x
{
    public t() {
        super(new String[] { "spectate", "view", "watch", "possess" });
    }
    
    @Override
    public boolean a(final String[] v) {
        try {
            if (v[0].equalsIgnoreCase("off") || v[0].equalsIgnoreCase("self")) {
                this.mc.setRenderViewEntity((Entity)this.mc.player);
                o.i("Now viewing from own perspective", "green");
                return true;
            }
            for (final EntityPlayer v2 : this.mc.world.playerEntities) {
                if (v2.getDisplayNameString().equalsIgnoreCase(v[0])) {
                    this.mc.setRenderViewEntity((Entity)v2);
                    o.i("Now viewing from perspective of '" + v2.getDisplayNameString() + "'", "green");
                    return true;
                }
            }
            o.bn("Couldnt find player '" + v[0] + "'");
        }
        catch (Exception v3) {
            o.i("Error: " + v3.getMessage(), "red");
            v3.printStackTrace();
        }
        return false;
    }
    
    @Override
    public String o() {
        return "-spectate <playername/self>";
    }
}
