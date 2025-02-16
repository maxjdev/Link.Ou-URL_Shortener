package ou.link.exceptions.business;

public class UrlTooLongException extends RuntimeException {
    public UrlTooLongException(String message) {
        super(message);
    }
}
