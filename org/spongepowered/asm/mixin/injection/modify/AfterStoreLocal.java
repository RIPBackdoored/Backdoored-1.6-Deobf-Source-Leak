package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;

@AtCode("STORE")
public class AfterStoreLocal extends BeforeLoadLocal
{
    public AfterStoreLocal(final InjectionPointData data) {
        super(data, 54, true);
    }
}
