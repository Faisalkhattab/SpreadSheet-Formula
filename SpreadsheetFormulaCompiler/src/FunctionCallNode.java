import java.util.List;
import java.util.stream.Collectors;

public class FunctionCallNode extends ASTNode {
    public final String functionName;
    public final List<ASTNode> arguments;

    public FunctionCallNode(String functionName, List<ASTNode> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public String prettyPrint(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append("FunctionCall(").append(functionName).append(")\n");
        for (int i = 0; i < arguments.size(); i++) {
            sb.append(arguments.get(i).prettyPrint(indent + "  "));
            if (i < arguments.size() - 1) sb.append("\n");
        }
        return sb.toString();
    }
}