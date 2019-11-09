package f.b.q.i;

import java.awt.*;
import net.minecraft.client.renderer.entity.*;
import f.b.*;
import net.minecraftforge.fml.common.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;

public class j
{
    public j() {
        super();
    }
    
    public static void a(final BlockPos v, final double v, final Color v) throws Exception {
        final double v2 = (double)ObfuscationReflectionHelper.getPrivateValue((Class)RenderManager.class, (Object)p.mc.getRenderManager(), new String[] { "renderPosX", "field_78725_b" });
        final double v3 = (double)ObfuscationReflectionHelper.getPrivateValue((Class)RenderManager.class, (Object)p.mc.getRenderManager(), new String[] { "renderPosY", "field_78726_c" });
        final double v4 = (double)ObfuscationReflectionHelper.getPrivateValue((Class)RenderManager.class, (Object)p.mc.getRenderManager(), new String[] { "renderPosZ", "field_78723_d" });
        final double v5 = v.getX() + 0.5 - v2;
        final double v6 = v.getY() - v3;
        final double v7 = v.getZ() + 0.5 - v4;
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d((double)(v.getRed() / 255.0f), (double)(v.getGreen() / 255.0f), (double)(v.getBlue() / 255.0f), 0.25);
        GL11.glBegin(9);
        for (int v8 = 0; v8 <= 360; ++v8) {
            GL11.glVertex3d(v5 + Math.sin(v8 * 3.141592653589793 / 180.0) * v, v6, v7 + Math.cos(v8 * 3.141592653589793 / 180.0) * v);
        }
        GL11.glEnd();
        GL11.glLineWidth(2.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void a(final AxisAlignedBB v, final int v, final int v, final int v, final float v) {
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d((double)(v / 255.0f), (double)(v / 255.0f), (double)(v / 255.0f), (double)v);
        a(v, 0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glColor4d((double)(v / 255.0f), (double)(v / 255.0f), (double)(v / 255.0f), (double)v);
        a(v);
        GL11.glLineWidth(2.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void a(final BlockPos v, final int v, final int v, final int v, final float v, final double v, final double v) {
        final double v2 = v.getX();
        final double v3 = v.getY();
        final double v4 = v.getZ();
        a(new AxisAlignedBB(v2, v3, v4, v2 + v, v3 + 1.0, v4 + v), v, v, v, v);
    }
    
    public static void a(final AxisAlignedBB v, final float v, final float v, final float v, final float v) {
        final Tessellator v2 = Tessellator.getInstance();
        final BufferBuilder v3 = v2.getBuffer();
        v3.begin(7, DefaultVertexFormats.POSITION_TEX);
        v3.pos(v.minX, v.minY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.maxY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.minY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.maxY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.minY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.maxY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.minY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.maxY, v.maxZ).color(v, v, v, v).endVertex();
        v2.draw();
        v3.begin(7, DefaultVertexFormats.POSITION_TEX);
        v3.pos(v.maxX, v.maxY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.minY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.maxY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.minY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.maxY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.minY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.maxY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.minY, v.maxZ).color(v, v, v, v).endVertex();
        v2.draw();
        v3.begin(7, DefaultVertexFormats.POSITION_TEX);
        v3.pos(v.minX, v.maxY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.maxY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.maxY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.maxY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.maxY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.maxY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.maxY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.maxY, v.minZ).color(v, v, v, v).endVertex();
        v2.draw();
        v3.begin(7, DefaultVertexFormats.POSITION_TEX);
        v3.pos(v.minX, v.minY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.minY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.minY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.minY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.minY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.minY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.minY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.minY, v.minZ).color(v, v, v, v).endVertex();
        v2.draw();
        v3.begin(7, DefaultVertexFormats.POSITION_TEX);
        v3.pos(v.minX, v.minY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.maxY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.minY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.maxY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.minY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.maxY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.minY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.maxY, v.minZ).color(v, v, v, v).endVertex();
        v2.draw();
        v3.begin(7, DefaultVertexFormats.POSITION_TEX);
        v3.pos(v.minX, v.maxY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.minY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.maxY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.minX, v.minY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.maxY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.minY, v.minZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.maxY, v.maxZ).color(v, v, v, v).endVertex();
        v3.pos(v.maxX, v.minY, v.maxZ).color(v, v, v, v).endVertex();
        v2.draw();
    }
    
    public static void a(final AxisAlignedBB v) {
        final Tessellator v2 = Tessellator.getInstance();
        final BufferBuilder v3 = v2.getBuffer();
        v3.begin(3, DefaultVertexFormats.POSITION);
        v3.pos(v.minX, v.minY, v.minZ).endVertex();
        v3.pos(v.maxX, v.minY, v.minZ).endVertex();
        v3.pos(v.maxX, v.minY, v.maxZ).endVertex();
        v3.pos(v.minX, v.minY, v.maxZ).endVertex();
        v3.pos(v.minX, v.minY, v.minZ).endVertex();
        v2.draw();
        v3.begin(3, DefaultVertexFormats.POSITION);
        v3.pos(v.minX, v.maxY, v.minZ).endVertex();
        v3.pos(v.maxX, v.maxY, v.minZ).endVertex();
        v3.pos(v.maxX, v.maxY, v.maxZ).endVertex();
        v3.pos(v.minX, v.maxY, v.maxZ).endVertex();
        v3.pos(v.minX, v.maxY, v.minZ).endVertex();
        v2.draw();
        v3.begin(1, DefaultVertexFormats.POSITION);
        v3.pos(v.minX, v.minY, v.minZ).endVertex();
        v3.pos(v.minX, v.maxY, v.minZ).endVertex();
        v3.pos(v.maxX, v.minY, v.minZ).endVertex();
        v3.pos(v.maxX, v.maxY, v.minZ).endVertex();
        v3.pos(v.maxX, v.minY, v.maxZ).endVertex();
        v3.pos(v.maxX, v.maxY, v.maxZ).endVertex();
        v3.pos(v.minX, v.minY, v.maxZ).endVertex();
        v3.pos(v.minX, v.maxY, v.maxZ).endVertex();
        v2.draw();
    }
    
    public static void a(final double v, final double v, final double v, final double v, final double v, final float v, final float v, final float v, final float v) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(v, v, v, v);
        a(new AxisAlignedBB(v - v, v, v - v, v + v, v + v, v + v));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void b(final AxisAlignedBB v) {
        GL11.glBegin(7);
        GL11.glVertex3d(v.minX, v.minY, v.minZ);
        GL11.glVertex3d(v.maxX, v.minY, v.minZ);
        GL11.glVertex3d(v.maxX, v.minY, v.maxZ);
        GL11.glVertex3d(v.minX, v.minY, v.maxZ);
        GL11.glVertex3d(v.minX, v.maxY, v.minZ);
        GL11.glVertex3d(v.minX, v.maxY, v.maxZ);
        GL11.glVertex3d(v.maxX, v.maxY, v.maxZ);
        GL11.glVertex3d(v.maxX, v.maxY, v.minZ);
        GL11.glVertex3d(v.minX, v.minY, v.minZ);
        GL11.glVertex3d(v.minX, v.maxY, v.minZ);
        GL11.glVertex3d(v.maxX, v.maxY, v.minZ);
        GL11.glVertex3d(v.maxX, v.minY, v.minZ);
        GL11.glVertex3d(v.maxX, v.minY, v.minZ);
        GL11.glVertex3d(v.maxX, v.maxY, v.minZ);
        GL11.glVertex3d(v.maxX, v.maxY, v.maxZ);
        GL11.glVertex3d(v.maxX, v.minY, v.maxZ);
        GL11.glVertex3d(v.minX, v.minY, v.maxZ);
        GL11.glVertex3d(v.maxX, v.minY, v.maxZ);
        GL11.glVertex3d(v.maxX, v.maxY, v.maxZ);
        GL11.glVertex3d(v.minX, v.maxY, v.maxZ);
        GL11.glVertex3d(v.minX, v.minY, v.minZ);
        GL11.glVertex3d(v.minX, v.minY, v.maxZ);
        GL11.glVertex3d(v.minX, v.maxY, v.maxZ);
        GL11.glVertex3d(v.minX, v.maxY, v.minZ);
        GL11.glEnd();
    }
    
    public static void g(final AxisAlignedBB v) {
        GL11.glBegin(1);
        GL11.glVertex3d(v.minX, v.minY, v.minZ);
        GL11.glVertex3d(v.maxX, v.minY, v.minZ);
        GL11.glVertex3d(v.maxX, v.minY, v.minZ);
        GL11.glVertex3d(v.maxX, v.minY, v.maxZ);
        GL11.glVertex3d(v.maxX, v.minY, v.maxZ);
        GL11.glVertex3d(v.minX, v.minY, v.maxZ);
        GL11.glVertex3d(v.minX, v.minY, v.maxZ);
        GL11.glVertex3d(v.minX, v.minY, v.minZ);
        GL11.glVertex3d(v.minX, v.minY, v.minZ);
        GL11.glVertex3d(v.minX, v.maxY, v.minZ);
        GL11.glVertex3d(v.maxX, v.minY, v.minZ);
        GL11.glVertex3d(v.maxX, v.maxY, v.minZ);
        GL11.glVertex3d(v.maxX, v.minY, v.maxZ);
        GL11.glVertex3d(v.maxX, v.maxY, v.maxZ);
        GL11.glVertex3d(v.minX, v.minY, v.maxZ);
        GL11.glVertex3d(v.minX, v.maxY, v.maxZ);
        GL11.glVertex3d(v.minX, v.maxY, v.minZ);
        GL11.glVertex3d(v.maxX, v.maxY, v.minZ);
        GL11.glVertex3d(v.maxX, v.maxY, v.minZ);
        GL11.glVertex3d(v.maxX, v.maxY, v.maxZ);
        GL11.glVertex3d(v.maxX, v.maxY, v.maxZ);
        GL11.glVertex3d(v.minX, v.maxY, v.maxZ);
        GL11.glVertex3d(v.minX, v.maxY, v.maxZ);
        GL11.glVertex3d(v.minX, v.maxY, v.minZ);
        GL11.glEnd();
    }
    
    public static void nh() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(2929);
        final double v = p.mc.getRenderManager().viewerPosX;
        final double v2 = p.mc.getRenderManager().viewerPosY;
        final double v3 = p.mc.getRenderManager().viewerPosZ;
        GL11.glPushMatrix();
        GL11.glTranslated(-v, -v2, -v3);
    }
    
    public static void a(final float v, final float v, final float v, final float v) {
        nh();
        GL11.glColor4f(v, v, v, v);
    }
    
    public static void nv() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static AxisAlignedBB g(final BlockPos v) {
        return p.mc.world.getBlockState(v).getBoundingBox((IBlockAccess)p.mc.world, v).offset(v);
    }
    
    public static void a(final int v, int v, int v, int v, int v, final int v) {
        if (v < v) {
            final int v2 = v;
            v = v;
            v = v2;
        }
        if (v < v) {
            final int v2 = v;
            v = v;
            v = v2;
        }
        final float v3 = (v >> 24 & 0xFF) / 255.0f;
        final float v4 = (v >> 16 & 0xFF) / 255.0f;
        final float v5 = (v >> 8 & 0xFF) / 255.0f;
        final float v6 = (v & 0xFF) / 255.0f;
        final Tessellator v7 = Tessellator.getInstance();
        final BufferBuilder v8 = v7.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(v4, v5, v6, v3);
        v8.begin(v, DefaultVertexFormats.POSITION);
        v8.pos((double)v, (double)v, 0.0).endVertex();
        v8.pos((double)v, (double)v, 0.0).endVertex();
        v8.pos((double)v, (double)v, 0.0).endVertex();
        v8.pos((double)v, (double)v, 0.0).endVertex();
        v7.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
