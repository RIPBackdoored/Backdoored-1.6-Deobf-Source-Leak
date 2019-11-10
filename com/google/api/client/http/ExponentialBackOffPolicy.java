package com.google.api.client.http;

import java.io.*;
import com.google.api.client.util.*;

@Deprecated
@Beta
public class ExponentialBackOffPolicy implements BackOffPolicy
{
    public static final int DEFAULT_INITIAL_INTERVAL_MILLIS = 500;
    public static final double DEFAULT_RANDOMIZATION_FACTOR = 0.5;
    public static final double DEFAULT_MULTIPLIER = 1.5;
    public static final int DEFAULT_MAX_INTERVAL_MILLIS = 60000;
    public static final int DEFAULT_MAX_ELAPSED_TIME_MILLIS = 900000;
    private final ExponentialBackOff exponentialBackOff;
    
    public ExponentialBackOffPolicy() {
        this(new Builder());
    }
    
    protected ExponentialBackOffPolicy(final Builder builder) {
        super();
        this.exponentialBackOff = builder.exponentialBackOffBuilder.build();
    }
    
    @Override
    public boolean isBackOffRequired(final int statusCode) {
        switch (statusCode) {
            case 500:
            case 503: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    @Override
    public final void reset() {
        this.exponentialBackOff.reset();
    }
    
    @Override
    public long getNextBackOffMillis() throws IOException {
        return this.exponentialBackOff.nextBackOffMillis();
    }
    
    public final int getInitialIntervalMillis() {
        return this.exponentialBackOff.getInitialIntervalMillis();
    }
    
    public final double getRandomizationFactor() {
        return this.exponentialBackOff.getRandomizationFactor();
    }
    
    public final int getCurrentIntervalMillis() {
        return this.exponentialBackOff.getCurrentIntervalMillis();
    }
    
    public final double getMultiplier() {
        return this.exponentialBackOff.getMultiplier();
    }
    
    public final int getMaxIntervalMillis() {
        return this.exponentialBackOff.getMaxIntervalMillis();
    }
    
    public final int getMaxElapsedTimeMillis() {
        return this.exponentialBackOff.getMaxElapsedTimeMillis();
    }
    
    public final long getElapsedTimeMillis() {
        return this.exponentialBackOff.getElapsedTimeMillis();
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    @Deprecated
    @Beta
    public static class Builder
    {
        final ExponentialBackOff.Builder exponentialBackOffBuilder;
        
        protected Builder() {
            super();
            this.exponentialBackOffBuilder = new ExponentialBackOff.Builder();
        }
        
        public ExponentialBackOffPolicy build() {
            return new ExponentialBackOffPolicy(this);
        }
        
        public final int getInitialIntervalMillis() {
            return this.exponentialBackOffBuilder.getInitialIntervalMillis();
        }
        
        public Builder setInitialIntervalMillis(final int initialIntervalMillis) {
            this.exponentialBackOffBuilder.setInitialIntervalMillis(initialIntervalMillis);
            return this;
        }
        
        public final double getRandomizationFactor() {
            return this.exponentialBackOffBuilder.getRandomizationFactor();
        }
        
        public Builder setRandomizationFactor(final double randomizationFactor) {
            this.exponentialBackOffBuilder.setRandomizationFactor(randomizationFactor);
            return this;
        }
        
        public final double getMultiplier() {
            return this.exponentialBackOffBuilder.getMultiplier();
        }
        
        public Builder setMultiplier(final double multiplier) {
            this.exponentialBackOffBuilder.setMultiplier(multiplier);
            return this;
        }
        
        public final int getMaxIntervalMillis() {
            return this.exponentialBackOffBuilder.getMaxIntervalMillis();
        }
        
        public Builder setMaxIntervalMillis(final int maxIntervalMillis) {
            this.exponentialBackOffBuilder.setMaxIntervalMillis(maxIntervalMillis);
            return this;
        }
        
        public final int getMaxElapsedTimeMillis() {
            return this.exponentialBackOffBuilder.getMaxElapsedTimeMillis();
        }
        
        public Builder setMaxElapsedTimeMillis(final int maxElapsedTimeMillis) {
            this.exponentialBackOffBuilder.setMaxElapsedTimeMillis(maxElapsedTimeMillis);
            return this;
        }
        
        public final NanoClock getNanoClock() {
            return this.exponentialBackOffBuilder.getNanoClock();
        }
        
        public Builder setNanoClock(final NanoClock nanoClock) {
            this.exponentialBackOffBuilder.setNanoClock(nanoClock);
            return this;
        }
    }
}
