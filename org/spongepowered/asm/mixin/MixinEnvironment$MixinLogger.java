package org.spongepowered.asm.mixin;

import java.io.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;

static class MixinLogger
{
    static MixinAppender appender;
    
    public MixinLogger() {
        super();
        final Logger log = (Logger)LogManager.getLogger("FML");
        MixinLogger.appender.start();
        log.addAppender((Appender)MixinLogger.appender);
    }
    
    static {
        MixinLogger.appender = new MixinAppender("MixinLogger", null, null);
    }
    
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
}
