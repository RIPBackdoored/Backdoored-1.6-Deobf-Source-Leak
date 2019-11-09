package com.sun.jna;

import java.util.*;

private static class LayoutInfo
{
    private int size;
    private int alignment;
    private final Map<String, StructField> fields;
    private int alignType;
    private TypeMapper typeMapper;
    private boolean variable;
    private StructField typeInfoField;
    
    private LayoutInfo() {
        super();
        this.size = -1;
        this.alignment = 1;
        this.fields = Collections.synchronizedMap(new LinkedHashMap<String, StructField>());
        this.alignType = 0;
    }
    
    static /* synthetic */ boolean access$000(final LayoutInfo x0) {
        return x0.variable;
    }
    
    static /* synthetic */ int access$100(final LayoutInfo x0) {
        return x0.size;
    }
    
    static /* synthetic */ int access$200(final LayoutInfo x0) {
        return x0.alignType;
    }
    
    static /* synthetic */ TypeMapper access$300(final LayoutInfo x0) {
        return x0.typeMapper;
    }
    
    static /* synthetic */ int access$400(final LayoutInfo x0) {
        return x0.alignment;
    }
    
    static /* synthetic */ Map access$500(final LayoutInfo x0) {
        return x0.fields;
    }
    
    LayoutInfo(final Structure$1 x0) {
        this();
    }
    
    static /* synthetic */ int access$202(final LayoutInfo x0, final int x1) {
        return x0.alignType = x1;
    }
    
    static /* synthetic */ TypeMapper access$302(final LayoutInfo x0, final TypeMapper x1) {
        return x0.typeMapper = x1;
    }
    
    static /* synthetic */ boolean access$002(final LayoutInfo x0, final boolean x1) {
        return x0.variable = x1;
    }
    
    static /* synthetic */ int access$402(final LayoutInfo x0, final int x1) {
        return x0.alignment = x1;
    }
    
    static /* synthetic */ StructField access$700(final LayoutInfo x0) {
        return x0.typeInfoField;
    }
    
    static /* synthetic */ StructField access$702(final LayoutInfo x0, final StructField x1) {
        return x0.typeInfoField = x1;
    }
    
    static /* synthetic */ int access$102(final LayoutInfo x0, final int x1) {
        return x0.size = x1;
    }
}
