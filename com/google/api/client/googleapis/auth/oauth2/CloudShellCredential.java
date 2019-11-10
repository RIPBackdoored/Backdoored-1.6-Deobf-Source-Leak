package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.auth.oauth2.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class CloudShellCredential extends GoogleCredential
{
    private static final int ACCESS_TOKEN_INDEX = 2;
    private static final int READ_TIMEOUT_MS = 5000;
    protected static final String GET_AUTH_TOKEN_REQUEST = "2\n[]";
    private final int authPort;
    private final JsonFactory jsonFactory;
    
    public CloudShellCredential(final int authPort, final JsonFactory jsonFactory) {
        super();
        this.authPort = authPort;
        this.jsonFactory = jsonFactory;
    }
    
    protected int getAuthPort() {
        return this.authPort;
    }
    
    @Override
    protected TokenResponse executeRefreshToken() throws IOException {
        final Socket socket = new Socket("localhost", this.getAuthPort());
        socket.setSoTimeout(5000);
        final TokenResponse token = new TokenResponse();
        try {
            final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("2\n[]");
            final BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            input.readLine();
            final Collection<Object> messageArray = this.jsonFactory.createJsonParser(input).parseArray(LinkedList.class, Object.class);
            final String accessToken = ((List)messageArray).get(2).toString();
            token.setAccessToken(accessToken);
        }
        finally {
            socket.close();
        }
        return token;
    }
}
