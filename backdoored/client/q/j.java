package f.b.q;

import java.time.*;
import java.time.temporal.*;

public class j
{
    public j() {
        super();
    }
    
    public static Instant nd() {
        return Instant.now();
    }
    
    public static boolean a(final Instant v, final Instant v, final long v) {
        return Duration.between(v, v).getSeconds() >= v;
    }
    
    public static boolean b(final Instant v, final Instant v, final long v) {
        return Duration.between(v, v).toMillis() >= v;
    }
}
