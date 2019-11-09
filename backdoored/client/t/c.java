package f.b.t;

import java.util.*;
import net.minecraftforge.fml.common.*;
import f.b.*;

public class c
{
    private static final HashMap<Integer, String> vj;
    
    public c() {
        super();
    }
    
    public static String n(final int v) {
        final String v2 = c.vj.get(v);
        return v2;
    }
    
    public static void cc() {
        FMLLog.log.info("Encrypting things");
        final long v = System.currentTimeMillis();
        int v2 = 0;
        for (final String v3 : new String[] { "Error initialising class ", "Backdoored tried to load ", " hack, out of which ", " failed", "Failed hack: ", "x", "y", "open", "prefix", "Backdoored startup finished", "Hack with name ", " not found", "Hack of class ", "Logging into an online account with email: ", "session", "field_71449_j", "Logged in successfully:", "Session ID: ", "Username: ", "textures/cape_backdoored.png", "textures/cape_backdoored_dev.png", "http://pastebin.com/raw/ZMZcF3nJ", "Gave capes to: ", "Could not fetch capes", "http://pastebin.com/raw/g4wjzg5U", "Could not fetch dev capes", "dark_red", "red", "gold", "yellow", "dark_green", "green", "aqua", "dark_aqua", "dark_blue", "blue", "light_purple", "dark_purple", "white", "gray", "dark_gray", "black", "Backdoored/friends.txt" }) {
            System.out.print("\nput(" + v2 + ",\"" + n.m(v3) + "\");      // " + v3);
            ++v2;
        }
        System.out.print("\nTook " + (System.currentTimeMillis() - v) / 1000.0 + "s");
        System.exit(-1);
    }
    
    static {
        vj = new c$o();
    }
}
