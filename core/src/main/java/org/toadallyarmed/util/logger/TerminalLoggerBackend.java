package org.toadallyarmed.util.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TerminalLoggerBackend implements LoggerBackend {
    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";

    private final LogLevel logLevel;

    public TerminalLoggerBackend(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public void log(LogLevel level, String message) {
        if (logLevel.getLevel() > level.getLevel()) return;

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        String threadName = Thread.currentThread().getName();
        String color = switch (level) {
            case DEBUG -> CYAN;
            case INFO -> BLUE;
            case WARN -> YELLOW;
            case ERROR -> RED;
        };

        String formatted = String.format(
            "%s[%s] [%s] [%s] %s%s",
            color, timestamp, threadName, level, message, RESET
        );

        System.out.println(formatted);
    }
}
