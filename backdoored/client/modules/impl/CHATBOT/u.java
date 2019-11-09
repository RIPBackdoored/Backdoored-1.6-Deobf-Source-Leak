package f.b.o.g.j;

import javax.script.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class u
{
    static final String ii = "Backdoored/chatbot.js";
    f if;
    static final Logger iq;
    
    u() throws Exception {
        super();
        this.if = new f().u(w("Backdoored/chatbot.js")).a(u.iq);
    }
    
    String n(final String v, final String v) throws ScriptException, NoSuchMethodException, IllegalStateException {
        return (String)this.if.b("onChatRecieved", v, v);
    }
    
    public static String w(final String v) throws IOException {
        try {
            final StringBuilder v2 = new StringBuilder();
            final BufferedReader v3 = new BufferedReader(new FileReader(v));
            String v4;
            while ((v4 = v3.readLine()) != null) {
                v2.append(v4);
            }
            v3.close();
            u.iq.info("Successfully read chatbot script");
            return v2.toString();
        }
        catch (FileNotFoundException v6) {
            final File v5 = new File(v);
            try {
                v5.createNewFile();
            }
            catch (Exception ex) {}
            throw new IllegalStateException("Could not find chatbot script file");
        }
    }
    
    static {
        iq = LogManager.getLogger("BackdooredChatBot");
    }
}
