import java.util.List;

public class Main {
    public static void main(String[] args) {
        // ---------- test cases (valid + invalid) ----------
        String[] testCases = {
                "=A + B2",                   // simple valid
                "=(A1 + B1) * 2",             // valid with parens
                "=SUM(A1, B2, 5)",            // function call
                "=IF(A1 > 10, B1, 0)",        // invalid: IF not implemented (will cause lexer/parser error)
                "=A1 +",                      // invalid: incomplete expression
                "=MAX(A1, MIN(B1, 2))",       // nested function call
                "= 10 * (A1 + B2)",           // valid with spaces
                "=SUM( , )",                  // invalid: empty arguments? parser fails on ',' without expr
                "=A1:B2",                     // invalid: ':' not allowed (optional range)
                "=AA10 + 5"                   // valid two-letter cell
        };

        for (String input : testCases) {
            System.out.println("=======================================");
            System.out.println("Input: " + input);

            // --- Lexer phase ---
            try {
                Lexer lexer = new Lexer(input);
                List<Token> tokens = lexer.tokenize();
                System.out.println("Token stream:");
                for (Token t : tokens) {
                    System.out.println("  " + t);
                }

                // --- Parser phase ---
                Parser parser = new Parser(tokens);
                FormulaNode ast = parser.parseFormula();
                System.out.println("AST:");
                System.out.println(ast.prettyPrint("  "));
            } catch (RuntimeException e) {
                System.err.println("ERROR: " + e.getMessage());
            }
            System.out.println();
        }
    }
}