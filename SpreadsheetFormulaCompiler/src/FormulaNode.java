public class FormulaNode extends ASTNode {
    public final ASTNode expression;

    public FormulaNode(ASTNode expression) {
        this.expression = expression;
    }

    @Override
    public String prettyPrint(String indent) {
        return indent + "Formula\n" + expression.prettyPrint(indent + "  ");
    }
}