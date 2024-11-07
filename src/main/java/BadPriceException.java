// Custom Exception for Invalid Price
class BadPriceException extends Exception {
    public BadPriceException(String message) {
        super(message);
    }
}