package com.google.cloud.storage.spi.v1;

import com.google.api.services.storage.model.*;
import java.util.*;

public static class RewriteRequest
{
    public final StorageObject source;
    public final Map<Option, ?> sourceOptions;
    public final boolean overrideInfo;
    public final StorageObject target;
    public final Map<Option, ?> targetOptions;
    public final Long megabytesRewrittenPerCall;
    
    public RewriteRequest(final StorageObject source, final Map<Option, ?> sourceOptions, final boolean overrideInfo, final StorageObject target, final Map<Option, ?> targetOptions, final Long megabytesRewrittenPerCall) {
        super();
        this.source = source;
        this.sourceOptions = sourceOptions;
        this.overrideInfo = overrideInfo;
        this.target = target;
        this.targetOptions = targetOptions;
        this.megabytesRewrittenPerCall = megabytesRewrittenPerCall;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RewriteRequest)) {
            return false;
        }
        final RewriteRequest other = (RewriteRequest)obj;
        return Objects.equals(this.source, other.source) && Objects.equals(this.sourceOptions, other.sourceOptions) && Objects.equals(this.overrideInfo, other.overrideInfo) && Objects.equals(this.target, other.target) && Objects.equals(this.targetOptions, other.targetOptions) && Objects.equals(this.megabytesRewrittenPerCall, other.megabytesRewrittenPerCall);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.source, this.sourceOptions, this.overrideInfo, this.target, this.targetOptions, this.megabytesRewrittenPerCall);
    }
}
