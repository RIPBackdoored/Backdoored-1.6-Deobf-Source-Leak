package com.google.api.client.extensions.java6.auth.oauth2;

import com.google.api.client.util.*;
import com.google.api.client.auth.oauth2.*;
import java.awt.*;
import java.net.*;
import java.util.logging.*;
import java.io.*;

public class AuthorizationCodeInstalledApp
{
    private final AuthorizationCodeFlow flow;
    private final VerificationCodeReceiver receiver;
    private static final Logger LOGGER;
    
    public AuthorizationCodeInstalledApp(final AuthorizationCodeFlow flow, final VerificationCodeReceiver receiver) {
        super();
        this.flow = Preconditions.checkNotNull(flow);
        this.receiver = Preconditions.checkNotNull(receiver);
    }
    
    public Credential authorize(final String userId) throws IOException {
        try {
            final Credential credential = this.flow.loadCredential(userId);
            if (credential != null && (credential.getRefreshToken() != null || credential.getExpiresInSeconds() == null || credential.getExpiresInSeconds() > 60L)) {
                return credential;
            }
            final String redirectUri = this.receiver.getRedirectUri();
            final AuthorizationCodeRequestUrl authorizationUrl = this.flow.newAuthorizationUrl().setRedirectUri(redirectUri);
            this.onAuthorization(authorizationUrl);
            final String code = this.receiver.waitForCode();
            final TokenResponse response = this.flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
            return this.flow.createAndStoreCredential(response, userId);
        }
        finally {
            this.receiver.stop();
        }
    }
    
    protected void onAuthorization(final AuthorizationCodeRequestUrl authorizationUrl) throws IOException {
        browse(authorizationUrl.build());
    }
    
    public static void browse(final String url) {
        Preconditions.checkNotNull(url);
        System.out.println("Please open the following address in your browser:");
        final PrintStream out = System.out;
        final String s = "  ";
        final String value = String.valueOf(url);
        out.println((value.length() != 0) ? s.concat(value) : new String(s));
        try {
            if (Desktop.isDesktopSupported()) {
                final Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    System.out.println("Attempting to open that address in the default browser now...");
                    desktop.browse(URI.create(url));
                }
            }
        }
        catch (IOException e) {
            AuthorizationCodeInstalledApp.LOGGER.log(Level.WARNING, "Unable to open browser", e);
        }
        catch (InternalError e2) {
            AuthorizationCodeInstalledApp.LOGGER.log(Level.WARNING, "Unable to open browser", e2);
        }
    }
    
    public final AuthorizationCodeFlow getFlow() {
        return this.flow;
    }
    
    public final VerificationCodeReceiver getReceiver() {
        return this.receiver;
    }
    
    static {
        LOGGER = Logger.getLogger(AuthorizationCodeInstalledApp.class.getName());
    }
}
