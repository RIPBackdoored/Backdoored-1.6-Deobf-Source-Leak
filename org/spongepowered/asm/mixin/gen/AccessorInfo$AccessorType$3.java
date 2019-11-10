package org.spongepowered.asm.mixin.gen;

import java.util.*;

enum AccessorInfo$AccessorType$3
{
    AccessorInfo$AccessorType$3(final String x0, final int x2, final Set expectedPrefixes) {
    }
    
    @Override
    AccessorGenerator getGenerator(final AccessorInfo info) {
        return new AccessorGeneratorMethodProxy(info);
    }
}