package f.b.w;

import org.json.*;
import java.nio.charset.*;
import org.apache.commons.io.*;
import f.b.o.g.*;
import f.b.f.*;
import f.b.o.c.*;
import f.b.c.*;
import java.io.*;
import java.util.*;

public class c
{
    private static final File bi;
    public static JSONObject bf;
    private static JSONObject bq;
    private static JSONObject bk;
    private static JSONObject bp;
    
    public c() {
        super();
    }
    
    public static void j() {
        a(c.bi);
    }
    
    public static void a(final File v) {
        try {
            if (!v.exists()) {
                v.createNewFile();
            }
            String v2 = FileUtils.readFileToString(v, Charset.defaultCharset());
            if (v2.trim().isEmpty() && v.renameTo(v)) {
                ba();
                v2 = FileUtils.readFileToString(v, Charset.defaultCharset());
            }
            c.bf = new JSONObject(v2);
            c.bq = c.bf.getJSONObject("Hacks");
            c.bk = c.bf.getJSONObject("EnumCategory");
            c.bp = c.bf.getJSONObject("Commands");
            for (final f.b.o.g.c v3 : u.so) {
                if (a(v3, "Enabled") != null) {
                    v3.a((boolean)a(v3, "Enabled"));
                }
                for (final f.b.f.c v4 : b.a(v3)) {
                    if (a(v3, v4.rh()) != null) {
                        v4.g(a(v3, v4.rh()));
                    }
                }
            }
            for (final o v5 : o.nx) {
                if (a(v5, "x") != null) {
                    v5.ne.cs = (int)a(v5, "x");
                }
                if (a(v5, "y") != null) {
                    v5.ne.ce = (int)a(v5, "y");
                }
                if (a(v5, "open") != null) {
                    v5.ne.cm = (boolean)a(v5, "open");
                }
            }
            if (q("prefix") != null) {
                g.v = (String)q("prefix");
            }
        }
        catch (Exception v6) {
            System.out.println("ERROR READING BACKDOORED CONFIG FILE!!!");
            v6.printStackTrace();
        }
    }
    
    public static Object a(final f.b.o.g.c v, final String v) {
        return b(v.lq, v);
    }
    
    public static Object b(final String v, final String v) {
        try {
            return c.bq.getJSONObject(v).get(v);
        }
        catch (Exception v2) {
            return null;
        }
    }
    
    public static Object a(final o v, final String v) {
        return g(v.ne.co, v);
    }
    
    public static Object g(final String v, final String v) {
        try {
            return c.bk.getJSONObject(v).get(v);
        }
        catch (Exception v2) {
            return null;
        }
    }
    
    public static Object q(final String v) {
        try {
            return c.bp.get(v);
        }
        catch (Exception v2) {
            return null;
        }
    }
    
    public static void ba() {
        b(c.bi);
    }
    
    public static void b(final File v) {
        c.bf.put("Hacks", bb());
        c.bf.put("EnumCategory", bg());
        c.bf.put("Commands", br());
        try {
            if (!v.exists()) {
                v.createNewFile();
            }
            if (v.renameTo(v)) {
                final PrintWriter v2 = new PrintWriter(new FileWriter(v));
                v2.print(c.bf.toString(4));
                v2.close();
            }
            else {
                System.out.println("Failed to save, file already in use");
            }
        }
        catch (Exception v3) {
            System.out.println("ERROR SAVING BACKDOORED CONFIG FILE!!!");
            v3.printStackTrace();
        }
    }
    
    private static JSONObject bb() {
        for (final f.b.o.g.c v : u.so) {
            final JSONObject v2 = new JSONObject();
            v2.put("Enabled", v.lm.cp);
            final ArrayList<f.b.f.c> v3 = b.a(v);
            if (v3 != null) {
                for (final f.b.f.c v4 : v3) {
                    v2.put(v4.rh(), v4.cl());
                }
            }
            c.bq.put(v.lq, v2);
        }
        return c.bq;
    }
    
    private static JSONObject bg() {
        for (final o v : o.nx) {
            final JSONObject v2 = new JSONObject();
            v2.put("x", v.ne.cs);
            v2.put("y", v.ne.ce);
            v2.put("open", v.ne.cm);
            c.bk.put(v.ne.co, v2);
        }
        return c.bk;
    }
    
    private static JSONObject br() {
        c.bp.put("prefix", g.v);
        return c.bp;
    }
    
    static {
        bi = new File("Backdoored/config.json");
        c.bf = new JSONObject();
        c.bq = new JSONObject();
        c.bk = new JSONObject();
        c.bp = new JSONObject();
    }
}
