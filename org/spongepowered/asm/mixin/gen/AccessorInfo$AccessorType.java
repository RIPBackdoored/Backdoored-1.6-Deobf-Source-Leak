package org.spongepowered.asm.mixin.gen;

import java.util.*;
import com.google.common.collect.*;

public enum AccessorType
{
    FIELD_GETTER((Set)ImmutableSet.of((Object)"get", (Object)"is")) {
        AccessorInfo$AccessorType$1(final String x0, final int x2, final Set expectedPrefixes) {
        }
        
        @Override
        AccessorGenerator getGenerator(final AccessorInfo info) {
            return new AccessorGeneratorFieldGetter(info);
        }
    }, 
    FIELD_SETTER((Set)ImmutableSet.of("set")) {
        AccessorInfo$AccessorType$2(final String x0, final int x2, final Set expectedPrefixes) {
        }
        
        @Override
        AccessorGenerator getGenerator(final AccessorInfo info) {
            return new AccessorGeneratorFieldSetter(info);
        }
    }, 
    METHOD_PROXY((Set)ImmutableSet.of((Object)"call", (Object)"invoke")) {
        AccessorInfo$AccessorType$3(final String x0, final int x2, final Set expectedPrefixes) {
        }
        
        @Override
        AccessorGenerator getGenerator(final AccessorInfo info) {
            return new AccessorGeneratorMethodProxy(info);
        }
    };
    
    private final Set<String> expectedPrefixes;
    private static final /* synthetic */ AccessorType[] $VALUES;
    
    public static AccessorType[] values() {
        return AccessorType.$VALUES.clone();
    }
    
    public static AccessorType valueOf(final String name) {
        return Enum.valueOf(AccessorType.class, name);
    }
    
    private AccessorType(final Set<String> expectedPrefixes) {
        this.expectedPrefixes = expectedPrefixes;
    }
    
    public boolean isExpectedPrefix(final String prefix) {
        return this.expectedPrefixes.contains(prefix);
    }
    
    public String getExpectedPrefixes() {
        return this.expectedPrefixes.toString();
    }
    
    abstract AccessorGenerator getGenerator(final AccessorInfo p0);
    
    AccessorType(final String x0, final int x1, final Set x2, final AccessorInfo$1 x3) {
        this(x2);
    }
    
    static {
        $VALUES = new AccessorType[] { AccessorType.FIELD_GETTER, AccessorType.FIELD_SETTER, AccessorType.METHOD_PROXY };
    }
}
