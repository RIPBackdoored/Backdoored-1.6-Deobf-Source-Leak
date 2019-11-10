package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.*;

enum ErrorPhase
{
    PREPARE {
        MixinTransformer$ErrorPhase$1(final String x0, final int x2) {
        }
        
        @Override
        IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler handler, final String context, final InvalidMixinException ex, final IMixinInfo mixin, final IMixinErrorHandler.ErrorAction action) {
            try {
                return handler.onPrepareError(mixin.getConfig(), ex, mixin, action);
            }
            catch (AbstractMethodError ame) {
                return action;
            }
        }
        
        @Override
        protected String getContext(final IMixinInfo mixin, final String context) {
            return String.format("preparing %s in %s", mixin.getName(), context);
        }
    }, 
    APPLY {
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
    };
    
    private final String text;
    private static final /* synthetic */ ErrorPhase[] $VALUES;
    
    public static ErrorPhase[] values() {
        return ErrorPhase.$VALUES.clone();
    }
    
    public static ErrorPhase valueOf(final String name) {
        return Enum.valueOf(ErrorPhase.class, name);
    }
    
    private ErrorPhase() {
        this.text = this.name().toLowerCase();
    }
    
    abstract IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler p0, final String p1, final InvalidMixinException p2, final IMixinInfo p3, final IMixinErrorHandler.ErrorAction p4);
    
    protected abstract String getContext(final IMixinInfo p0, final String p1);
    
    public String getLogMessage(final String context, final InvalidMixinException ex, final IMixinInfo mixin) {
        return String.format("Mixin %s failed %s: %s %s", this.text, this.getContext(mixin, context), ex.getClass().getName(), ex.getMessage());
    }
    
    public String getErrorMessage(final IMixinInfo mixin, final IMixinConfig config, final MixinEnvironment.Phase phase) {
        return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", mixin, phase, config, this.name());
    }
    
    ErrorPhase(final String x0, final int x1, final MixinTransformer$1 x2) {
        this();
    }
    
    static {
        $VALUES = new ErrorPhase[] { ErrorPhase.PREPARE, ErrorPhase.APPLY };
    }
}
