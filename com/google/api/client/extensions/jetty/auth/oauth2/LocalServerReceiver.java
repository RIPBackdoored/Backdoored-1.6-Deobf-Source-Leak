package com.google.api.client.extensions.jetty.auth.oauth2;

import com.google.api.client.extensions.java6.auth.oauth2.*;
import java.util.concurrent.*;
import com.google.api.client.util.*;
import org.mortbay.jetty.handler.*;
import javax.servlet.http.*;
import org.mortbay.jetty.*;
import java.io.*;

public final class LocalServerReceiver implements VerificationCodeReceiver
{
    private static final String LOCALHOST = "localhost";
    private static final String CALLBACK_PATH = "/Callback";
    private Server server;
    String code;
    String error;
    final Semaphore waitUnlessSignaled;
    private int port;
    private final String host;
    private final String callbackPath;
    private String successLandingPageUrl;
    private String failureLandingPageUrl;
    
    public LocalServerReceiver() {
        this("localhost", -1, "/Callback", null, null);
    }
    
    LocalServerReceiver(final String host, final int port, final String successLandingPageUrl, final String failureLandingPageUrl) {
        this(host, port, "/Callback", successLandingPageUrl, failureLandingPageUrl);
    }
    
    LocalServerReceiver(final String host, final int port, final String callbackPath, final String successLandingPageUrl, final String failureLandingPageUrl) {
        super();
        this.waitUnlessSignaled = new Semaphore(0);
        this.host = host;
        this.port = port;
        this.callbackPath = callbackPath;
        this.successLandingPageUrl = successLandingPageUrl;
        this.failureLandingPageUrl = failureLandingPageUrl;
    }
    
    public String getRedirectUri() throws IOException {
        this.server = new Server((this.port != -1) ? this.port : 0);
        final Connector connector = this.server.getConnectors()[0];
        connector.setHost(this.host);
        this.server.addHandler((Handler)new CallbackHandler());
        try {
            this.server.start();
            this.port = connector.getLocalPort();
        }
        catch (Exception e) {
            Throwables.propagateIfPossible(e);
            throw new IOException(e);
        }
        final String value = String.valueOf(String.valueOf(this.host));
        final int port = this.port;
        final String value2 = String.valueOf(String.valueOf(this.callbackPath));
        return new StringBuilder(19 + value.length() + value2.length()).append("http://").append(value).append(":").append(port).append(value2).toString();
    }
    
    public String waitForCode() throws IOException {
        this.waitUnlessSignaled.acquireUninterruptibly();
        if (this.error != null) {
            final String value = String.valueOf(String.valueOf(this.error));
            throw new IOException(new StringBuilder(28 + value.length()).append("User authorization failed (").append(value).append(")").toString());
        }
        return this.code;
    }
    
    public void stop() throws IOException {
        this.waitUnlessSignaled.release();
        if (this.server != null) {
            try {
                this.server.stop();
            }
            catch (Exception e) {
                Throwables.propagateIfPossible(e);
                throw new IOException(e);
            }
            this.server = null;
        }
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getCallbackPath() {
        return this.callbackPath;
    }
    
    static /* synthetic */ String access$000(final LocalServerReceiver x0) {
        return x0.successLandingPageUrl;
    }
    
    static /* synthetic */ String access$100(final LocalServerReceiver x0) {
        return x0.failureLandingPageUrl;
    }
    
    public static final class Builder
    {
        private String host;
        private int port;
        private String successLandingPageUrl;
        private String failureLandingPageUrl;
        private String callbackPath;
        
        public Builder() {
            super();
            this.host = "localhost";
            this.port = -1;
            this.callbackPath = "/Callback";
        }
        
        public LocalServerReceiver build() {
            return new LocalServerReceiver(this.host, this.port, this.callbackPath, this.successLandingPageUrl, this.failureLandingPageUrl);
        }
        
        public String getHost() {
            return this.host;
        }
        
        public Builder setHost(final String host) {
            this.host = host;
            return this;
        }
        
        public int getPort() {
            return this.port;
        }
        
        public Builder setPort(final int port) {
            this.port = port;
            return this;
        }
        
        public String getCallbackPath() {
            return this.callbackPath;
        }
        
        public Builder setCallbackPath(final String callbackPath) {
            this.callbackPath = callbackPath;
            return this;
        }
        
        public Builder setLandingPages(final String successLandingPageUrl, final String failureLandingPageUrl) {
            this.successLandingPageUrl = successLandingPageUrl;
            this.failureLandingPageUrl = failureLandingPageUrl;
            return this;
        }
    }
    
    class CallbackHandler extends AbstractHandler
    {
        final /* synthetic */ LocalServerReceiver this$0;
        
        CallbackHandler(final LocalServerReceiver this$0) {
            this.this$0 = this$0;
            super();
        }
        
        public void handle(final String target, final HttpServletRequest request, final HttpServletResponse response, final int dispatch) throws IOException {
            if (!"/Callback".equals(target)) {
                return;
            }
            try {
                ((Request)request).setHandled(true);
                this.this$0.error = request.getParameter("error");
                this.this$0.code = request.getParameter("code");
                if (this.this$0.error == null && this.this$0.successLandingPageUrl != null) {
                    response.sendRedirect(this.this$0.successLandingPageUrl);
                }
                else if (this.this$0.error != null && this.this$0.failureLandingPageUrl != null) {
                    response.sendRedirect(this.this$0.failureLandingPageUrl);
                }
                else {
                    this.writeLandingHtml(response);
                }
                response.flushBuffer();
            }
            finally {
                this.this$0.waitUnlessSignaled.release();
            }
        }
        
        private void writeLandingHtml(final HttpServletResponse response) throws IOException {
            response.setStatus(200);
            response.setContentType("text/html");
            final PrintWriter doc = response.getWriter();
            doc.println("<html>");
            doc.println("<head><title>OAuth 2.0 Authentication Token Received</title></head>");
            doc.println("<body>");
            doc.println("Received verification code. You may now close this window.");
            doc.println("</body>");
            doc.println("</html>");
            doc.flush();
        }
    }
}
