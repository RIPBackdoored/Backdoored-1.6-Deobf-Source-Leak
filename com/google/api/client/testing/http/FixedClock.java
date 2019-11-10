package com.google.api.client.testing.http;

import com.google.api.client.util.*;
import java.util.concurrent.atomic.*;

@Beta
public class FixedClock implements Clock
{
    private AtomicLong currentTime;
    
    public FixedClock() {
        this(0L);
    }
    
    public FixedClock(final long startTime) {
        super();
        this.currentTime = new AtomicLong(startTime);
    }
    
    public FixedClock setTime(final long newTime) {
        this.currentTime.set(newTime);
        return this;
    }
    
    @Override
    public long currentTimeMillis() {
        return this.currentTime.get();
    }
}
