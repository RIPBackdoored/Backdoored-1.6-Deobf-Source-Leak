package org.spongepowered.asm.mixin;

import org.apache.logging.log4j.core.appender.*;
import java.io.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;

static class MixinAppender extends AbstractAppender
{
    protected MixinAppender(final String name, final Filter filter, final Layout<? extends Serializable> layout) {
        super(name, filter, (Layout)layout);
    }
    
    public void append(final LogEvent event) {
        if (event.getLevel() == Level.DEBUG && "Validating minecraft".equals(event.getMessage().getFormat())) {
            MixinEnvironment.gotoPhase(Phase.INIT);
        }
    }
}
