package f.b.q;

import net.minecraft.client.*;
import net.minecraft.block.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.math.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import f.b.q.i.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.util.vector.*;
import java.nio.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import f.b.q.l.*;
import f.b.*;
import net.minecraft.init.*;

public class h
{
    private static Minecraft mc;
    public static final Block[] block;
    
    public h() {
        super();
    }
    
    public static boolean a(final Block v) {
        return ArrayUtils.contains(h.block, v);
    }
    
    public static String a(final Vec3d v, final boolean... v) {
        final boolean v2 = v.length <= 0 || v[0];
        final StringBuilder v3 = new StringBuilder();
        v3.append('(');
        v3.append((int)Math.floor(v.x));
        v3.append(", ");
        if (v2) {
            v3.append((int)Math.floor(v.y));
            v3.append(", ");
        }
        v3.append((int)Math.floor(v.z));
        v3.append(")");
        return v3.toString();
    }
    
    public static String r(final BlockPos v) {
        return a(new Vec3d((Vec3i)v), new boolean[0]);
    }
    
    public static void a(final String v, final int v, final int v, final float v) {
        int v2 = v;
        for (final char v3 : v.toCharArray()) {
            final String v4 = String.valueOf(v3);
            v2 += v;
        }
    }
    
    public static Color a(final long v, final float v) {
        final float v2 = (System.nanoTime() + v) / 1.0E10f % 1.0f;
        final Color v3 = new Color((int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB(v2, 1.0f, 1.0f)), 16));
        return new Color(v3.getRed() / 255.0f * v, v3.getGreen() / 255.0f * v, v3.getBlue() / 255.0f * v, v3.getAlpha() / 255.0f);
    }
    
    public static double a(final double v, final int v) {
        final double v2 = Math.pow(10.0, v);
        return Math.round(v * v2) / v2;
    }
    
    public static void bs(final String v) {
        final StringSelection v2 = new StringSelection(v);
        final Clipboard v3 = Toolkit.getDefaultToolkit().getSystemClipboard();
        v3.setContents(v2, v2);
    }
    
    public static boolean f(final String v, final String v) {
        try {
            final BufferedWriter v2 = new BufferedWriter(new FileWriter(v));
            v2.write(v);
            v2.close();
            return true;
        }
        catch (Exception v3) {
            v3.printStackTrace();
            return false;
        }
    }
    
    public static c b(final Vec3d v) {
        final Entity v2 = h.mc.getRenderViewEntity();
        if (v2 == null) {
            return new c(0.0, 0.0, false);
        }
        final ActiveRenderInfo v3 = new ActiveRenderInfo();
        final Vec3d v4 = h.mc.player.getPositionEyes(h.mc.getRenderPartialTicks());
        final Vec3d v5 = ActiveRenderInfo.projectViewFromEntity(v2, (double)h.mc.getRenderPartialTicks());
        final float v6 = (float)(v4.x + v5.x - (float)v.x);
        final float v7 = (float)(v4.y + v5.y - (float)v.y);
        final float v8 = (float)(v4.z + v5.z - (float)v.z);
        final Vector4f v9 = new Vector4f(v6, v7, v8, 1.0f);
        final Matrix4f v10 = new Matrix4f();
        v10.load((FloatBuffer)ObfuscationReflectionHelper.getPrivateValue((Class)ActiveRenderInfo.class, (Object)new ActiveRenderInfo(), new String[] { "MODELVIEW", "field_178812_b" }));
        final Matrix4f v11 = new Matrix4f();
        v11.load((FloatBuffer)ObfuscationReflectionHelper.getPrivateValue((Class)ActiveRenderInfo.class, (Object)new ActiveRenderInfo(), new String[] { "PROJECTION", "field_178813_c" }));
        a(v9, v10);
        a(v9, v11);
        if (v9.w > 0.0f) {
            final Vector4f vector4f = v9;
            vector4f.x *= -100000.0f;
            final Vector4f vector4f2 = v9;
            vector4f2.y *= -100000.0f;
        }
        else {
            final float v12 = 1.0f / v9.w;
            final Vector4f vector4f3 = v9;
            vector4f3.x *= v12;
            final Vector4f vector4f4 = v9;
            vector4f4.y *= v12;
        }
        final ScaledResolution v13 = new ScaledResolution(h.mc);
        final float v14 = v13.getScaledWidth() / 2.0f;
        final float v15 = v13.getScaledHeight() / 2.0f;
        v9.x = v14 + (0.5f * v9.x * v13.getScaledWidth() + 0.5f);
        v9.y = v15 - (0.5f * v9.y * v13.getScaledHeight() + 0.5f);
        boolean v16 = true;
        if (v9.x < 0.0f || v9.y < 0.0f || v9.x > v13.getScaledWidth() || v9.y > v13.getScaledHeight()) {
            v16 = false;
        }
        return new c(v9.x, v9.y, v16);
    }
    
    private static void a(final Vector4f v, final Matrix4f v) {
        final float v2 = v.x;
        final float v3 = v.y;
        final float v4 = v.z;
        v.x = v2 * v.m00 + v3 * v.m10 + v4 * v.m20 + v.m30;
        v.y = v2 * v.m01 + v3 * v.m11 + v4 * v.m21 + v.m31;
        v.z = v2 * v.m02 + v3 * v.m12 + v4 * v.m22 + v.m32;
        v.w = v2 * v.m03 + v3 * v.m13 + v4 * v.m23 + v.m33;
    }
    
    public static float ny() {
        float v = h.mc.player.rotationYaw;
        if (h.mc.player.moveForward < 0.0f) {
            v += 180.0f;
        }
        float v2 = 1.0f;
        if (h.mc.player.moveForward < 0.0f) {
            v2 = -0.5f;
        }
        else if (h.mc.player.moveForward > 0.0f) {
            v2 = 0.5f;
        }
        if (h.mc.player.moveStrafing > 0.0f) {
            v -= 90.0f * v2;
        }
        if (h.mc.player.moveStrafing < 0.0f) {
            v += 90.0f * v2;
        }
        v *= 0.017453292f;
        return v;
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
    
    static {
        h.mc = p.mc;
        block = new Block[] { Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX };
    }
}
