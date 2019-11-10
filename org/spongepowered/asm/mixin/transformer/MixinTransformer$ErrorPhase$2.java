package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;

enum MixinTransformer$ErrorPhase$2
{
    MixinTransformer$ErrorPhase$2(final String x0, final int x2) {
    }
    
    @Override
    IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler handler, final String context, final InvalidMixinException ex, final IMixinInfo mixin, final IMixinErrorHandler.ErrorAction action) {
        try {
            return handler.onApplyError(context, ex, mixin, action);
        }
        catch (AbstractMethodError ame) {
            return action;
        }
    }
    
    @Override
    protected String getContext(final IMixinInfo mixin, final String context) {
        return String.format("%s -> %s", mixin, context);
    }
}