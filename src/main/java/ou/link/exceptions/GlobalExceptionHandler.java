package ou.link.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ou.link.exceptions.business.UrlAlreadyShortenedException;
import ou.link.exceptions.business.UrlEmptyException;
import ou.link.exceptions.business.UrlNotFoundException;
import ou.link.exceptions.business.UrlTooLongException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ApiError> handlerUrlNotFound(final UrlNotFoundException ex) {
        var apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(UrlEmptyException.class)
    public ResponseEntity<ApiError> handlerUrlEmpty(final UrlEmptyException ex) {
        var apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(UrlTooLongException.class)
    public ResponseEntity<ApiError> handlerUrlTooLong(final UrlTooLongException ex) {
        var apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    @ExceptionHandler(UrlAlreadyShortenedException.class)
    public ResponseEntity<ApiError> handlerUrlAlreadyShortened(final UrlAlreadyShortenedException ex) {
        var apiError = new ApiError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
                ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handlerGeneric(final Exception ex) {
        log.error("UNEXPECTED ERROR", ex);
        var apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected internal server error occured");
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public record ApiError(
            @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
            LocalDateTime timestamp,
            int code,
            String status,
            String description
    ) {
        public ApiError(int code, String status, String description) {
            this(LocalDateTime.now(), code, status, description);
        }
    }
}
