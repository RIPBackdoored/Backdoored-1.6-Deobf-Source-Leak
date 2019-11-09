package f.b.i.x;

import f.b.o.g.*;
import f.b.f.*;
import java.util.*;

public class c extends j
{
    public c() {
        super();
    }
    
    @Override
    public void bm() {
        for (final f.b.o.g.c v : u.so) {
            if (v.lm.cm) {
                for (final f.b.f.c v2 : Objects.requireNonNull(b.a(v))) {
                    v2.dp.cw = !v2.dp.cw;
                }
                for (final f.b.f.c v2 : b.cu()) {
                    if (v2.di != v) {
                        v2.dp.cw = false;
                    }
                }
                v.lm.cm = false;
            }
        }
    }
}
