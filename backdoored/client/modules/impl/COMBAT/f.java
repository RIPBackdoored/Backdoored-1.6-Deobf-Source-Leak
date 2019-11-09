package f.b.o.g.x;

import f.b.o.g.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import f.b.a.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;
import f.b.q.l.*;

@c$b(name = "AutoEz", description = "Automatically ez", category = f.b.o.c.c.COMBAT)
public class f extends c
{
    private f.b.f.c pc;
    private int pn;
    private EntityPlayer entityPlayer;
    
    public f() {
        super();
        this.pc = new f.b.f.c("Text", this, "Ez", new String[] { "Ez", "Nice fight, shit_stain.pl owns me and all!", "Nice fight, shit_tier.pl owns me and all!", "Nice fight, DotShit.cc owns me and all!", "Nice fight, Backdoored Client owns me and all!", "Nice fight, cookiedragon234 owns me and all!", "Nice fight, tigermouthbear owns me and all!" });
    }
    
    @SubscribeEvent
    public void a(final AttackEntityEvent v) {
        if (this.bu() && v.getTarget() instanceof EntityPlayer) {
            final EntityPlayer v2 = (EntityPlayer)v.getTarget();
            if (v.getEntityPlayer().getUniqueID().equals(f.mc.player.getUniqueID())) {
                if (v2.getHealth() <= 0.0f || v2.isDead || !f.mc.world.playerEntities.contains(v2)) {
                    f.mc.player.sendChatMessage(this.pc.ci());
                    y();
                    return;
                }
                this.pn = 500;
                this.entityPlayer = v2;
            }
        }
    }
    
    public void bh() {
        if (f.mc.player.isDead) {
            this.pn = 0;
        }
        if (this.pn > 0 && (this.entityPlayer.getHealth() <= 0.0f || this.entityPlayer.isDead || !f.mc.world.playerEntities.contains(this.entityPlayer))) {
            if (this.bu()) {
                f.mc.player.sendChatMessage(this.pc.ci());
                y();
            }
            this.pn = 0;
        }
        --this.pn;
    }
    
    @SubscribeEvent
    public void a(final z v) {
        if (!this.bu()) {
            return;
        }
        if (!v.bq().equals((Object)f.mc.player) && v.bq().equals((Object)this.entityPlayer)) {
            f.mc.player.sendChatMessage(this.pc.ci());
            y();
        }
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
