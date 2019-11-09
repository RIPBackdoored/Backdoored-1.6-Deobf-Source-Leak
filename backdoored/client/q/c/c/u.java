package f.b.q.c.c;

import org.apache.commons.lang3.*;

public class u extends p
{
    private static final char[] yg;
    
    public u() {
        super();
    }
    
    @Override
    public String rh() {
        return "Fancy";
    }
    
    @Override
    public String bl(final String v) {
        final StringBuilder v2 = new StringBuilder();
        for (final char v3 : v.toCharArray()) {
            if (v3 < '!' || v3 > '\u0080') {
                v2.append(v3);
            }
            else if (ArrayUtils.contains(u.yg, v3)) {
                v2.append(v3);
            }
            else {
                v2.append(Character.toChars(v3 + '\ufee0'));
            }
        }
        return v2.toString();
    }
    
    static {
        yg = new char[] { '(', ')', '{', '}', '[', ']', '|' };
    }
}
