package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.*;
import java.io.*;

public static class FixedSpaceIndenter extends NopIndenter
{
    public static final FixedSpaceIndenter instance;
    
    public FixedSpaceIndenter() {
        super();
    }
    
    @Override
    public void writeIndentation(final JsonGenerator g, final int level) throws IOException {
        g.writeRaw(' ');
    }
    
    @Override
    public boolean isInline() {
        return true;
    }
    
    static {
        instance = new FixedSpaceIndenter();
    }
}
