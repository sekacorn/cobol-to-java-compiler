package compiler;


import java.nio.file.*;
import java.util.List;

/**
 * MainCompiler - The entry point for the COBOL to Java compiler.
 * This class loads the COBOL source, tokenizes it, parses it into an AST,
 * and generates Java code.
 */
public class MainCompiler {
    public static void main(String[] args) {
        try {
            // Read COBOL source code from file
            String sourceCode = Files.readString(Paths.get("path-to-sample1.cbl"));

            // Enable debugging if needed
            Debugger.enableDebugging();

            // Tokenize COBOL code
            Debugger.log("Starting Lexical Analysis...");
            List<Token> tokens = CobolLexer.tokenize(sourceCode);
            Debugger.log("Tokens: " + tokens);

            // Parse tokens into AST
            Debugger.log("Parsing Tokens...");
            CobolParser parser = new CobolParser(tokens);
            ASTNode ast = parser.parseProgram();
            Debugger.log("AST: " + ast);

            // Generate Java code
            Debugger.log("Generating Java Code...");
            JavaCodeGenerator generator = new JavaCodeGenerator(ast);
            String javaCode = generator.generateCode();

            // Print final output
            System.out.println("Generated Java Code:\n" + javaCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
