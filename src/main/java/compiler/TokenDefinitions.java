package compiler;

import java.util.Arrays;
import java.util.List;


/**
 * TokenDefinitions - Ensures all COBOL division headers are correctly recognized.
 */
public class TokenDefinitions {
    public static List<TokenDefinition> getTokenDefinitions() {
        return Arrays.asList(
                new TokenDefinition("DIVISION_HEADER", "\\b(IDENTIFICATION|DATA|PROCEDURE)\\b"),
                new TokenDefinition("KEYWORD", "\\b(DIVISION|PROGRAM-ID|WORKING-STORAGE|SECTION|STOP RUN|MOVE|TO|IF|ELSE|DISPLAY|COMPUTE|PERFORM|TIMES|READ|WRITE|OPEN|CLOSE|INTO|OUTPUT|INPUT|END-IF|END-PERFORM|ACCEPT|EVALUATE|WHEN|OTHER|END-EVALUATE|STRING|FUNCTION|LENGTH|OCCURS|SEARCH|AT|END|ALL|SORT|ASCENDING|DESCENDING|MERGE|CALL|USING|GOTO|EXEC|SQL|END-EXEC|PIC|COMPUTE|ACCEPT)\\b"),
                new TokenDefinition("IDENTIFIER", "\\b[A-Z][A-Z0-9-]*\\b"),
                new TokenDefinition("STRING", "\"[^\"]*\""),
                new TokenDefinition("NUMBER", "\\b\\d+(\\.\\d+)?\\b"),
                new TokenDefinition("COMMENT", "\\*>.*"),
                new TokenDefinition("OPERATOR", "(<=|>=|<>|=|<|>)"),
                new TokenDefinition("OPERATOR", "[+\\-*/=]"),
                new TokenDefinition("PUNCTUATION", "[,\\.\\\"]"),
                new TokenDefinition("SYMBOL", "[()\\:]")
        );
    }
}

