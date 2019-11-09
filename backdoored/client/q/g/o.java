package f.b.q.g;

import net.minecraft.util.math.*;
import net.minecraft.client.renderer.entity.*;
import f.b.*;
import net.minecraftforge.fml.common.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class o
{
    public o() {
        super();
    }
    
    public static void a(final String v, final Vec3d v) {
        final float v2 = (float)ObfuscationReflectionHelper.getPrivateValue((Class)RenderManager.class, (Object)p.mc.getRenderManager(), new String[] { "playerViewX", "field_78732_j" });
        final float v3 = (float)ObfuscationReflectionHelper.getPrivateValue((Class)RenderManager.class, (Object)p.mc.getRenderManager(), new String[] { "playerViewY", "field_78735_i" });
        final float v4 = 1.6f;
        final float v5 = (float)(0.01666666753590107 * p.mc.player.getDistance(v.x, v.y, v.z) / 2.0);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)v.x, (float)v.y, (float)v.z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-v3, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(v2, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-v5, -v5, v5);
        GL11.glDepthMask(false);
        GL11.glDisable(2896);
        final Tessellator v6 = Tessellator.getInstance();
        final BufferBuilder v7 = v6.getBuffer();
        final int v8 = (int)(-p.mc.player.getDistance(v.x, v.y, v.z)) / (int)v4;
        GL11.glDisable(3553);
        final int v9 = p.mc.fontRenderer.getStringWidth(v) / 2;
        p.mc.fontRenderer.drawStringWithShadow(v, (float)(-v9), (float)v8, 16777215);
        p.mc.entityRenderer.disableLightmap();
        GL11.glLineWidth(1.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2896);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
}
