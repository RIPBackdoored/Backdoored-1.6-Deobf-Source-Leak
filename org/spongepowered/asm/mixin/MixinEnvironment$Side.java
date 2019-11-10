package org.spongepowered.asm.mixin;

import org.spongepowered.asm.service.*;

public enum Side
{
    UNKNOWN {
        MixinEnvironment$Side$1(final String x0, final int x2) {
        }
        
        @Override
        protected boolean detect() {
            return false;
        }
    }, 
    CLIENT {
        MixinEnvironment$Side$2(final String x0, final int x2) {
        }
        
        @Override
        protected boolean detect() {
            final String sideName = MixinService.getService().getSideName();
            return "CLIENT".equals(sideName);
        }
    }, 
    SERVER {
        MixinEnvironment$Side$3(final String x0, final int x2) {
        }
        
        @Override
        protected boolean detect() {
            final String sideName = MixinService.getService().getSideName();
            return "SERVER".equals(sideName) || "DEDICATEDSERVER".equals(sideName);
        }
    };
    
    private static final /* synthetic */ Side[] $VALUES;
    
    public static Side[] values() {
        return Side.$VALUES.clone();
    }
    
    public static Side valueOf(final String name) {
        return Enum.valueOf(Side.class, name);
    }
    
    protected abstract boolean detect();
    
    Side(final String x0, final int x1, final MixinEnvironment$1 x2) {
        this();
    }
    
    static {
        $VALUES = new Side[] { Side.UNKNOWN, Side.CLIENT, Side.SERVER };
    }
}
