package org.toadallyarmed.util;

public class Debugging {
    public static boolean debuggingMode() {
        return "1".equals(System.getenv("DEBUG"));
    }
}
