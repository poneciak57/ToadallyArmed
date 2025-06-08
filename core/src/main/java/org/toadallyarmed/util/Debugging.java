package org.toadallyarmed.util;

public class Debugging {
    public static boolean debuggingMode() {
        return "1".equals(System.getenv("DEBUG"));
    }
    public static boolean stressTestMode() { return "1".equals(System.getenv("STRESS_TEST")); }
}
