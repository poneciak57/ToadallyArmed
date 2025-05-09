package org.toadallyarmed.util.logger;

public enum LogLevel {
    TRACE(-1),
    DEBUG(0),
    INFO(1),
    WARN(2),
    ERROR(3);

    private final int level;

    LogLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
