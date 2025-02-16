package ou.link.validations;

public interface IValidations {
    boolean isUrlAlreadyShortened(String urlLong);
    boolean isUrlTooLong(String urlLong);
    boolean isUrlEmpty(String urlLong);
}
