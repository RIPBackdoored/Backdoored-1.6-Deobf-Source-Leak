package com.google.api.client.util;

import java.util.logging.*;
import java.io.*;

public final class LoggingStreamingContent implements StreamingContent
{
    private final StreamingContent content;
    private final int contentLoggingLimit;
    private final Level loggingLevel;
    private final Logger logger;
    
    public LoggingStreamingContent(final StreamingContent content, final Logger logger, final Level loggingLevel, final int contentLoggingLimit) {
        super();
        this.content = content;
        this.logger = logger;
        this.loggingLevel = loggingLevel;
        this.contentLoggingLimit = contentLoggingLimit;
    }
    
    @Override
    public void writeTo(final OutputStream out) throws IOException {
        final LoggingOutputStream loggableOutputStream = new LoggingOutputStream(out, this.logger, this.loggingLevel, this.contentLoggingLimit);
        try {
            this.content.writeTo(loggableOutputStream);
        }
        finally {
            loggableOutputStream.getLogStream().close();
        }
        out.flush();
    }
}
