package f.b.g;

import java.nio.charset.*;
import java.net.*;
import com.google.gson.*;
import java.io.*;

public class x
{
    public x() {
        super();
    }
    
    public static String a(final String v, final String v, final String v) throws IOException {
        final byte[] v2 = v.getBytes(StandardCharsets.UTF_8);
        final URL v3 = new URL(v + "/documents");
        final HttpURLConnection v4 = (HttpURLConnection)v3.openConnection();
        v4.setRequestMethod("POST");
        v4.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
        v4.setFixedLengthStreamingMode(v2.length);
        v4.setDoInput(true);
        v4.setDoOutput(true);
        v4.connect();
        try {
            final OutputStream v5 = v4.getOutputStream();
            v5.write(v2);
            final InputStreamReader v6 = new InputStreamReader(v4.getInputStream());
            final JsonObject v7 = new Gson().fromJson(v6, JsonObject.class);
            String v8 = "";
            if (v != null && !v.isEmpty()) {
                v8 = "." + v;
            }
            v4.disconnect();
            return v + "/" + v7.get("key").getAsString() + "." + v;
        }
        finally {
            v4.disconnect();
        }
    }
}
