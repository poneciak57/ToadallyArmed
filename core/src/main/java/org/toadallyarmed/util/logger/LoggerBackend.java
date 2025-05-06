package org.toadallyarmed.util.logger;

public interface LoggerBackend {
    void log(LogLevel level, String message);
}
