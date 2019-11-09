package f.b.o.g.a;

import f.b.o.g.*;
import java.util.*;
import f.b.a.*;
import f.b.q.*;
import f.b.q.c.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.client.event.*;
import java.awt.*;
import \u0000f.\u0000b.\u0000o.\u0000g.\u0000a.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;
import f.b.q.i.*;
import net.minecraft.client.renderer.entity.*;

@c$b(name = "Logout Spots", description = "Show the logout spots of other players", category = f.b.o.c.c.CLIENT)
public class n extends c
{
    private HashMap<String, AxisAlignedBB> ft;
    private final f.b.f.c fw;
    private final f.b.f.c fu;
    private final f.b.f.c fh;
    private final f.b.f.c fv;
    
    public n() {
        super();
        this.ft = new HashMap<String, AxisAlignedBB>();
        this.fw = new f.b.f.c("Red", this, 0, 0, 255);
        this.fu = new f.b.f.c("Green", this, 0, 0, 255);
        this.fh = new f.b.f.c("Blue", this, 0, 0, 255);
        this.fv = new f.b.f.c("Alpha", this, 1.0, 0.0, 1.0);
    }
    
    @SubscribeEvent
    public void a(final bb v) {
        final EntityPlayer v2 = n.mc.world.getPlayerEntityByUUID(v.gd.getId());
        if (v2 != null && n.mc.player != null && !n.mc.player.equals((Object)v2)) {
            final AxisAlignedBB v3 = v2.getEntityBoundingBox();
            final String v4 = v2.getDisplayNameString();
            if (this.ft.get(v4) != null) {
                this.ft.remove(v4);
            }
            this.ft.put(v4, v3);
            if (this.bu()) {
                o.i(String.format("Player '%s' disconnected at %s", v4, h.a(v2.getPositionVector(), new boolean[0])), "red");
                y();
            }
        }
    }
    
    @SubscribeEvent
    public void a(final TickEvent.ClientTickEvent v) {
        if (n.mc.world == null && this.ft.size() != 0) {
            this.ft.clear();
            y();
        }
    }
    
    @SubscribeEvent
    public void a(final RenderWorldLastEvent v) {
        if (!this.bu()) {
            return;
        }
        final Color v2 = new Color(this.fw.cp(), this.fu.cp(), this.fh.cp(), this.fv.cp());
        this.ft.forEach(\u0000n::a);
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
    
    private static /* synthetic */ void a(final Color v, final String v, final AxisAlignedBB v) {
        Vec3d v2 = v.getCenter();
        if (n.mc.player.getDistanceSq(v2.x, v2.y, v2.z) > 2500.0) {
            final Vec3d v3 = v2.subtract(new Vec3d(n.mc.getRenderManager().viewerPosX, n.mc.getRenderManager().viewerPosY, n.mc.getRenderManager().viewerPosZ)).normalize();
            v2 = new Vec3d(n.mc.getRenderManager().viewerPosX + v3.x * 50.0, n.mc.getRenderManager().viewerPosY + v3.y * 50.0, n.mc.getRenderManager().viewerPosZ + v3.z * 50.0);
        }
        double v4 = n.mc.player.getDistance(v2.x, v2.y, v2.z) / 4.0;
        v4 = Math.max(1.6, v4);
        final RenderManager v5 = n.mc.getRenderManager();
        GL11.glPushMatrix();
        GL11.glTranslated(-v5.viewerPosX, -v5.viewerPosY, -v5.viewerPosZ);
        GL11.glTranslatef((float)v2.x + 0.5f, (float)v2.y + 0.5f, (float)v2.z + 0.5f);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-v5.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(v5.playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glScaled(-v4, -v4, v4);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final String v6 = v + " (" + n.mc.player.getDistance(v.getCenter().x, v.getCenter().y, v.getCenter().z) + "m)";
        final int v7 = n.mc.fontRenderer.getStringWidth(v6) / 2;
        n.mc.fontRenderer.drawStringWithShadow(v6, (float)(-v7), (float)(-(n.mc.fontRenderer.FONT_HEIGHT - 1)), v.getRGB());
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
        j.a((float)(v.getRed() / 255), (float)(v.getBlue() / 255), (float)(v.getRed() / 255), (float)v.getAlpha());
        j.g(v);
        j.nv();
    }
}
