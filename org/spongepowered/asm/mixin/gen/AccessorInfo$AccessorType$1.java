package org.spongepowered.asm.mixin.gen;

import java.util.*;

enum AccessorInfo$AccessorType$1
{
    AccessorInfo$AccessorType$1(final String x0, final int x2, final Set expectedPrefixes) {
    }
    
    @Override
    AccessorGenerator getGenerator(final AccessorInfo info) {
        return new AccessorGeneratorFieldGetter(info);
    }
}