package com.google.api.client.util;

import java.util.logging.*;
import java.io.*;

public class LoggingOutputStream extends FilterOutputStream
{
    private final LoggingByteArrayOutputStream logStream;
    
    public LoggingOutputStream(final OutputStream outputStream, final Logger logger, final Level loggingLevel, final int contentLoggingLimit) {
        super(outputStream);
        this.logStream = new LoggingByteArrayOutputStream(logger, loggingLevel, contentLoggingLimit);
    }
    
    @Override
    public void write(final int b) throws IOException {
        this.out.write(b);
        this.logStream.write(b);
    }
    
    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        this.out.write(b, off, len);
        this.logStream.write(b, off, len);
    }
    
    @Override
    public void close() throws IOException {
        this.logStream.close();
        super.close();
    }
    
    public final LoggingByteArrayOutputStream getLogStream() {
        return this.logStream;
    }
}
