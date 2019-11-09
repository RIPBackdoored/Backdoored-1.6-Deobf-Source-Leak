package com.fasterxml.jackson.core;

public enum NumberType
{
    INT, 
    LONG, 
    BIG_INTEGER, 
    FLOAT, 
    DOUBLE, 
    BIG_DECIMAL;
    
    private static final /* synthetic */ NumberType[] $VALUES;
    
    public static NumberType[] values() {
        return NumberType.$VALUES.clone();
    }
    
    public static NumberType valueOf(final String name) {
        return Enum.valueOf(NumberType.class, name);
    }
    
    static {
        $VALUES = new NumberType[] { NumberType.INT, NumberType.LONG, NumberType.BIG_INTEGER, NumberType.FLOAT, NumberType.DOUBLE, NumberType.BIG_DECIMAL };
    }
}
