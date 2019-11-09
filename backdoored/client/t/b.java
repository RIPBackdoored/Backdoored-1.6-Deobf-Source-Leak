package f.b.t;

public class b
{
    private static b da;
    
    public b() {
        super();
    }
    
    public static String ba(final String v) {
        if (b.da == null) {
            b.da = new b();
        }
        return "";
    }
    
    public static String bb(final String v) {
        if (b.da == null) {
            b.da = new b();
        }
        return "";
    }
    
    native String bg(final String p0);
    
    native String br(final String p0);
}
