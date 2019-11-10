package com.google.cloud.storage;

import com.google.api.services.storage.model.*;
import java.io.*;
import com.google.api.client.json.jackson2.*;

static class RawDeleteRule extends DeleteRule
{
    private static final long serialVersionUID = -7166938278642301933L;
    private transient Bucket.Lifecycle.Rule rule;
    
    RawDeleteRule(final Bucket.Lifecycle.Rule rule) {
        super(Type.UNKNOWN);
        this.rule = rule;
    }
    
    @Override
    void populateCondition(final Bucket.Lifecycle.Rule.Condition condition) {
        throw new UnsupportedOperationException();
    }
    
    private void writeObject(final ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(this.rule.toString());
    }
    
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.rule = new JacksonFactory().fromString(in.readUTF(), Bucket.Lifecycle.Rule.class);
    }
    
    @Override
    Bucket.Lifecycle.Rule toPb() {
        return this.rule;
    }
}
