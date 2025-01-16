public class InvalidLoginException extends Exception {
    public InvalidLoginException(String message) {
        super(message);
    }
    public InvalidLoginException() {
        super("please give valid login credentials.");
    }
}
