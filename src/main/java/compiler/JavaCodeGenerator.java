package compiler;

import java.util.List;

/**
 * JavaCodeGenerator - Converts the COBOL AST into Java source code.
 * This class traverses the AST and generates equivalent Java statements.
 */
public class JavaCodeGenerator {
    private final ASTNode root;
    private final StringBuilder code = new StringBuilder();

    /**
     * Constructor that initializes the root AST node.
     * @param root The root AST node of the parsed COBOL program.
     */
    public JavaCodeGenerator(ASTNode root) {
        this.root = root;
    }

    /**
     * Generates Java code from the COBOL AST.
     * @return A formatted Java source code string.
     */
    public String generateCode() {
        code.append("import java.util.*;\n");
        code.append("import java.sql.*;\n");
        code.append("public class Program {\n");
        code.append("    public static void main(String[] args) throws SQLException {\n");

        for (ASTNode child : root.getChildren()) {
            processNode(child);
        }

        code.append("    }\n");
        code.append("}\n");
        return code.toString();
    }

    /**
     * Processes different AST node types and translates them into Java statements.
     * @param node The ASTNode to process.
     */
    private void processNode(ASTNode node) {
        switch (node.getType()) {
            case "DisplayStatement":
                processDisplayStatement(node);
                break;
            case "MoveStatement":
                processMoveStatement(node);
                break;
            case "StopRun":
                code.append("        System.exit(0);\n");
                break;
            case "CallStatement":
                processCallStatement(node);
                break;
            case "ExecSQL":
                processExecSQL(node);
                break;
        }
    }

    /**
     * Translates `DISPLAY` statements to Java's `System.out.println()`.
     * @param node ASTNode representing the COBOL DISPLAY statement.
     */
    private void processDisplayStatement(ASTNode node) {
        code.append("        System.out.println(\"").append(node.getValue()).append("\");\n");
    }

    /**
     * Translates `MOVE` statements to Java variable assignments.
     * @param node ASTNode representing the COBOL MOVE statement.
     */
    private void processMoveStatement(ASTNode node) {
        String value = node.getChildren().get(0).getValue();
        String variable = node.getChildren().get(1).getValue();
        code.append("        ").append(variable).append(" = ").append(value).append(";\n");
    }

    /**
     * Translates `CALL` statements to Java method calls.
     * @param node ASTNode representing the COBOL CALL statement.
     */
    private void processCallStatement(ASTNode node) {
        String subroutine = node.getChildren().get(0).getValue();
        List<ASTNode> parameters = node.getChildren().subList(1, node.getChildren().size());

        code.append("        ").append(subroutine).append(".methodName(");
        for (int i = 0; i < parameters.size(); i++) {
            if (i > 0) code.append(", ");
            code.append(parameters.get(i).getValue());
        }
        code.append(");\n");
    }

    /**
     * Translates `EXEC SQL` statements into Java JDBC.
     * @param node ASTNode representing the COBOL EXEC SQL statement.
     */
    private void processExecSQL(ASTNode node) {
        String sqlQuery = node.getValue();
        code.append("        try (Connection conn = DriverManager.getConnection(\"jdbc:mysql://localhost:3306/mydb\", \"user\", \"password\");\n");
        code.append("             PreparedStatement stmt = conn.prepareStatement(\"").append(sqlQuery).append("\")) {\n");
        code.append("            ResultSet rs = stmt.executeQuery();\n");
        code.append("            if (rs.next()) {\n");
        code.append("                System.out.println(rs.getString(1));\n");
        code.append("            }\n");
        code.append("        }\n");
    }
}
