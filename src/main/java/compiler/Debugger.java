package compiler;

/**
 * Debugger - Utility class for tracing execution.
 * This helps in debugging and can be enabled/disabled.
 */
public class Debugger {
    private static boolean DEBUG_MODE = false;

    /**
     * Enable debugging mode.
     */
    public static void enableDebugging() {
        DEBUG_MODE = true;
    }

    /**
     * Logs debugging messages.
     * @param message Debugging message.
     */
    public static void log(String message) {
        if (DEBUG_MODE) {
            System.out.println("[DEBUG] " + message);
        }
    }
}
