public class ParseException extends RuntimeException {
    public ParseException(String message, int position) {
        super("Parse error at position " + position + ": " + message);
    }
}