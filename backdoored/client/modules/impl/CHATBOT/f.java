package f.b.o.g.j;

import java.util.*;
import org.apache.logging.log4j.*;
import javax.script.*;

public class f
{
    private final ScriptEngine ik;
    
    public f() {
        super();
        Objects.requireNonNull(this.ik = new ScriptEngineManager(null).getEngineByName("nashorn"));
    }
    
    public f u(final String v) throws ScriptException {
        this.ik.eval(v);
        return this;
    }
    
    public f a(final Logger v) {
        this.ik.put("logger", v);
        return this;
    }
    
    public Object b(final String v, final Object... v) throws ScriptException, NoSuchMethodException {
        return ((Invocable)this.ik).invokeFunction(v, v);
    }
}
