import org.junit.Test;
import org.toadallyarmed.util.logger.LogLevel;
import org.toadallyarmed.util.logger.Logger;
import org.toadallyarmed.util.logger.TerminalLoggerBackend;

import java.lang.reflect.Field;
import java.util.List;

/// These are for manuall tests only
public class LoggerTests {
    @Test
    public void testAllLoggerMethods() {
        clearLoggerInitState();
        Logger.init(List.of(new TerminalLoggerBackend(LogLevel.DEBUG)), LogLevel.DEBUG);
        Logger.debug("debug");
        Logger.info("info");
        Logger.warn("warn");
        Logger.error("error");
    }

    @Test
    public void testLogLevelFilter() {
        clearLoggerInitState();
        Logger.init(List.of(new TerminalLoggerBackend(LogLevel.DEBUG)), LogLevel.ERROR);
        Logger.debug("debug");
        Logger.info("info");
        Logger.warn("warn");
        Logger.error("error");

    }

    @Test
    public void testTerminalLoggerLogLevelFilter() {
        clearLoggerInitState();
        Logger.init(List.of(new TerminalLoggerBackend(LogLevel.ERROR)), LogLevel.INFO);
        Logger.debug("debug");
        Logger.info("info");
        Logger.warn("warn");
        Logger.error("error");
    }

    void clearLoggerInitState() {
        try {
            Field initState = Logger.class.getDeclaredField("initialized");
            initState.setAccessible(true);
            initState.set(null, false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
