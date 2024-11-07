// Custom Exception for Invalid ISBN-10
class BadIsbn10Exception extends Exception {
    public BadIsbn10Exception(String message) {
        super(message);
    }
}