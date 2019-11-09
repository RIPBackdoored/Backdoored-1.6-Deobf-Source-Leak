package f.b.c;

import f.b.g.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import f.b.q.c.*;

public class i extends x
{
    public i() {
        super("fakeplayer");
    }
    
    @Override
    public boolean a(final String[] v) {
        try {
            if (v.length < 1) {
                return false;
            }
            final UUID v2 = UUID.fromString(a.c(v[0]));
            System.out.print("UUID LOCATED: " + v2.toString());
            final EntityOtherPlayerMP v3 = new EntityOtherPlayerMP((World)this.mc.world, new GameProfile(v2, v[0]));
            v3.copyLocationAndAnglesFrom((Entity)this.mc.player);
            final NBTTagCompound v4 = this.mc.player.writeToNBT(new NBTTagCompound());
            v3.readFromNBT(v4);
            final int[] array;
            final int[] v5 = array = new int[] { -21, -69, -911, -420, -666, -2003 };
            for (final int v6 : array) {
                if (this.mc.world.getEntityByID(v6) == null) {
                    this.mc.world.addEntityToWorld(v6, (Entity)v3);
                    return true;
                }
            }
            for (int v7 = -1; v7 > -400; --v7) {
                if (this.mc.world.getEntityByID(v7) == null) {
                    this.mc.world.addEntityToWorld(v7, (Entity)v3);
                    return true;
                }
            }
            o.i("No entity ids available", "gold");
            return false;
        }
        catch (Exception v8) {
            o.i(v8.getMessage(), "gold");
            v8.printStackTrace();
            return false;
        }
    }
    
    @Override
    public String o() {
        return "-fakeplayer DanTDM";
    }
}
