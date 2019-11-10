package com.google.cloud.storage.spi.v1;

import com.google.api.services.storage.model.*;
import java.util.*;

public static class RewriteResponse
{
    public final RewriteRequest rewriteRequest;
    public final StorageObject result;
    public final long blobSize;
    public final boolean isDone;
    public final String rewriteToken;
    public final long totalBytesRewritten;
    
    public RewriteResponse(final RewriteRequest rewriteRequest, final StorageObject result, final long blobSize, final boolean isDone, final String rewriteToken, final long totalBytesRewritten) {
        super();
        this.rewriteRequest = rewriteRequest;
        this.result = result;
        this.blobSize = blobSize;
        this.isDone = isDone;
        this.rewriteToken = rewriteToken;
        this.totalBytesRewritten = totalBytesRewritten;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RewriteResponse)) {
            return false;
        }
        final RewriteResponse other = (RewriteResponse)obj;
        return Objects.equals(this.rewriteRequest, other.rewriteRequest) && Objects.equals(this.result, other.result) && Objects.equals(this.rewriteToken, other.rewriteToken) && this.blobSize == other.blobSize && Objects.equals(this.isDone, other.isDone) && this.totalBytesRewritten == other.totalBytesRewritten;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.rewriteRequest, this.result, this.blobSize, this.isDone, this.rewriteToken, this.totalBytesRewritten);
    }
}
