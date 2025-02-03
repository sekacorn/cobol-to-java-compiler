package compiler;

import java.util.regex.Pattern;

/**
 * TokenDefinition - Defines a single COBOL token with a type and regex pattern.
 * This is used by the lexer to recognize different parts of COBOL syntax.
 */
public class TokenDefinition {
    private final String type;
    private final Pattern pattern;

    /**
     * Constructs a TokenDefinition.
     * @param type The token type (e.g., KEYWORD, IDENTIFIER, NUMBER).
     * @param regex The regular expression pattern to match the token.
     */
    public TokenDefinition(String type, String regex) {
        this.type = type;
        this.pattern = Pattern.compile(regex);
    }

    public String getType() {
        return type;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
