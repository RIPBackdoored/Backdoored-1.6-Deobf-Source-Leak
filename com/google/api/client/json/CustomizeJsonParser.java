package com.google.api.client.json;

import com.google.api.client.util.*;
import java.lang.reflect.*;
import java.util.*;

@Beta
public class CustomizeJsonParser
{
    public CustomizeJsonParser() {
        super();
    }
    
    public boolean stopAt(final Object context, final String key) {
        return false;
    }
    
    public void handleUnrecognizedKey(final Object context, final String key) {
    }
    
    public Collection<Object> newInstanceForArray(final Object context, final Field field) {
        return null;
    }
    
    public Object newInstanceForObject(final Object context, final Class<?> fieldClass) {
        return null;
    }
}
