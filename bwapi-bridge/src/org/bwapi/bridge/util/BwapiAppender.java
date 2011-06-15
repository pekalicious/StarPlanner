package org.bwapi.bridge.util;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;
import org.bwapi.bridge.model.Game;


/**
 * Custom log4j appender for BWAPI
 * 
 * @author Chad Retz
 */
public class BwapiAppender extends AppenderSkeleton {

    @Override
    protected void append(LoggingEvent event) {
        if (layout == null) {
            errorHandler.error("No layout for appender " + name,
                    null, ErrorCode.MISSING_LAYOUT);
            return;
        }
        Game.getInstance().printf(layout.format(event));
        String[] throwable = event.getThrowableStrRep();
        if (throwable != null) {
            for (String piece : throwable) {
                Game.getInstance().printf("  " + piece);
            }
        }
    }

    @Override
    public void close() {
        
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }
}
