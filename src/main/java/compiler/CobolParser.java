package compiler;

import java.util.List;

/**
 * CobolParser - Converts COBOL tokens into an Abstract Syntax Tree (AST).
 */
public class CobolParser {
    private final List<Token> tokens;
    private int position = 0;

    /**
     * Initializes the COBOL Parser.
     * @param tokens The list of tokens from the lexer.
     */
    public CobolParser(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Parses the entire COBOL program into an AST.
     * @return The root AST node representing the program.
     */
    public ASTNode parseProgram() {
        ASTNode programNode = new ASTNode("Program");


        // Call skipCobolDivisions() to ignore COBOL division headers before parsing statements
        skipCobolDivisions();

        while (!isAtEnd()) {
            programNode.addChild(parseStatement());
        }
        return programNode;
    }

    /**
     * Skips COBOL division headers like "IDENTIFICATION DIVISION.", "DATA DIVISION."
     */
    private void skipCobolDivisions() {
        while (!isAtEnd()) {
            Token token = peek();
            if (token.type().equals("DIVISION_HEADER")) {
                consume("DIVISION_HEADER"); //  Consume "IDENTIFICATION", "DATA", or "PROCEDURE"
                consume("KEYWORD", "DIVISION"); //  Consume "DIVISION"
                consume("PUNCTUATION", "."); //  Consume the period after "DIVISION"
            } else {
                break;
            }
        }
    }

    /**
     * Parses a COBOL statement.
     * Supports DISPLAY, MOVE, STOP RUN, CALL, EXEC SQL, and more.
     * @return An ASTNode representing the statement.
     */
    private ASTNode parseStatement() {
        Token token = peek();

        if (match("KEYWORD", "DISPLAY")) {
            return parseDisplayStatement();
        } else if (match("KEYWORD", "MOVE")) {
            return parseMoveStatement();
        } else if (match("KEYWORD", "STOP")) {
            return parseStopRunStatement();
        } else if (match("KEYWORD", "CALL")) {
            return parseCallStatement();
        } else if (match("KEYWORD", "EXEC")) {
            return parseExecSQLStatement();
        } else {
            throw new RuntimeException("Unexpected token: " + token.value());
        }
    }

    /**
     * Parses a `DISPLAY` statement.
     * Example: DISPLAY "Hello World".
     */
    private ASTNode parseDisplayStatement() {
        consume("KEYWORD", "DISPLAY");
        Token value = consume("STRING");
        consume("PUNCTUATION", ".");

        return new ASTNode("DisplayStatement", value.value());
    }

    /**
     * Parses a `MOVE` statement.
     * Example: MOVE 10 TO VAR.
     */
    private ASTNode parseMoveStatement() {
        consume("KEYWORD", "MOVE");
        Token value = consume("NUMBER");
        consume("KEYWORD", "TO");
        Token variable = consume("IDENTIFIER");
        consume("PUNCTUATION", ".");

        ASTNode moveNode = new ASTNode("MoveStatement");
        moveNode.addChild(new ASTNode("Value", value.value()));
        moveNode.addChild(new ASTNode("Variable", variable.value()));

        return moveNode;
    }

    /**
     * Parses a `STOP RUN` statement.
     */
    private ASTNode parseStopRunStatement() {
        consume("KEYWORD", "STOP");
        consume("KEYWORD", "RUN");
        consume("PUNCTUATION", ".");

        return new ASTNode("StopRun");
    }

    /**
     * Parses a `CALL` statement.
     * Example: CALL "SUBROUTINE" USING PARAM1, PARAM2.
     */
    private ASTNode parseCallStatement() {
        consume("KEYWORD", "CALL");
        Token subroutineName = consume("STRING");

        ASTNode callNode = new ASTNode("CallStatement");
        callNode.addChild(new ASTNode("Subroutine", subroutineName.value()));

        if (match("KEYWORD", "USING")) {
            while (!match("PUNCTUATION", ".")) {
                Token param = consume("IDENTIFIER");
                callNode.addChild(new ASTNode("Parameter", param.value()));
                match("PUNCTUATION", ",");
            }
        } else {
            consume("PUNCTUATION", ".");
        }

        return callNode;
    }

    /**
     * Parses an `EXEC SQL` statement.
     */
    private ASTNode parseExecSQLStatement() {
        consume("KEYWORD", "EXEC");
        consume("KEYWORD", "SQL");

        StringBuilder sqlQuery = new StringBuilder();
        while (!match("KEYWORD", "END-EXEC")) {
            sqlQuery.append(consumeAny().value()).append(" ");
        }
        consume("PUNCTUATION", ".");

        return new ASTNode("ExecSQL", sqlQuery.toString());
    }

    // Overloaded method: Allows consuming a token by type only
    private Token consume(String type) {
        if (isAtEnd()) throw new RuntimeException("Unexpected end of input");
        Token token = tokens.get(position);
        if (token.type().equals(type)) {
            position++;
            return token;
        }
        throw new RuntimeException("Expected " + type + " but found " + token.value());
    }

    // Original method: Requires both type and specific value
    private Token consume(String type, String value) {
        Token token = tokens.get(position);
        if (token.type().equals(type) && token.value().equals(value)) {
            position++;
            return token;
        }
        throw new RuntimeException("Expected " + value + " but found " + token.value());
    }

    private Token consumeAny() {
        return tokens.get(position++);
    }

    private boolean match(String type, String value) {
        if (isAtEnd()) return false;
        Token token = tokens.get(position);
        if (token.type().equals(type) && token.value().equals(value)) {
            position++;
            return true;
        }
        return false;
    }

    private Token peek() {
        return tokens.get(position);
    }

    private boolean isAtEnd() {
        return position >= tokens.size();
    }

}
