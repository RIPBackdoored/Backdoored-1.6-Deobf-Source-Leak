package com.backdoored.mixin;

import net.minecraft.launchwrapper.*;

public class ClassTransformer implements IClassTransformer
{
    public ClassTransformer() {
        super();
    }
    
    public byte[] transform(final String className, final String transformedName, final byte[] classfileBuffer) {
        return classfileBuffer;
    }
}
