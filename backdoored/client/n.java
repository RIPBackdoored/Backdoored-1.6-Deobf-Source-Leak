package f.b;

import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class n
{
    private static Field bo;
    
    public n() {
        super();
    }
    
    public static String[] a(final ClassLoader v) {
        try {
            final Vector<String> v2 = (Vector<String>)n.bo.get(v);
            return v2.toArray(new String[0]);
        }
        catch (Exception v3) {
            v3.printStackTrace();
            return new String[0];
        }
    }
    
    public static void k(final String v) throws IOException {
        final ClassLoader v2 = ClassLoader.getSystemClassLoader();
        final File v3 = new File(Objects.requireNonNull(v2.getResource(v)).getFile());
        System.out.println(v3.getAbsolutePath());
        final InputStream v4 = new FileInputStream(v3);
        final byte[] v5 = new byte[1024];
        int v6 = -1;
        final File v7 = File.createTempFile(v, "");
        final FileOutputStream v8 = new FileOutputStream(v7);
        while ((v6 = v4.read(v5)) != -1) {
            v8.write(v5, 0, v6);
        }
        v8.close();
        v4.close();
        System.out.println(v7.getAbsolutePath());
        System.load(v7.getAbsolutePath());
    }
    
    public static void bl() {
        try {
            k("com_backdoored_DllManager.dll");
        }
        catch (IOException v) {
            v.printStackTrace();
        }
    }
    
    public static String p(final String v) {
        return r(v, e.f);
    }
    
    public static native String m(final String p0);
    
    public static native String r(final String p0, final String p1);
    
    public static native String bi();
    
    static {
        try {
            (n.bo = ClassLoader.class.getDeclaredField("loadedLibraryNames")).setAccessible(true);
        }
        catch (Exception v) {
            v.printStackTrace();
            n.bo = null;
        }
    }
}
