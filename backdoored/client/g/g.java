package f.b.g;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.*;
import java.io.*;
import com.google.api.services.sheets.v4.*;
import com.google.api.client.googleapis.javanet.*;
import com.google.api.client.json.jackson2.*;
import com.google.api.client.json.*;
import com.google.api.client.http.*;
import java.security.*;
import com.google.api.services.sheets.v4.model.*;
import java.util.*;

class g
{
    private static String r;
    
    g() {
        super();
    }
    
    private static Credential c() throws IOException {
        final InputStream v = g.class.getResourceAsStream("/resources/backdoored-client-340b78ae95c4.json");
        final Credential v2 = GoogleCredential.fromStream(v).createScoped(Collections.singleton("https://www.googleapis.com/auth/spreadsheets"));
        return v2;
    }
    
    private static Sheets n() throws GeneralSecurityException, IOException {
        final Sheets v = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), c()).setApplicationName("Backdoored License Handler").build();
        return v;
    }
    
    private static String a(final String v) throws IOException, GeneralSecurityException {
        final ValueRange v2 = n().spreadsheets().values().get(g.r, v).execute();
        for (int v3 = 0; v3 < v2.getValues().size(); ++v3) {
            System.out.println(v2.getValues().get(v3));
        }
        final String v4 = (v2.getValues() != null) ? v2.getValues().get(0).toString() : "";
        return v4;
    }
    
    private static List<List<Object>> b(final String v) throws IOException, GeneralSecurityException {
        final ValueRange v2 = n().spreadsheets().values().get(g.r, v).execute();
        return v2.getValues();
    }
    
    public static boolean g(final String v) throws IOException, GeneralSecurityException {
        final ValueRange v2 = n().spreadsheets().values().get(g.r, "A2:A").execute();
        for (int v3 = 0; v3 < v2.getValues().size(); ++v3) {
            if (v2.getValues().get(v3).toString().replace("[", "").replace("]", "").equals(v)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        g.r = "1_kxn8nNafDEUPpKNZ6ozlUaASlODC_Sf9hIniJvH33E";
    }
}
