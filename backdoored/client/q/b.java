package f.b.q;

import java.util.*;
import java.awt.*;

public class b
{
    public static final Map<String, String> yn;
    
    public b() {
        super();
    }
    
    public static Color nb() {
        final float v = (System.nanoTime() + 10L) / 1.0E10f % 1.0f;
        final long v2 = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(v, 1.0f, 1.0f)), 16);
        final Color v3 = new Color((int)v2);
        return new Color(v3.getRed() / 255.0f, v3.getGreen() / 255.0f, v3.getBlue() / 255.0f, v3.getAlpha() / 255.0f);
    }
    
    public static String bi(final String v) {
        return b.yn.getOrDefault(v.replace(" ", "_").trim().toLowerCase(), "§d");
    }
    
    static {
        yn = new b$o();
    }
}
