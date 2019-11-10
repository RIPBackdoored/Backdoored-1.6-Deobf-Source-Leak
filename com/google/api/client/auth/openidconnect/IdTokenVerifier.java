package com.google.api.client.auth.openidconnect;

import java.util.*;
import com.google.api.client.util.*;

@Beta
public class IdTokenVerifier
{
    public static final long DEFAULT_TIME_SKEW_SECONDS = 300L;
    private final Clock clock;
    private final long acceptableTimeSkewSeconds;
    private final Collection<String> issuers;
    private final Collection<String> audience;
    
    public IdTokenVerifier() {
        this(new Builder());
    }
    
    protected IdTokenVerifier(final Builder builder) {
        super();
        this.clock = builder.clock;
        this.acceptableTimeSkewSeconds = builder.acceptableTimeSkewSeconds;
        this.issuers = ((builder.issuers == null) ? null : Collections.unmodifiableCollection((Collection<? extends String>)builder.issuers));
        this.audience = ((builder.audience == null) ? null : Collections.unmodifiableCollection((Collection<? extends String>)builder.audience));
    }
    
    public final Clock getClock() {
        return this.clock;
    }
    
    public final long getAcceptableTimeSkewSeconds() {
        return this.acceptableTimeSkewSeconds;
    }
    
    public final String getIssuer() {
        if (this.issuers == null) {
            return null;
        }
        return this.issuers.iterator().next();
    }
    
    public final Collection<String> getIssuers() {
        return this.issuers;
    }
    
    public final Collection<String> getAudience() {
        return this.audience;
    }
    
    public boolean verify(final IdToken idToken) {
        return (this.issuers == null || idToken.verifyIssuer(this.issuers)) && (this.audience == null || idToken.verifyAudience(this.audience)) && idToken.verifyTime(this.clock.currentTimeMillis(), this.acceptableTimeSkewSeconds);
    }
    
    @Beta
    public static class Builder
    {
        Clock clock;
        long acceptableTimeSkewSeconds;
        Collection<String> issuers;
        Collection<String> audience;
        
        public Builder() {
            super();
            this.clock = Clock.SYSTEM;
            this.acceptableTimeSkewSeconds = 300L;
        }
        
        public IdTokenVerifier build() {
            return new IdTokenVerifier(this);
        }
        
        public final Clock getClock() {
            return this.clock;
        }
        
        public Builder setClock(final Clock clock) {
            this.clock = Preconditions.checkNotNull(clock);
            return this;
        }
        
        public final String getIssuer() {
            if (this.issuers == null) {
                return null;
            }
            return this.issuers.iterator().next();
        }
        
        public Builder setIssuer(final String issuer) {
            if (issuer == null) {
                return this.setIssuers(null);
            }
            return this.setIssuers(Collections.singleton(issuer));
        }
        
        public final Collection<String> getIssuers() {
            return this.issuers;
        }
        
        public Builder setIssuers(final Collection<String> issuers) {
            Preconditions.checkArgument(issuers == null || !issuers.isEmpty(), (Object)"Issuers must not be empty");
            this.issuers = issuers;
            return this;
        }
        
        public final Collection<String> getAudience() {
            return this.audience;
        }
        
        public Builder setAudience(final Collection<String> audience) {
            this.audience = audience;
            return this;
        }
        
        public final long getAcceptableTimeSkewSeconds() {
            return this.acceptableTimeSkewSeconds;
        }
        
        public Builder setAcceptableTimeSkewSeconds(final long acceptableTimeSkewSeconds) {
            Preconditions.checkArgument(acceptableTimeSkewSeconds >= 0L);
            this.acceptableTimeSkewSeconds = acceptableTimeSkewSeconds;
            return this;
        }
    }
}
