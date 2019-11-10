package com.google.api.client.http;

import java.io.*;
import com.google.api.client.util.*;

public abstract class AbstractInputStreamContent implements HttpContent
{
    private String type;
    private boolean closeInputStream;
    
    public AbstractInputStreamContent(final String type) {
        super();
        this.closeInputStream = true;
        this.setType(type);
    }
    
    public abstract InputStream getInputStream() throws IOException;
    
    @Override
    public void writeTo(final OutputStream out) throws IOException {
        IOUtils.copy(this.getInputStream(), out, this.closeInputStream);
        out.flush();
    }
    
    @Override
    public String getType() {
        return this.type;
    }
    
    public final boolean getCloseInputStream() {
        return this.closeInputStream;
    }
    
    public AbstractInputStreamContent setType(final String type) {
        this.type = type;
        return this;
    }
    
    public AbstractInputStreamContent setCloseInputStream(final boolean closeInputStream) {
        this.closeInputStream = closeInputStream;
        return this;
    }
}
