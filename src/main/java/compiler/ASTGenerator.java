package compiler;

import java.util.List;


/**
 * ASTGenerator - Manages AST construction from COBOL source code.
 * This class acts as an intermediate step between the parser and the code generator.
 */
class ASTGenerator {
    private final CobolParser parser;

    /**
     * Constructor for ASTGenerator.
     * @param tokens List of tokens from the COBOL Lexer.
     */
    public ASTGenerator(List<Token> tokens) {
        this.parser = new CobolParser(tokens);
    }

    /**
     * Generates an AST from the parsed tokens.
     * @return The root node of the AST.
     */
    public ASTNode generateAST() {
        return parser.parseProgram();
    }

    /**
     * Optimizes the AST (Optional Step)
     */
    public void optimizeAST(ASTNode root) {
        // Example: Remove unnecessary nodes, simplify expressions, etc.
        Debugger.log("Optimizing AST (Feature Coming Soon)");
    }
}
