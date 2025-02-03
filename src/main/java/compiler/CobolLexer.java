package compiler;

import java.util.*;
import java.util.regex.*;

/**
 * CobolLexer - Converts COBOL source code into tokens.
 */
public class CobolLexer {
    private static final List<TokenDefinition> TOKEN_DEFINITIONS = TokenDefinitions.getTokenDefinitions();

    /**
     * Tokenizes the COBOL source code.
     * @param sourceCode COBOL source as a string.
     * @return List of tokens.
     */
    public static List<Token> tokenize(String sourceCode) {
        List<Token> tokens = new ArrayList<>();
        String remainingCode = sourceCode;

        while (!remainingCode.isEmpty()) {
            remainingCode = remainingCode.trim();
            boolean matched = false;

            for (TokenDefinition def : TOKEN_DEFINITIONS) {
                Matcher matcher = def.getPattern().matcher(remainingCode);
                if (matcher.find() && matcher.start() == 0) {
                    tokens.add(new Token(def.getType(), matcher.group()));
                    remainingCode = remainingCode.substring(matcher.end());
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                throw new RuntimeException("Unexpected token in source: " + remainingCode);
            }
        }

        return tokens;
    }
}
