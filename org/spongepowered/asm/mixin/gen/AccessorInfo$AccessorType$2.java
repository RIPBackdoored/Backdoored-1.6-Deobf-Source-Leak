package org.spongepowered.asm.mixin.gen;

import java.util.*;

enum AccessorInfo$AccessorType$2
{
    AccessorInfo$AccessorType$2(final String x0, final int x2, final Set expectedPrefixes) {
    }
    
    @Override
    AccessorGenerator getGenerator(final AccessorInfo info) {
        return new AccessorGeneratorFieldSetter(info);
    }
}