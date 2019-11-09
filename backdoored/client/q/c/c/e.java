package f.b.q.c.c;

public class e extends p
{
    public e() {
        super();
    }
    
    @Override
    public String rh() {
        return "Emphasize";
    }
    
    @Override
    public String bl(String v) {
        v = v.replaceAll(" ", "");
        final StringBuilder v2 = new StringBuilder();
        for (final char v3 : v.toCharArray()) {
            v2.append(Character.toUpperCase(v3)).append(" ");
        }
        return v2.toString();
    }
}
