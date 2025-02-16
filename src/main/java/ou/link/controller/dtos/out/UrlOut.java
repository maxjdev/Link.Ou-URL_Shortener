package ou.link.controller.dtos.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import ou.link.model.Url;

import java.time.LocalDateTime;

public record UrlOut(
        String urlLong,
        String urlShort,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime createdAt
) {
        public UrlOut(Url url) {
            this(url.getUrlLong(), url.getUrlShort(), url.getCreatedAt());
        }
}
