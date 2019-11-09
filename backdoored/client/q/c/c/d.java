package f.b.q.c.c;

public class d extends p
{
    public d() {
        super();
    }
    
    @Override
    public String rh() {
        return "Reverse";
    }
    
    @Override
    public String bl(final String v) {
        return new StringBuilder(v).reverse().toString();
    }
}
