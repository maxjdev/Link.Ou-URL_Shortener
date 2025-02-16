package ou.link.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ou.link.exceptions.business.UrlEmptyException;
import ou.link.exceptions.business.UrlNotFoundException;
import ou.link.exceptions.business.UrlTooLongException;
import ou.link.model.Url;
import ou.link.repository.IUrlRepository;
import ou.link.service.IUrlShortener;
import ou.link.validations.IValidations;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlShortenerImpl implements IUrlShortener<Url> {
    private final IUrlRepository repository;
    private final IValidations validations;

    @Override
    public Url shortenUrl(String urlLong) {
        if (validations.isUrlAlreadyShortened(urlLong)) {
            return repository.findByUrlLong(urlLong)
                    .orElseThrow(() -> new UrlNotFoundException("URL not found in the database"));
        }
        if (validations.isUrlTooLong(urlLong)) {
            throw new UrlTooLongException("URL too long");
        }
        if (validations.isUrlEmpty(urlLong)) {
            throw new UrlEmptyException("URL is empty");
        }

        Url url = Url.builder()
                .urlLong(urlLong)
                .urlShort(generateRandomUrl())
                .createdAt(LocalDateTime.now())
                .build();
        log.info("URL created: {}", url);

        return repository.save(url);
    }

    @Override
    public Url expandUrl(String urlShort) {
        if (validations.isUrlEmpty(urlShort)) {
            throw new UrlEmptyException("URL is empty");
        }

        Optional<Url> url = repository.findByUrlShort(urlShort);
        if (url.isEmpty()) {
            log.error("URL not found: {}", urlShort);
            throw new UrlNotFoundException("URL not found in the database");
        }

        log.info("URL found: {}", url.get());
        return url.get();
    }

    private String generateRandomUrl() {
        log.info("Generating random URL");
        return RandomStringUtils.secure().nextAlphanumeric(4, 8);
    }
}
