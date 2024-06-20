package gr.aueb.cf.expensetracker.controller;

import gr.aueb.cf.expensetracker.service.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ErrorController {
    /**
     * Handles CustomException and returns a custom response entity.
     *
     * @param ce    the CustomException thrown.
     * @return      ResponseEntity with the custom API message and status code from the exception
     */
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<String> handleCustomException(CustomException ce) {
        log.error("Error", ce);
        return new ResponseEntity<>(ce.getApiMessage(), ce.getStatusCode());
    }

    /**
     * Handles AccessDeniedException and returns a forbidden response entity.
     *
     * @param accessDeniedException the AccessDeniedException thrown.
     * @return ResponseEntity with a FORBIDDEN status and the localized message from the exception
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException accessDeniedException) {
        return new ResponseEntity<>(accessDeniedException.getLocalizedMessage(), HttpStatus.FORBIDDEN);
    }

    /**
     * Handles MethodArgumentTypeMismatchException and returns a bad request response entity.
     *
     * @param methodArgumentTypeMismatchException   the MethodArgumentTypeMismatchException thrown
     * @return  ResponseEntity with a BAD_REQUEST status and the localized message from the exception
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        return new ResponseEntity<>(methodArgumentTypeMismatchException.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles general exceptions and returns an internal server error response entity.
     *
     * @param ex    the Exception thrown.
     * @return      ResponseEntity with an INTERNAL_SERVER_ERROR status and a generic error message.
     */
    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public ResponseEntity<String> handleBadRequest(Exception ex) {
        log.error("Error", ex);
        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
