package com.google.api.client.http;

import java.io.*;

class GZipEncoding$1 extends BufferedOutputStream {
    final /* synthetic */ GZipEncoding this$0;
    
    GZipEncoding$1(final GZipEncoding this$0, final OutputStream x0) {
        this.this$0 = this$0;
        super(x0);
    }
    
    @Override
    public void close() throws IOException {
        try {
            this.flush();
        }
        catch (IOException ex) {}
    }
}