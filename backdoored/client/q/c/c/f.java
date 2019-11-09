package f.b.q.c.c;

public class f extends p
{
    public f() {
        super();
    }
    
    @Override
    public String rh() {
        return "JustLearntEngrish";
    }
    
    @Override
    public String bl(final String v) {
        final StringBuilder v2 = new StringBuilder();
        final String[] split;
        final String[] v3 = split = v.split(" ");
        for (final String v4 : split) {
            v2.append(v4.substring(0, 1).toUpperCase()).append(v4.substring(1)).append(" ");
        }
        return v2.toString();
    }
}
