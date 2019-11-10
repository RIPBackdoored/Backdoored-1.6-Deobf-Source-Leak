package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.ext.*;

class MixinTransformer$1 implements MixinConfig.IListener {
    final /* synthetic */ IHotSwap val$hotSwapper;
    final /* synthetic */ MixinTransformer this$0;
    
    MixinTransformer$1(final MixinTransformer this$0, final IHotSwap val$hotSwapper) {
        this.this$0 = this$0;
        this.val$hotSwapper = val$hotSwapper;
        super();
    }
    
    @Override
    public void onPrepare(final MixinInfo mixin) {
        this.val$hotSwapper.registerMixinClass(mixin.getClassName());
    }
    
    @Override
    public void onInit(final MixinInfo mixin) {
    }
}