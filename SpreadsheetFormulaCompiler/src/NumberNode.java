public class NumberNode extends ASTNode {
    public final int value;

    public NumberNode(int value) {
        this.value = value;
    }

    @Override
    public String prettyPrint(String indent) {
        return indent + "Number(" + value + ")";
    }
}