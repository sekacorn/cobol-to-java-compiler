package compiler;

/**
 * Token - Represents a single token in the COBOL source code.
 */
public class Token {
    private final String type;
    private final String value;

    public Token(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String type() { return type; }
    public String value() { return value; }

    @Override
    public String toString() {
        return "Token{" + "type='" + type + '\'' + ", value='" + value + '\'' + '}';
    }
}
