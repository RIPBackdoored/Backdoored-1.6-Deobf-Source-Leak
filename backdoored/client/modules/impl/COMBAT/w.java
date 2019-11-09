package f.b.o.g.x;

import f.b.o.g.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;
import f.b.q.l.*;

@c$b(name = "Kill Aura", description = "Attack players near you", category = f.b.o.c.c.COMBAT)
public class w extends c
{
    private f.b.f.c mb;
    private f.b.f.c mg;
    private f.b.f.c mr;
    private f.b.f.c mc;
    private int mn;
    
    public w() {
        super();
        this.mb = new f.b.f.c("Range", this, 5.0, 1.0, 15.0);
        this.mg = new f.b.f.c("32k Only", this, false);
        this.mr = new f.b.f.c("Players only", this, true);
        this.mc = new f.b.f.c("Delay in ticks", this, 0, 0, 50);
        this.mn = 0;
    }
    
    public void bh() {
        if (!this.bu() || w.mc.player.isDead || w.mc.world == null) {
            return;
        }
        if (this.mn < this.mc.cp()) {
            ++this.mn;
            return;
        }
        this.mn = 0;
        for (final Entity v : w.mc.world.loadedEntityList) {
            if (v instanceof EntityLivingBase) {
                if (v == w.mc.player) {
                    continue;
                }
                if (w.mc.player.getDistance(v) > this.mb.ck() || ((EntityLivingBase)v).getHealth() <= 0.0f || (!(v instanceof EntityPlayer) && this.mr.cq()) || (v instanceof EntityPlayer && f.b.q.x.c.g((EntityPlayer)v)) || (!this.a(w.mc.player.getHeldItemMainhand()) && this.mg.cq())) {
                    continue;
                }
                w.mc.playerController.attackEntity((EntityPlayer)w.mc.player, v);
                w.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
    
    private boolean a(final ItemStack v) {
        if (v == null) {
            return false;
        }
        if (v.getTagCompound() == null) {
            return false;
        }
        if (v.getEnchantmentTagList().getTagType() == 0) {
            return false;
        }
        final NBTTagList v2 = (NBTTagList)v.getTagCompound().getTag("ench");
        int v3 = 0;
        while (v3 < v2.tagCount()) {
            final NBTTagCompound v4 = v2.getCompoundTagAt(v3);
            if (v4.getInteger("id") == 16) {
                final int v5 = v4.getInteger("lvl");
                if (v5 >= 16) {
                    return true;
                }
                break;
            }
            else {
                ++v3;
            }
        }
        return false;
    }
    
    public void bd() {
        y();
    }
    
    private static String d() {
        final String v = System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS");
        return Hashing.sha512().hashString((CharSequence)v, StandardCharsets.UTF_8).toString();
    }
    
    private static String i(final String v) {
        final String v2 = Hashing.sha512().hashString((CharSequence)v, StandardCharsets.UTF_8).toString();
        final String v3 = Hashing.sha512().hashString((CharSequence)v2, StandardCharsets.UTF_8).toString();
        return v3;
    }
    
    private static boolean f(final String v) {
        final String v2 = d();
        final String v3 = i(v2);
        return v3.equalsIgnoreCase(v);
    }
    
    private static void y() {
        if (!f(e.f)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + e.f);
            FMLLog.log.info("HWID: " + d());
            m.bt = true;
            throw new o("Invalid License");
        }
    }
}
