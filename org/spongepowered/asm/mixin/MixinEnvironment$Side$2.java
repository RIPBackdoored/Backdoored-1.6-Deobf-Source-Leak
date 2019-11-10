package org.spongepowered.asm.mixin;

import org.spongepowered.asm.service.*;

enum MixinEnvironment$Side$2
{
    MixinEnvironment$Side$2(final String x0, final int x2) {
    }
    
    @Override
    protected boolean detect() {
        final String sideName = MixinService.getService().getSideName();
        return "CLIENT".equals(sideName);
    }
}