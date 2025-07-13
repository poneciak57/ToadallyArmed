package org.toadallyarmed.util.log;

import com.badlogic.gdx.Gdx;

public class LibgdxLoggerBackend implements LoggerBackend{
    @Override
    public void log(LogLevel level, String message) {
        Gdx.app.log(level.name(), message);
    }
}
