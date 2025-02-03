package compiler;

import java.util.List;

/**
 * CobolParserTest - Basic test cases for the COBOL parser.
 */
public class CobolParserTest {
    public static void main(String[] args) {
        String cobolCode = "DISPLAY \"Hello, COBOL\".\nMOVE 100 TO VAR1.\nSTOP RUN.";

        List<Token> tokens = CobolLexer.tokenize(cobolCode);
        CobolParser parser = new CobolParser(tokens);
        ASTNode ast = parser.parseProgram();

        assert ast.getChildren().size() == 3;
        System.out.println("All tests passed!");
    }
}
