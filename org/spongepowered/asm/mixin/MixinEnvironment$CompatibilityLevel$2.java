package org.spongepowered.asm.mixin;

import org.spongepowered.asm.util.*;

enum MixinEnvironment$CompatibilityLevel$2
{
    MixinEnvironment$CompatibilityLevel$2(final String x0, final int x2, final int ver, final int classVersion, final boolean resolveMethodsInInterfaces) {
    }
    
    @Override
    boolean isSupported() {
        return JavaVersion.current() >= 1.8;
    }
}