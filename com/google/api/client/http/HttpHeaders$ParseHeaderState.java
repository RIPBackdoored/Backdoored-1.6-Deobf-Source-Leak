package com.google.api.client.http;

import com.google.api.client.util.*;
import java.lang.reflect.*;
import java.util.*;

private static final class ParseHeaderState
{
    final ArrayValueMap arrayValueMap;
    final StringBuilder logger;
    final ClassInfo classInfo;
    final List<Type> context;
    
    public ParseHeaderState(final HttpHeaders headers, final StringBuilder logger) {
        super();
        final Class<? extends HttpHeaders> clazz = headers.getClass();
        this.context = Arrays.asList(clazz);
        this.classInfo = ClassInfo.of(clazz, true);
        this.logger = logger;
        this.arrayValueMap = new ArrayValueMap(headers);
    }
    
    void finish() {
        this.arrayValueMap.setValues();
    }
}
