package com.google.api.client.testing.util;

import java.util.logging.*;
import com.google.api.client.util.*;
import java.util.*;

@Beta
public class LogRecordingHandler extends Handler
{
    private final List<LogRecord> records;
    
    public LogRecordingHandler() {
        super();
        this.records = (List<LogRecord>)Lists.newArrayList();
    }
    
    @Override
    public void publish(final LogRecord record) {
        this.records.add(record);
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void close() throws SecurityException {
    }
    
    public List<String> messages() {
        final List<String> result = (List<String>)Lists.newArrayList();
        for (final LogRecord record : this.records) {
            result.add(record.getMessage());
        }
        return result;
    }
}
