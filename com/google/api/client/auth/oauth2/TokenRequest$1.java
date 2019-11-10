package com.google.api.client.auth.oauth2;

import com.google.api.client.http.*;
import java.io.*;

class TokenRequest$1 implements HttpRequestInitializer {
    final /* synthetic */ TokenRequest this$0;
    
    TokenRequest$1(final TokenRequest this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void initialize(final HttpRequest request) throws IOException {
        if (this.this$0.requestInitializer != null) {
            this.this$0.requestInitializer.initialize(request);
        }
        final HttpExecuteInterceptor interceptor = request.getInterceptor();
        request.setInterceptor(new HttpExecuteInterceptor() {
            final /* synthetic */ HttpExecuteInterceptor val$interceptor;
            final /* synthetic */ TokenRequest$1 this$1;
            
            TokenRequest$1$1() {
                this.this$1 = this$1;
                super();
            }
            
            @Override
            public void intercept(final HttpRequest request) throws IOException {
                if (interceptor != null) {
                    interceptor.intercept(request);
                }
                if (this.this$1.this$0.clientAuthentication != null) {
                    this.this$1.this$0.clientAuthentication.intercept(request);
                }
            }
        });
    }
}