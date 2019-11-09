package f.b.q;

import net.minecraft.util.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;
import com.mojang.authlib.exceptions.*;

public class w
{
    public w() {
        super();
    }
    
    public static Session q(final String v, final String v) {
        if (v == null || v.length() <= 0 || v == null || v.length() <= 0) {
            return null;
        }
        final YggdrasilAuthenticationService v2 = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication v3 = (YggdrasilUserAuthentication)v2.createUserAuthentication(Agent.MINECRAFT);
        v3.setUsername(v);
        v3.setPassword(v);
        try {
            v3.logIn();
            return new Session(v3.getSelectedProfile().getName(), v3.getSelectedProfile().getId().toString(), v3.getAuthenticatedToken(), "LEGACY");
        }
        catch (AuthenticationException v4) {
            v4.printStackTrace();
            System.out.println("Failed login: " + v + ":" + v);
            return null;
        }
    }
    
    public static Session be(final String v) {
        return new Session(v, "", "", "LEGACY");
    }
}
