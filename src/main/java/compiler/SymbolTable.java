package compiler;

import java.util.HashMap;

/**
 * SymbolTable - Manages COBOL variable declarations.
 */
public class SymbolTable {
    private static final HashMap<String, String> variables = new HashMap<>();

    /**
     * Declares a COBOL variable.
     * @param name Variable name.
     * @param type Variable type.
     */
    public static void declareVariable(String name, String type) {
        variables.put(name, type);
        Debugger.log("Declared variable: " + name + " of type " + type);
    }

    /**
     * Retrieves the type of a variable.
     * @param name Variable name.
     * @return Variable type.
     */
    public static String getType(String name) {
        return variables.getOrDefault(name, "UNKNOWN");
    }
}
