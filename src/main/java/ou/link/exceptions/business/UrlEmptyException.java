package ou.link.exceptions.business;

public class UrlEmptyException extends RuntimeException {
    public UrlEmptyException(String message) {
        super(message);
    }
}
