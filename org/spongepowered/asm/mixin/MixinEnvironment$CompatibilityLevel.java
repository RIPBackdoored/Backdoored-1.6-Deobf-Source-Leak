package org.spongepowered.asm.mixin;

import org.spongepowered.asm.util.*;

public enum CompatibilityLevel
{
    JAVA_6(6, 50, false), 
    JAVA_7(7, 51, false) {
        MixinEnvironment$CompatibilityLevel$1(final String x0, final int x2, final int ver, final int classVersion, final boolean resolveMethodsInInterfaces) {
        }
        
        @Override
        boolean isSupported() {
            return JavaVersion.current() >= 1.7;
        }
    }, 
    JAVA_8(8, 52, true) {
        MixinEnvironment$CompatibilityLevel$2(final String x0, final int x2, final int ver, final int classVersion, final boolean resolveMethodsInInterfaces) {
        }
        
        @Override
        boolean isSupported() {
            return JavaVersion.current() >= 1.8;
        }
    }, 
    JAVA_9(9, 53, true) {
        MixinEnvironment$CompatibilityLevel$3(final String x0, final int x2, final int ver, final int classVersion, final boolean resolveMethodsInInterfaces) {
        }
        
        @Override
        boolean isSupported() {
            return false;
        }
    };
    
    private static final int CLASS_V1_9 = 53;
    private final int ver;
    private final int classVersion;
    private final boolean supportsMethodsInInterfaces;
    private CompatibilityLevel maxCompatibleLevel;
    private static final /* synthetic */ CompatibilityLevel[] $VALUES;
    
    public static CompatibilityLevel[] values() {
        return CompatibilityLevel.$VALUES.clone();
    }
    
    public static CompatibilityLevel valueOf(final String name) {
        return Enum.valueOf(CompatibilityLevel.class, name);
    }
    
    private CompatibilityLevel(final int ver, final int classVersion, final boolean resolveMethodsInInterfaces) {
        this.ver = ver;
        this.classVersion = classVersion;
        this.supportsMethodsInInterfaces = resolveMethodsInInterfaces;
    }
    
    private void setMaxCompatibleLevel(final CompatibilityLevel maxCompatibleLevel) {
        this.maxCompatibleLevel = maxCompatibleLevel;
    }
    
    boolean isSupported() {
        return true;
    }
    
    public int classVersion() {
        return this.classVersion;
    }
    
    public boolean supportsMethodsInInterfaces() {
        return this.supportsMethodsInInterfaces;
    }
    
    public boolean isAtLeast(final CompatibilityLevel level) {
        return level == null || this.ver >= level.ver;
    }
    
    public boolean canElevateTo(final CompatibilityLevel level) {
        return level == null || this.maxCompatibleLevel == null || level.ver <= this.maxCompatibleLevel.ver;
    }
    
    public boolean canSupport(final CompatibilityLevel level) {
        return level == null || level.canElevateTo(this);
    }
    
    CompatibilityLevel(final String x0, final int x1, final int x2, final int x3, final boolean x4, final MixinEnvironment$1 x5) {
        this(x2, x3, x4);
    }
    
    static {
        $VALUES = new CompatibilityLevel[] { CompatibilityLevel.JAVA_6, CompatibilityLevel.JAVA_7, CompatibilityLevel.JAVA_8, CompatibilityLevel.JAVA_9 };
    }
}
