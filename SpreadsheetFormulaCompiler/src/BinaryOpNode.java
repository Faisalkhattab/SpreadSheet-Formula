public class BinaryOpNode extends ASTNode {
    public final ASTNode left;
    public final String operator; // "+", "-", "*", "/"
    public final ASTNode right;

    public BinaryOpNode(ASTNode left, String operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String prettyPrint(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append("BinaryOp(").append(operator).append(")\n");
        sb.append(left.prettyPrint(indent + "  ")).append("\n");
        sb.append(right.prettyPrint(indent + "  "));
        return sb.toString();
    }
}