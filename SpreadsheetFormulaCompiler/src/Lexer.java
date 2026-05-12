import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int pos;          // current index
    private final int length;

    public Lexer(String input) {
        this.input = input;
        this.length = input.length();
        this.pos = 0;
    }

    // Main entry: tokenize the whole input
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (pos < length) {
            char current = input.charAt(pos);

            // Skip whitespace
            if (Character.isWhitespace(current)) {
                pos++;
                continue;
            }

            // Single‑character tokens
            if (current == '=') {
                tokens.add(new Token(TokenType.EQUALS, "=", pos));
                pos++;
            } else if (current == '+') {
                tokens.add(new Token(TokenType.PLUS, "+", pos));
                pos++;
            } else if (current == '-') {
                tokens.add(new Token(TokenType.MINUS, "-", pos));
                pos++;
            } else if (current == '*') {
                tokens.add(new Token(TokenType.STAR, "*", pos));
                pos++;
            } else if (current == '/') {
                tokens.add(new Token(TokenType.SLASH, "/", pos));
                pos++;
            } else if (current == '(') {
                tokens.add(new Token(TokenType.LPAREN, "(", pos));
                pos++;
            } else if (current == ')') {
                tokens.add(new Token(TokenType.RPAREN, ")", pos));
                pos++;
            } else if (current == ',') {
                tokens.add(new Token(TokenType.COMMA, ",", pos));
                pos++;
            }
            // Numbers
            else if (Character.isDigit(current)) {
                int start = pos;
                while (pos < length && Character.isDigit(input.charAt(pos))) {
                    pos++;
                }
                String number = input.substring(start, pos);
                tokens.add(new Token(TokenType.INTEGER, number, start));
            }
            // Identifiers: could be function keyword or cell reference
            else if (Character.isLetter(current)) {
                int start = pos;
                // Collect letters and digits (cell ref includes trailing digits)
                while (pos < length && Character.isLetterOrDigit(input.charAt(pos))) {
                    pos++;
                }
                String word = input.substring(start, pos);

                // Check if it's a function keyword
                if (word.equals("SUM") || word.equals("MAX") || word.equals("MIN")
                        || word.equals("AVERAGE") || word.equals("COUNT") || word.equals("PRODUCT")) {
                    tokens.add(new Token(TokenType.FUNCTION, word, start));
                }
                // Otherwise it must be a cell reference: letters then digits
                else if (word.matches("[A-Za-z]+[0-9]+")) {
                    // Enforce uppercase letters? We'll accept any case, but canonical cell refs are uppercase.
                    // For consistency with spec, assume uppercase only? We'll be lenient.
                    if (word.matches("[A-Z]+[0-9]+")) {
                        tokens.add(new Token(TokenType.CELL_REF, word.toUpperCase(), start));
                    } else {
                        throw new RuntimeException("Invalid cell reference or identifier: " + word
                                + " at position " + start);
                    }
                } else {
                    throw new RuntimeException("Unexpected identifier: " + word + " at position " + start);
                }
            }
            // Any other character is an error
            else {
                throw new RuntimeException("Unexpected character '" + current + "' at position " + pos);
            }
        }
        tokens.add(new Token(TokenType.EOF, "EOF", length));
        return tokens;
    }
}