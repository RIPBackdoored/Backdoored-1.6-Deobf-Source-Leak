package f.b.g;

import com.google.common.collect.*;
import org.json.simple.*;
import java.net.*;
import java.io.*;
import org.json.simple.parser.*;
import javax.net.ssl.*;
import f.b.s.*;
import f.b.*;
import \u0000f.\u0000b.\u0000g.*;

public class a
{
    public static HashBiMap<String, String> c;
    private static final Boolean[] n;
    
    public a() {
        super();
    }
    
    public static String r(final String v) {
        if (a.c.containsKey((Object)v)) {
            System.out.println("Grabbing username from hash map");
            return (String)a.c.get((Object)v);
        }
        System.out.println("Grabbing username from Mojang Web Api");
        return a(v, true);
    }
    
    public static String c(final String v) {
        if (a.c.containsValue((Object)v)) {
            System.out.println("Grabbing UUID from hash map");
            return a.c.inverse().get(v);
        }
        System.out.println("Grabbing UUID from Mojang Web Api");
        return a(v, false);
    }
    
    private static String a(final String v, final Boolean v) {
        if (v) {
            final String v2 = v;
            try {
                final String v3 = "https://api.mojang.com/user/profiles/" + v.replace("-", "") + "/names";
                final URL v4 = new URL(v3);
                final BufferedReader v5 = new BufferedReader(new InputStreamReader(v4.openStream()));
                String v6 = "Popbob";
                final String v7 = v5.readLine();
                System.out.println(v7);
                v5.close();
                if (v7 != null) {
                    final JSONParser v8 = new JSONParser();
                    final JSONArray v9 = (JSONArray)v8.parse(v7);
                    final JSONObject v10 = v9.get(v9.size() - 1);
                    v6 = v10.get("name").toString();
                }
                v5.close();
                a.c.put((Object)v2, (Object)v6);
                return v6;
            }
            catch (MalformedURLException v18) {
                System.out.println("MALIGNED URL, CARBOLEMONS IS DUMB IF YOU ARE READING THIS, BECAUSE, WHAT, IMPOSSIBLE... LITCHERALLLY...");
                return "";
            }
            catch (IOException v19) {
                System.out.println("uh, something went horribly wrong if you are seeing this in your log.");
                return "";
            }
            catch (ParseException v20) {
                System.out.println("JSON userdata was parsed wrong, shit.");
                return "";
            }
        }
        try {
            final String v2 = "https://api.mojang.com/users/profiles/minecraft/" + v;
            final URL v11 = new URL(v2);
            final BufferedReader v12 = new BufferedReader(new InputStreamReader(v11.openStream()));
            String v13 = "00000000000000000000000000000000";
            String v6 = "00000000-0000-0000-0000-000000000000";
            final String v7 = "Popbob";
            final String v14 = v12.readLine();
            v12.close();
            if (v14 != null) {
                final JSONParser v15 = new JSONParser();
                final JSONObject v10 = (JSONObject)v15.parse(v14);
                v13 = v10.get("id").toString();
                final String v16 = new String(v13);
                final StringBuilder v17 = new StringBuilder(v16);
                v17.insert(8, '-');
                v17.insert(13, '-');
                v17.insert(18, '-');
                v17.insert(23, '-');
                v6 = v17.toString();
            }
            a.c.put((Object)v6, (Object)v);
            return v6;
        }
        catch (MalformedURLException v21) {
            System.out.println("MALIGNED URL, CARBOLEMONS IS DUMB IF YOU ARE READING THIS, BECAUSE, WHAT, IMPOSSIBLE... LITCHERALLLY...");
            return "";
        }
        catch (IOException v22) {
            System.out.println("uh, something went horribly wrong if you are seeing this in your log.");
            return "";
        }
        catch (ParseException v23) {
            System.out.println("JSON userdata was parsed wrong, shit.");
            return "";
        }
    }
    
    private static boolean l() {
        return n("https://authserver.mojang.com/authenticate");
    }
    
    private static boolean i() {
        return n("https://sessionserver.mojang.com/");
    }
    
    public static boolean f() {
        synchronized (a.n) {
            return a.n[0];
        }
    }
    
    public static boolean q() {
        synchronized (a.n) {
            return a.n[1];
        }
    }
    
    private static boolean n(final String v) {
        try {
            final URL v2 = new URL(v);
            final HttpsURLConnection v3 = (HttpsURLConnection)v2.openConnection();
            v3.connect();
            v3.disconnect();
            return true;
        }
        catch (IOException v4) {
            v4.printStackTrace();
            return false;
        }
    }
    
    private static /* synthetic */ void k() {
        while (true) {
            try {
                while (true) {
                    if (p.mc.currentScreen instanceof x) {
                        final boolean v = l();
                        final boolean v2 = i();
                        synchronized (a.n) {
                            a.n[0] = v;
                            a.n[1] = v2;
                        }
                    }
                    Thread.sleep(7500L);
                }
            }
            catch (Exception v3) {
                v3.printStackTrace();
                continue;
            }
            break;
        }
    }
    
    static {
        a.c = (HashBiMap<String, String>)HashBiMap.create();
        n = new Boolean[] { true, true };
        new Thread(\u0000a::k, "Status checker").start();
    }
}
