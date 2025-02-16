package ou.link.validations.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ou.link.repository.IUrlRepository;
import ou.link.validations.IValidations;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidationsImpl implements IValidations {
    private final IUrlRepository repository;

    @Override
    public boolean isUrlAlreadyShortened(String urlLong) {
        boolean exists = repository.existsByUrlLong(urlLong);

        if (exists) {
            log.error("URL already exists in the database. {}", urlLong);
        } else {
            log.info("URL does not exist in the database. {}", urlLong);
        }

        return exists;
    }

    @Override
    public boolean isUrlTooLong(String urlLong) {
        boolean isTooLong = urlLong.length() > 2048;

        if (isTooLong) {
            log.error("URL exceeds allowed length of 2048 characters: {}", urlLong);
        } else {
            log.info("URL length is within allowed limit: {}", urlLong);
        }

        return isTooLong;
    }

    @Override
    public boolean isUrlEmpty(String urlLong) {
        boolean isEmpty = urlLong.isEmpty();

        if (isEmpty) {
            log.error("The URL provided is empty or blank. URL: {}", urlLong);
        } else {
            log.info("URL is not empty or blank: {}", urlLong);
        }

        return isEmpty;
    }
}
