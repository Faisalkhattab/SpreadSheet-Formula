public class CellRefNode extends ASTNode {
    public final String cell;

    public CellRefNode(String cell) {
        this.cell = cell;
    }

    @Override
    public String prettyPrint(String indent) {
        return indent + "CellRef(" + cell + ")";
    }
}