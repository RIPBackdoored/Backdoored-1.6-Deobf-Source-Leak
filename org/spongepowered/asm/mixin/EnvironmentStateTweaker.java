package org.spongepowered.asm.mixin;

import java.util.*;
import java.io.*;
import net.minecraft.launchwrapper.*;
import org.spongepowered.asm.launch.*;

public class EnvironmentStateTweaker implements ITweaker
{
    public EnvironmentStateTweaker() {
        super();
    }
    
    public void acceptOptions(final List<String> args, final File gameDir, final File assetsDir, final String profile) {
    }
    
    public void injectIntoClassLoader(final LaunchClassLoader classLoader) {
        MixinBootstrap.getPlatform().inject();
    }
    
    public String getLaunchTarget() {
        return "";
    }
    
    public String[] getLaunchArguments() {
        MixinEnvironment.gotoPhase(MixinEnvironment.Phase.DEFAULT);
        return new String[0];
    }
}
