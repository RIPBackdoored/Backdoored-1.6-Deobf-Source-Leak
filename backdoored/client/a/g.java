package f.b.a;

import org.spongepowered.asm.mixin.injection.callback.*;

public class g extends b
{
    public CallbackInfoReturnable<Boolean> bu;
    
    public g(final CallbackInfoReturnable<Boolean> v) {
        super();
        this.bu = v;
    }
}
