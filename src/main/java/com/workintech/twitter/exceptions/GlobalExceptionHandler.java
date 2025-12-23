package com.workintech.twitter.exceptions;
import com.workintech.twitter.dto.response.TwitterErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.time.LocalDateTime;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TwitterException.class)
    public ResponseEntity<TwitterErrorResponse> handleTwitterException(TwitterException ex) {

        TwitterErrorResponse response = new TwitterErrorResponse();
        response.setStatus(ex.getHttpStatus().value());
        response.setMessage(ex.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        response.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<TwitterErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

        TwitterErrorResponse response = new TwitterErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Geçersiz parametre tipi.");
        response.setTimestamp(System.currentTimeMillis());
        response.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TwitterErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation hatası");

        TwitterErrorResponse response = new TwitterErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(message);
        response.setTimestamp(System.currentTimeMillis());
        response.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TwitterErrorResponse> handleGeneric(Exception ex) {

        log.error("Unhandled exception", ex);

        TwitterErrorResponse response = new TwitterErrorResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Beklenmeyen bir hata oluştu.");
        response.setTimestamp(System.currentTimeMillis());
        response.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

