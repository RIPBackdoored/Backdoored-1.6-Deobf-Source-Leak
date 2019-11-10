package com.google.api.client.util;

import java.util.logging.*;
import java.io.*;

public class LoggingInputStream extends FilterInputStream
{
    private final LoggingByteArrayOutputStream logStream;
    
    public LoggingInputStream(final InputStream inputStream, final Logger logger, final Level loggingLevel, final int contentLoggingLimit) {
        super(inputStream);
        this.logStream = new LoggingByteArrayOutputStream(logger, loggingLevel, contentLoggingLimit);
    }
    
    @Override
    public int read() throws IOException {
        final int read = super.read();
        this.logStream.write(read);
        return read;
    }
    
    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        final int read = super.read(b, off, len);
        if (read > 0) {
            this.logStream.write(b, off, read);
        }
        return read;
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
