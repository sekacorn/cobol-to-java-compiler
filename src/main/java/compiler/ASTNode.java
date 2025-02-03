package compiler;


import java.util.List;

/**
 * ASTNode - Represents a node in the Abstract Syntax Tree (AST).
 * Each node corresponds to a COBOL construct (e.g., Statements, Expressions).
 */
public class ASTNode {
    private final String type;
    private final String value;
    private final List<ASTNode> children;

    public ASTNode(String type) {
        this.type = type;
        this.value = "";
        this.children = new java.util.ArrayList<>();
    }

    public ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
        this.children = new java.util.ArrayList<>();
    }

    public void addChild(ASTNode child) {
        children.add(child);
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "ASTNode{" + "type='" + type + "', value='" + value + "', children=" + children + "}";
    }
}