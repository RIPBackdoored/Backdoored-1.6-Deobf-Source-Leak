package com.google.cloud.storage;

import java.io.*;
import com.google.cloud.storage.spi.v1.*;
import java.util.*;
import com.google.common.base.*;

public abstract class Option implements Serializable
{
    private static final long serialVersionUID = -73199088766477208L;
    private final StorageRpc.Option rpcOption;
    private final Object value;
    
    Option(final StorageRpc.Option rpcOption, final Object value) {
        super();
        this.rpcOption = Preconditions.checkNotNull(rpcOption);
        this.value = value;
    }
    
    StorageRpc.Option getRpcOption() {
        return this.rpcOption;
    }
    
    Object getValue() {
        return this.value;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Option)) {
            return false;
        }
        final Option other = (Option)obj;
        return Objects.equals(this.rpcOption, other.rpcOption) && Objects.equals(this.value, other.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.rpcOption, this.value);
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", this.rpcOption.value()).add("value", this.value).toString();
    }
}
