package org.spongepowered.asm.mixin.refmap;

import com.google.common.io.*;
import java.util.*;
import com.google.common.base.*;
import java.io.*;

static final class RemappingReferenceMapper$1 implements LineProcessor<Object> {
    final /* synthetic */ Map val$map;
    
    RemappingReferenceMapper$1(final Map val$map) {
        this.val$map = val$map;
        super();
    }
    
    public Object getResult() {
        return null;
    }
    
    public boolean processLine(final String line) throws IOException {
        if (Strings.isNullOrEmpty(line) || line.startsWith("#")) {
            return true;
        }
        final int fromPos = 0;
        int toPos = 0;
        int n2;
        final int n = line.startsWith("MD: ") ? (n2 = 2) : (line.startsWith("FD: ") ? (n2 = 1) : (n2 = 0));
        toPos = n2;
        if (n > 0) {
            final String[] entries = line.substring(4).split(" ", 4);
            this.val$map.put(entries[fromPos].substring(entries[fromPos].lastIndexOf(47) + 1), entries[toPos].substring(entries[toPos].lastIndexOf(47) + 1));
        }
        return true;
    }
}