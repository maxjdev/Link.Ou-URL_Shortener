package ou.link.service;

public interface IUrlShortener<T> {
    T shortenUrl(String urlLong);
    T expandUrl(String urlShort);
}
