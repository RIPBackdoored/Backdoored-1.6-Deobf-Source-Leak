package f.b;

import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.client.*;

public interface p
{
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final boolean cq = true;
    
    default NetworkManager bk() {
        return FMLClientHandler.instance().getClientToServerNetworkManager();
    }
}
