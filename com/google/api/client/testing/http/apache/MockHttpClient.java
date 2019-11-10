package com.google.api.client.testing.http.apache;

import org.apache.http.impl.client.*;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.*;
import org.apache.http.params.*;
import org.apache.http.client.*;
import org.apache.http.protocol.*;
import org.apache.http.message.*;
import org.apache.http.*;
import java.io.*;
import com.google.api.client.util.*;

@Beta
public class MockHttpClient extends DefaultHttpClient
{
    int responseCode;
    
    public MockHttpClient() {
        super();
    }
    
    protected RequestDirector createClientRequestDirector(final HttpRequestExecutor requestExec, final ClientConnectionManager conman, final ConnectionReuseStrategy reustrat, final ConnectionKeepAliveStrategy kastrat, final HttpRoutePlanner rouplan, final HttpProcessor httpProcessor, final HttpRequestRetryHandler retryHandler, final RedirectHandler redirectHandler, final AuthenticationHandler targetAuthHandler, final AuthenticationHandler proxyAuthHandler, final UserTokenHandler stateHandler, final HttpParams params) {
        return (RequestDirector)new RequestDirector() {
            final /* synthetic */ MockHttpClient this$0;
            
            MockHttpClient$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Beta
            public HttpResponse execute(final HttpHost target, final HttpRequest request, final HttpContext context) throws HttpException, IOException {
                return (HttpResponse)new BasicHttpResponse((ProtocolVersion)HttpVersion.HTTP_1_1, this.this$0.responseCode, (String)null);
            }
        };
    }
    
    public final int getResponseCode() {
        return this.responseCode;
    }
    
    public MockHttpClient setResponseCode(final int responseCode) {
        Preconditions.checkArgument(responseCode >= 0);
        this.responseCode = responseCode;
        return this;
    }
}
