package com.google.api.client.util;

import java.io.*;

@Beta
public final class BackOffUtils
{
    public static boolean next(final Sleeper sleeper, final BackOff backOff) throws InterruptedException, IOException {
        final long backOffTime = backOff.nextBackOffMillis();
        if (backOffTime == -1L) {
            return false;
        }
        sleeper.sleep(backOffTime);
        return true;
    }
    
    private BackOffUtils() {
        super();
    }
}
