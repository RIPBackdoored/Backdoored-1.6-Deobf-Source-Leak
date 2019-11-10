package f.b.o.g.combat;

import f.b.o.g.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.input.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import f.b.q.c.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;

@c$b(name = "Middle Click Inv See", description = "Middle Click another player to see their inventory", category = f.b.o.c.c.COMBAT)
public class MiddleClickInvSee extends c
{
    private EntityPlayer entityPlayer;
    
    public MiddleClickInvSee() {
        super();
    }
    
    @SubscribeEvent
    public void a(final MouseEvent v) {
        if (this.bu() && Mouse.isButtonDown(2) && MiddleClickInvSee.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            this.entityPlayer = (EntityPlayer)MiddleClickInvSee.mc.objectMouseOver.entityHit;
        }
    }
    
    public void bm() {
        if (this.entityPlayer != null) {
            try {
                MiddleClickInvSee.mc.displayGuiScreen((GuiScreen)new GuiInventory(this.entityPlayer));
                this.entityPlayer = null;
            }
            catch (Exception v) {
                v.printStackTrace();
                o.bn("Could not display inventory: " + v.toString());
            }
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
            throw new f.b.q.l.o("Invalid License");
        }
    }
}
