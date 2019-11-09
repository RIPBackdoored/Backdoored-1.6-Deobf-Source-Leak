package f.b.q;

import net.minecraft.item.*;
import net.minecraft.nbt.*;

public final class a
{
    public a() {
        super();
    }
    
    public static boolean g(final ItemStack v) {
        return v.hasTagCompound();
    }
    
    public static void r(final ItemStack v) {
        if (!g(v)) {
            a(v, new NBTTagCompound());
        }
    }
    
    public static void a(final ItemStack v, final NBTTagCompound v) {
        v.setTagCompound(v);
    }
    
    public static NBTTagCompound c(final ItemStack v) {
        r(v);
        return v.getTagCompound();
    }
    
    public static void a(final ItemStack v, final String v, final boolean v) {
        c(v).setBoolean(v, v);
    }
    
    public static void a(final ItemStack v, final String v, final byte v) {
        c(v).setByte(v, v);
    }
    
    public static void a(final ItemStack v, final String v, final short v) {
        c(v).setShort(v, v);
    }
    
    public static void a(final ItemStack v, final String v, final int v) {
        c(v).setInteger(v, v);
    }
    
    public static void a(final ItemStack v, final String v, final long v) {
        c(v).setLong(v, v);
    }
    
    public static void a(final ItemStack v, final String v, final float v) {
        c(v).setFloat(v, v);
    }
    
    public static void a(final ItemStack v, final String v, final double v) {
        c(v).setDouble(v, v);
    }
    
    public static void a(final ItemStack v, final String v, final NBTTagCompound v) {
        if (!v.equalsIgnoreCase("ench")) {
            c(v).setTag(v, (NBTBase)v);
        }
    }
    
    public static void a(final ItemStack v, final String v, final String v) {
        c(v).setString(v, v);
    }
    
    public static void a(final ItemStack v, final String v, final NBTTagList v) {
        c(v).setTag(v, (NBTBase)v);
    }
    
    public static boolean a(final ItemStack v, final String v) {
        return !v.isEmpty() && g(v) && c(v).hasKey(v);
    }
    
    @Deprecated
    public static boolean b(final ItemStack v, final String v) {
        return a(v, v);
    }
    
    public static boolean b(final ItemStack v, final String v, final boolean v) {
        return a(v, v) ? c(v).getBoolean(v) : v;
    }
    
    public static byte b(final ItemStack v, final String v, final byte v) {
        return a(v, v) ? c(v).getByte(v) : v;
    }
    
    public static short b(final ItemStack v, final String v, final short v) {
        return a(v, v) ? c(v).getShort(v) : v;
    }
    
    public static int b(final ItemStack v, final String v, final int v) {
        return a(v, v) ? c(v).getInteger(v) : v;
    }
    
    public static long b(final ItemStack v, final String v, final long v) {
        return a(v, v) ? c(v).getLong(v) : v;
    }
    
    public static float b(final ItemStack v, final String v, final float v) {
        return a(v, v) ? c(v).getFloat(v) : v;
    }
    
    public static double b(final ItemStack v, final String v, final double v) {
        return a(v, v) ? c(v).getDouble(v) : v;
    }
    
    public static NBTTagCompound g(final ItemStack v, final String v, final boolean v) {
        return a(v, v) ? c(v).getCompoundTag(v) : (v ? null : new NBTTagCompound());
    }
    
    public static String b(final ItemStack v, final String v, final String v) {
        return a(v, v) ? c(v).getString(v) : v;
    }
    
    public static NBTTagList a(final ItemStack v, final String v, final int v, final boolean v) {
        return a(v, v) ? c(v).getTagList(v, v) : (v ? null : new NBTTagList());
    }
}
