package com.google.api.client.extensions.jetty.auth.oauth2;

import org.mortbay.jetty.handler.*;
import javax.servlet.http.*;
import org.mortbay.jetty.*;
import java.io.*;

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
            if (this.this$0.error == null && LocalServerReceiver.access$000(this.this$0) != null) {
                response.sendRedirect(LocalServerReceiver.access$000(this.this$0));
            }
            else if (this.this$0.error != null && LocalServerReceiver.access$100(this.this$0) != null) {
                response.sendRedirect(LocalServerReceiver.access$100(this.this$0));
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
