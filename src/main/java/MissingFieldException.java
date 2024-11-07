// Custom Exception for Missing Field
class MissingFieldException extends Exception {
    public MissingFieldException(String message) {
        super(message);
    }
}