package ou.link.exceptions.business;

public class UrlAlreadyShortenedException extends RuntimeException {
    public UrlAlreadyShortenedException(String message) {
        super(message);
    }
}
