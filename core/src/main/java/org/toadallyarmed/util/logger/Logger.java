package org.toadallyarmed.util.logger;

import org.lwjgl.Sys;

import java.util.List;

public class Logger {
    static List<LoggerBackend> backends = List.of();
    static LogLevel overallLevelFilter = LogLevel.INFO;
    static boolean initialized = false;

    public static void init(List<LoggerBackend> backends, LogLevel overallLevelFilter) {
        if (initialized) {
            throw new IllegalStateException("Logger can be initialized only once");
        }
        initialized = true;
        Logger.backends = backends;
        Logger.overallLevelFilter = overallLevelFilter;
    }

    public static void log(LogLevel level, String message) {
        if (overallLevelFilter.getLevel() > level.getLevel()) return;
        for (LoggerBackend backend : backends) {
            backend.log(level, message);
        }
    }

    public static void trace(String msg) {
        log(LogLevel.TRACE, msg);
    }

    public static void debug(String msg) {
        log(LogLevel.DEBUG, msg);
    }

    public static void info(String msg) {
        log(LogLevel.INFO, msg);
    }

    public static void warn(String msg) {
        log(LogLevel.WARN, msg);
    }

    public static void error(String msg) {
        log(LogLevel.ERROR, msg);
    }

    public static void errorIfNot(boolean condition, String msg) {
        if (!condition)
            error(msg);
    }
}
