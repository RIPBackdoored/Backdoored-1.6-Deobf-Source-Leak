package com.google.api.client.googleapis.batch;

import com.google.api.client.http.*;
import java.util.*;
import java.io.*;

class BatchInterceptor implements HttpExecuteInterceptor
{
    private HttpExecuteInterceptor originalInterceptor;
    final /* synthetic */ BatchRequest this$0;
    
    BatchInterceptor(final BatchRequest this$0, final HttpExecuteInterceptor originalInterceptor) {
        this.this$0 = this$0;
        super();
        this.originalInterceptor = originalInterceptor;
    }
    
    public void intercept(final HttpRequest batchRequest) throws IOException {
        if (this.originalInterceptor != null) {
            this.originalInterceptor.intercept(batchRequest);
        }
        for (final RequestInfo<?, ?> requestInfo : this.this$0.requestInfos) {
            final HttpExecuteInterceptor interceptor = requestInfo.request.getInterceptor();
            if (interceptor != null) {
                interceptor.intercept(requestInfo.request);
            }
        }
    }
}
