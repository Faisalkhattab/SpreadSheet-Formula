public enum TokenType {
    EQUALS,      // =
    PLUS,        // +
    MINUS,       // -
    STAR,        // *
    SLASH,       // /
    LPAREN,      // (
    RPAREN,      // )
    COMMA,       // ,
    FUNCTION,    // SUM, MAX, MIN
    CELL_REF,    // e.g., A1, AA10
    INTEGER,     // sequence of digits
    EOF          // end of input
}