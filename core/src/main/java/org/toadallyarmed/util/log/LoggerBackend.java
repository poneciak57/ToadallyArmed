package org.toadallyarmed.util.log;

public interface LoggerBackend {
    void log(LogLevel level, String message);
}
