package f.b.c;

import f.b.q.x.*;
import f.b.q.c.*;
import java.io.*;
import java.nio.charset.*;
import org.apache.commons.io.*;
import org.json.*;

public class n extends x
{
    public n() {
        super(new String[] { "import", "importfriends" });
    }
    
    @Override
    public boolean a(final String[] v) {
        if (v.length == 1) {
            try {
                if (v[0].equalsIgnoreCase("impact")) {
                    final String v2 = "Impact/friends.cfg";
                    final BufferedReader v3 = new BufferedReader(new FileReader(v2));
                    String v4;
                    while ((v4 = v3.readLine()) != null) {
                        final String v5 = v4.split(":")[0];
                        if (!c.bp(v5)) {
                            c.bq(v5);
                            o.i("Added '" + v5 + "' to your friends!", "green");
                        }
                        else {
                            o.i("'" + v5 + "' was already a friend", "red");
                        }
                    }
                    v3.close();
                    System.out.println("Successfully imported friends");
                }
                else if (v[0].equalsIgnoreCase("wwe")) {
                    final String v2 = "WWE/friends.txt";
                    final BufferedReader v3 = new BufferedReader(new FileReader(v2));
                    String v4;
                    while ((v4 = v3.readLine()) != null) {
                        final String v5 = v4.split(" ")[0];
                        if (!c.bp(v5)) {
                            c.bq(v5);
                            o.i("Added '" + v5 + "' to your friends!", "green");
                        }
                        else {
                            o.i("'" + v5 + "' was already a friend", "red");
                        }
                    }
                    v3.close();
                    System.out.println("Successfully imported friends");
                }
                else if (v[0].equalsIgnoreCase("future")) {
                    final String v2 = System.getProperty("user.home") + "/Future/friends.json";
                    final String v6 = FileUtils.readFileToString(new File(v2), Charset.defaultCharset());
                    final JSONObject v7 = new JSONObject(v6);
                    final JSONObject v8 = v7.getJSONObject("friend-label");
                    final Object[] array;
                    final Object[] v9 = array = v8.keySet().toArray();
                    for (final Object v10 : array) {
                        final String v11 = v10.toString();
                        if (!c.bp(v11)) {
                            c.bq(v11);
                            o.i("Added '" + v11 + "' to your friends!", "green");
                        }
                        else {
                            o.i("'" + v11 + "' was already a friend", "red");
                        }
                    }
                    System.out.println("Successfully imported friends");
                }
            }
            catch (Exception v12) {
                System.out.println("Could not import to friends.txt: " + v12.toString());
                v12.printStackTrace();
                System.out.println(c.ni());
            }
            return true;
        }
        return false;
    }
    
    @Override
    public String o() {
        return "-import <Impact/WWE only ones supported now>";
    }
}
