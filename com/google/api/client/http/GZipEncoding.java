package com.google.api.client.http;

import com.google.api.client.util.*;
import java.io.*;
import java.util.zip.*;

public class GZipEncoding implements HttpEncoding
{
    public GZipEncoding() {
        super();
    }
    
    @Override
    public String getName() {
        return "gzip";
    }
    
    @Override
    public void encode(final StreamingContent content, final OutputStream out) throws IOException {
        final OutputStream out2 = new BufferedOutputStream(out) {
            final /* synthetic */ GZipEncoding this$0;
            
            GZipEncoding$1(final OutputStream x0) {
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
        };
        final GZIPOutputStream zipper = new GZIPOutputStream(out2);
        content.writeTo(zipper);
        zipper.close();
    }
}
