package org.spongepowered.asm.mixin;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.CLASS)
public @interface Interface {
    Class<?> iface();
    
    String prefix();
    
    boolean unique() default false;
    
    Remap remap() default Remap.ALL;
    
    public enum Remap
    {
        ALL, 
        FORCE(true), 
        ONLY_PREFIXED, 
        NONE;
        
        private final boolean forceRemap;
        private static final /* synthetic */ Remap[] $VALUES;
        
        public static Remap[] values() {
            return Remap.$VALUES.clone();
        }
        
        public static Remap valueOf(final String name) {
            return Enum.valueOf(Remap.class, name);
        }
        
        private Remap() {
            this(false);
        }
        
        private Remap(final boolean forceRemap) {
            this.forceRemap = forceRemap;
        }
        
        public boolean forceRemap() {
            return this.forceRemap;
        }
        
        static {
            $VALUES = new Remap[] { Remap.ALL, Remap.FORCE, Remap.ONLY_PREFIXED, Remap.NONE };
        }
    }
}
