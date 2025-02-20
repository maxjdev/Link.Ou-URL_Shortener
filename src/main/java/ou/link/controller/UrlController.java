package ou.link.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ou.link.controller.dtos.in.UrlIn;
import ou.link.controller.dtos.out.Response;
import ou.link.controller.dtos.out.UrlOut;
import ou.link.model.Url;
import ou.link.service.IUrlShortener;

import java.io.IOException;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UrlController {
    private final IUrlShortener<Url> urlShortener;

    @Operation(summary = "It takes a long URL and returns the shortened version, creating a new resource.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully shortened URL"),
            @ApiResponse(responseCode = "404", description = "URL not found"),
            @ApiResponse(responseCode = "422", description = "URL too long or empty URL")
    })
    @Cacheable(value = "shortenedUrls", key = "#urlLong.url()")
    @PostMapping("/shorten")
    public ResponseEntity<Response<UrlOut>> shortenUrl(@RequestBody UrlIn urlLong) {
        log.info("Received call to shorten URL: {}", urlLong.url());

        Url shortenedUrl = urlShortener.shortenUrl(urlLong.url());

        URI location = URI.create("/api/url/" + shortenedUrl.getId());
        UrlOut urlOut = new UrlOut(shortenedUrl);
        Response<UrlOut> response = new Response<>(
                HttpStatus.CREATED.toString(),
                "Successfully shortened URL",
                urlOut);

        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Takes a shortened URL and returns the original long URL.")
    @ApiResponses(value = {
            @ApiResponse(description = "URL redirected successfully"),
            @ApiResponse(responseCode = "404", description = "URL not found"),
            @ApiResponse(responseCode = "422", description = "Empty URL")
    })
    @GetMapping("/link-ou/{urlShort}")
    public void expandUrl(@PathVariable UrlIn urlShort, HttpServletResponse httpServletResponse) throws IOException {
        log.info("Received call to expand URL: {}", urlShort);

        Url expandedUrl = urlShortener.expandUrl(urlShort.url());

        if (expandedUrl != null) {
            httpServletResponse.sendRedirect(expandedUrl.getUrlLong());
        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "URL not found");
        }
    }
}
