import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current; // index of current token

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.current = 0;
    }

    // Entry point: formula = '=' expr
    public FormulaNode parseFormula() {
        // Expect leading '='
        Token eq = consume(TokenType.EQUALS, "Formula must start with '='");
        ASTNode expr = parseExpr();
        // After expression we should be at EOF
        if (peek().type != TokenType.EOF) {
            throw new ParseException("Unexpected token after expression: " + peek().lexeme, peek().position);
        }
        return new FormulaNode(expr);
    }

    // expr → term (('+'|'-') term)*
    private ASTNode parseExpr() {
        ASTNode left = parseTerm();
        while (peek().type == TokenType.PLUS || peek().type == TokenType.MINUS) {
            Token op = advance();
            ASTNode right = parseTerm();
            left = new BinaryOpNode(left, op.lexeme, right);
        }
        return left;
    }

    // term → factor (('*'|'/') factor)*
    private ASTNode parseTerm() {
        ASTNode left = parseFactor();
        while (peek().type == TokenType.STAR || peek().type == TokenType.SLASH) {
            Token op = advance();
            ASTNode right = parseFactor();
            left = new BinaryOpNode(left, op.lexeme, right);
        }
        return left;
    }

    // factor → '(' expr ')' | function_call | cell_ref | integer
    private ASTNode parseFactor() {
        Token token = peek();

        if (token.type == TokenType.LPAREN) {
            advance(); // consume '('
            ASTNode expr = parseExpr();
            consume(TokenType.RPAREN, "Expected ')' after expression");
            return expr;
        } else if (token.type == TokenType.FUNCTION) {
            return parseFunctionCall();
        } else if (token.type == TokenType.CELL_REF) {
            advance();
            return new CellRefNode(token.lexeme);
        } else if (token.type == TokenType.INTEGER) {
            advance();
            return new NumberNode(Integer.parseInt(token.lexeme));
        } else {
            throw new ParseException("Unexpected token " + token.lexeme + " (" + token.type + ")",
                    token.position);
        }
    }

    // function_call → FUNCTION '(' arglist ')'
    private FunctionCallNode parseFunctionCall() {
        Token funcToken = consume(TokenType.FUNCTION, "Expected function name");
        String funcName = funcToken.lexeme;
        consume(TokenType.LPAREN, "Expected '(' after function name");
        List<ASTNode> args = new ArrayList<>();
        // Parse arguments if not immediately ')'
        if (peek().type != TokenType.RPAREN) {
            args.add(parseExpr());
            while (peek().type == TokenType.COMMA) {
                advance(); // consume ','
                args.add(parseExpr());
            }
        }
        consume(TokenType.RPAREN, "Expected ')' after function arguments");
        return new FunctionCallNode(funcName, args);
    }

    // Helper methods
    private Token peek() {
        return tokens.get(current);
    }

    private Token advance() {
        Token t = tokens.get(current);
        current++;
        return t;
    }

    private Token consume(TokenType type, String errorMessage) {
        Token t = peek();
        if (t.type != type) {
            throw new ParseException(errorMessage + " but found " + t.lexeme + " (" + t.type + ")",
                    t.position);
        }
        return advance();
    }
}